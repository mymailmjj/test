/**
 * 
 */
package server.heart;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author cango
 * 
 */
public class HttpHeartServer {

	private int port;

	public HttpHeartServer(int port) {
		this.port = port;
		init();
	}

	private Selector selector;

	private void init() {
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel
					.open();

			serverSocketChannel.bind(new InetSocketAddress(port));

			serverSocketChannel.configureBlocking(false);

			selector = Selector.open();

			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			ServiceWork serviceWork = new ServiceWork();

			Thread t1 = new Thread(serviceWork);

			t1.start();

		} catch (ClosedChannelException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	class ServiceWork implements Runnable {

		public void run() {
			while (true) {

				try {
					// 如果没有key，则等待
					if (selector.select(2000) == 0) {
						System.out.println("====");
						continue;
					}

					// 这里的方法是阻塞的，请求的时候会触发想要的keytype,然后继续往下执行
					Set<SelectionKey> selectedKeys = selector.selectedKeys();

					Iterator<SelectionKey> iterator = selectedKeys.iterator();

					while (iterator.hasNext()) {

						SelectionKey key = iterator.next();
						if (key.isAcceptable()) {
							handleAccept(key);
						} else if (key.isReadable()) {
							handleRead(key);
						} else if (key.isWritable()) {
							handleWrite(key);
						} else if (key.isConnectable()) {

						}

						iterator.remove();

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

	}

	/**
	 * 心跳器
	 * 
	 * @author cango
	 * 
	 */
	class ServerIdleCheck implements Runnable {

		private SocketChannel socketChannel;

		public ServerIdleCheck(SocketChannel socketChannel) {
			this.socketChannel = socketChannel;
		}

		ByteBuffer buffer = ByteBuffer.wrap("server heart beat".getBytes());

		public void run() {
			
			while(true){
				
				System.out.println("服务端写入");
				try {
					
					buffer.put("server heat beat".getBytes());
					
					buffer.flip();
					
					socketChannel.write(buffer);
					buffer.compact();
				} catch (IOException e) {
					e.printStackTrace();
					try {
						socketChannel.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					break;
				}
				
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}

	}

	private final static int BUFFER_SIZE = 1024;

	public void handleAccept(SelectionKey key) throws IOException {
		ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
		SocketChannel sc = ssChannel.accept();
		sc.configureBlocking(false);
		sc.register(key.selector(), SelectionKey.OP_READ,
				ByteBuffer.allocateDirect(BUFFER_SIZE));
		Thread thread = new Thread(new ServerIdleCheck(sc));
		thread.setDaemon(true);
		thread.start();
	}

	// 这里对用户进入的请求进行处理
	public static void handleRead(SelectionKey key) throws IOException {
		SocketChannel sc = (SocketChannel) key.channel();
		ByteBuffer buf = (ByteBuffer) key.attachment();
		long bytesRead = sc.read(buf);
		while (bytesRead > 0) {
			buf.flip();
			while (buf.hasRemaining()) {
				System.out.print((char) buf.get());
			}
			System.out.println();
			buf.compact();
			bytesRead = sc.read(buf);
		}

		buf.clear();

		if (bytesRead == -1) {
			sc.close();
		}

		// sc.close();

		// sc.register(key.selector(), SelectionKey.OP_WRITE);

		// 这个代码结束
	}

	public static void handleWrite(SelectionKey key) throws IOException {
		System.out.println("服务端写入");
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.put("server heart beat".getBytes());
		buf.flip();
		SocketChannel sc = (SocketChannel) key.channel();
		sc.write(buf);
		buf.compact();
		buf.clear();
		sc.close();
	}

	// private static final int TIMEOUT = 3000;

	public static void main(String[] args) throws IOException {

		HttpHeartServer httpHeartServer = new HttpHeartServer(8081);

	}

}
