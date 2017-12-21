/**
 * 
 */
package sslserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.Set;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author cango
 * 
 */
public class SSLNioServer {

	private int port;

	private SSLContext sslContext;

	private SSLEngine sslEngine;

	private CharsetEncoder encoder = Charset.forName("UTF8").newEncoder();
	private CharsetDecoder decoder = Charset.forName("UTF8").newDecoder();

	private ByteBuffer appOut; // clear text buffer for out
	private ByteBuffer appIn; // clear text buffer for in
	private ByteBuffer netOut; // encrypted buffer for out
	private ByteBuffer netIn; // encrypted buffer for in

	public SSLNioServer(int port) {
		this.port = port;

	}

	public void startServer() {

		createSocketChannel();

		
		createSSLCotext();

		createSSLEngine();
		
		createBuffers();

		try {
			select();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Selector selector;

	private void createSocketChannel() {

		ServerSocketChannel serverSocketChannel;

		try {
			serverSocketChannel = ServerSocketChannel.open();
			selector = Selector.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.bind(new InetSocketAddress(8443));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createBuffers() {
		SSLSession session = sslEngine.getSession();
		int appBufferMax = session.getApplicationBufferSize();
		int netBufferMax = session.getPacketBufferSize();

		appOut = ByteBuffer.wrap("This is an SSL Server".getBytes());// server
																		// only
																		// reply
																		// this
																		// sentence
		appIn = ByteBuffer.allocate(appBufferMax + 10);// appIn is bigger than
														// the allowed max
														// application buffer
														// siz
		netOut = ByteBuffer.allocateDirect(netBufferMax);// direct allocate for
															// better
															// performance
		netIn = ByteBuffer.allocateDirect(netBufferMax);
	}

	private void select() throws IOException {

		while (true) {

			while (selector.select(2000) == 0) {
				System.out.println("====");
				continue;
			}

			// 进入处理程序

			Set<SelectionKey> selectedKeys = selector.selectedKeys();

			Iterator<SelectionKey> iterator = selectedKeys.iterator();

			while (iterator.hasNext()) {

				SelectionKey key = iterator.next();

				if (key.isAcceptable()) {

					ServerSocketChannel channel = (ServerSocketChannel) key
							.channel();

					channel.configureBlocking(false);

					SocketChannel socketChannel = channel.accept();

					doShakeHand(socketChannel);

				} else if (key.isReadable()) {

					if (sslEngine.getHandshakeStatus() == HandshakeStatus.NOT_HANDSHAKING) {

						SocketChannel sc = (SocketChannel) key.channel();
						sc.read(netIn);
						netIn.flip();

						SSLEngineResult engineResult = sslEngine.unwrap(netIn,
								appIn);
						doTask();
//						 runDelegatedTasks(engineResult, sslEngine);
						netIn.compact();
						if (engineResult.getStatus() == SSLEngineResult.Status.OK) {
							appIn.flip();// ready for reading
							
							while(appIn.hasRemaining()){
								
								byte b = appIn.get();
								System.out.print((char)b);
								
							}
							System.out.println();
							appIn.compact();
							
							successResponse(key);
							
						} else if (engineResult.getStatus() == SSLEngineResult.Status.CLOSED) {
							doSSLClose(key);
						}

					}

				} else if (key.isWritable()) {

				}

				iterator.remove(); // 处理完毕删除
			}

		}

	}
	
	
	public void successResponse(SelectionKey key){
		appOut.put("HTTP/1.1 200 OK \r\n".getBytes());
		appOut.flip();
		appOut.compact();
		try {
			this.sslEngine.wrap(appOut, netOut);
			doTask();
		} catch (SSLException e) {
			e.printStackTrace();
		}
	}
	
	
	/*public static void successResponse(SelectionKey key) throws IOException{
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.put("HTTP/1.1 200 OK \r\n".getBytes());
		buf.flip();
		SocketChannel sc = (SocketChannel) key.channel();
		while (buf.hasRemaining()) {
			System.out.println("server response ...");
			sc.write(buf);
		}
		buf.compact();
		
	}*/
	
	
	
	 public static HandshakeStatus runDelegatedTasks(SSLEngineResult engineResult, SSLEngine sslEngine) 
    { 
        if (engineResult.getHandshakeStatus() == HandshakeStatus.NEED_TASK) 
        { 
            Runnable runnable; 
            while ((runnable = sslEngine.getDelegatedTask()) != null) 
            { 
                System.out.println("\trunning delegated task..."); 
                runnable.run(); 
            } 
            HandshakeStatus hsStatus = sslEngine.getHandshakeStatus(); 
            if (hsStatus == HandshakeStatus.NEED_TASK) 
            { 
                //throw new Exception("handshake shouldn't need additional tasks"); 
                System.out.println("handshake shouldn't need additional tasks"); 
            } 
            System.out.println("\tnew HandshakeStatus: " + hsStatus); 
        } 
        return sslEngine.getHandshakeStatus(); 
         
    }  
	

	private boolean handShakeDone = false;

	private void doShakeHand(SocketChannel sc) throws IOException {
		try {
			sslEngine.beginHandshake();
		} catch (SSLException e) {
			e.printStackTrace();
		}

		HandshakeStatus handshakeStatus = sslEngine.getHandshakeStatus();

		while (!handShakeDone) {

			switch (handshakeStatus) {
			case FINISHED:

				break;

			case NEED_TASK:
				handshakeStatus = doTask();
				break;

			case NEED_UNWRAP:
				// unwrap means unwrap the ssl packet to get ssl handshake
				// information
				sc.read(netIn);
				netIn.flip();
				handshakeStatus = doUnwrap();
				break;

			case NEED_WRAP:
				// wrap means wrap the app packet into an ssl packet to add ssl
				// handshake information
				handshakeStatus = doWrap();
				sc.write(netOut);
				netOut.clear();
				break;

			case NOT_HANDSHAKING:
				// now it is not in a handshake or say byebye status. here it
				// means handshake is over and ready for ssl talk
				sc.configureBlocking(false);// set the socket to unblocking mode
				sc.register(selector, SelectionKey.OP_READ
						| SelectionKey.OP_WRITE);// register the read and write
													// event
				handShakeDone = true;
				break;

			default:
				break;
			}

		}

	}

	private HandshakeStatus doUnwrap() throws SSLException {
		HandshakeStatus hsStatus;
		do {// do unwrap until the state is change to "NEED_WRAP"
			SSLEngineResult engineResult = sslEngine.unwrap(netIn, appIn);
			hsStatus = doTask();
		} while (hsStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP
				&& netIn.remaining() > 0);
		System.out.println("\tnew HandshakeStatus: " + hsStatus);
		netIn.clear();
		return hsStatus;
	}

	private HandshakeStatus doWrap() throws SSLException {
		HandshakeStatus hsStatus;
		SSLEngineResult engineResult = sslEngine.wrap(appOut, netOut);
		hsStatus = doTask();
		System.out.println("\tnew HandshakeStatus: " + hsStatus);
		netOut.flip();
		return hsStatus;
	}

	private HandshakeStatus doTask() {
		Runnable runnable;
		while ((runnable = sslEngine.getDelegatedTask()) != null) {
			System.out.println("\trunning delegated task...");
			runnable.run();
		}
		HandshakeStatus hsStatus = sslEngine.getHandshakeStatus();
		if (hsStatus == HandshakeStatus.NEED_TASK) {
			// throw new Exception("handshake shouldn't need additional tasks");
			System.out.println("handshake shouldn't need additional tasks");
		}
		System.out.println("\tnew HandshakeStatus: " + hsStatus);

		return hsStatus;
	}

	private void doSSLClose(SelectionKey key) throws IOException {
		SocketChannel sc = (SocketChannel) key.channel();
		key.cancel();

		try {
			sc.configureBlocking(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HandshakeStatus hsStatus = sslEngine.getHandshakeStatus();
		while (handShakeDone) {
			switch (hsStatus) {
			case FINISHED:

				break;
			case NEED_TASK:
				hsStatus = doTask();
				break;
			case NEED_UNWRAP:
				sc.read(netIn);
				netIn.flip();
				hsStatus = doUnwrap();
				break;
			case NEED_WRAP:
				hsStatus = doWrap();
				sc.write(netOut);
				netOut.clear();
				break;
			case NOT_HANDSHAKING:
				handShakeDone = false;
				sc.close();
				break;
			}
		}
	}

	private void createSSLCotext() {

		try {
			KeyStore ks = KeyStore.getInstance("JKS");
			KeyStore ts = KeyStore.getInstance("JKS");

			char[] passphrase = "123456".toCharArray();

			ks.load(new FileInputStream("D:\\ssl\\kserver.ks"), passphrase);
			ts.load(new FileInputStream("D:\\ssl\\tserver.ks"), passphrase);

			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, passphrase);

			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance("SunX509");
			tmf.init(ts);

			SSLContext sslCtx = SSLContext.getInstance("SSL");

			sslCtx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

			this.sslContext = sslCtx;

		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void createSSLEngine() {
		SSLEngine sslEngine = sslContext.createSSLEngine();
		sslEngine.setUseClientMode(false);
		sslEngine.setNeedClientAuth(true);
		this.sslEngine = sslEngine;

	}

	public static void main(String[] args) {

		SSLNioServer sslNioServer = new SSLNioServer(8443);

		sslNioServer.startServer();

	}

}
