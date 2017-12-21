/**
 * 
 */
package sslserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * 基于ssl方式的serversocket
 * 证书文件见ssl目录
 * @author cango
 * 
 */
public class SSLBioServer {

	private int port;

	public SSLBioServer(int port) {
		this.port = port;
		initSocket();
	}

	class SocketProcessor implements Runnable {

		private Socket socket;

		public SocketProcessor(Socket socket) {
			this.socket = socket;
		}

		public void service() {
			Thread thread = new Thread(this);
			thread.start();
		}

		public void run() {

			try {
				Socket s = socket;
				System.out.println("s:" + s);
				InputStream input = s.getInputStream();
				OutputStream output = s.getOutputStream();

				PrintWriter p = new PrintWriter(output);

				p.println("HTTP/1.1 200 OK");
				
//				bos.write("HTTP/1.1 200 OK".getBytes());

//				bos.write("\n".getBytes());

//				bos.flush();
				
				p.flush();

				byte[] buffer = new byte[20];

				int length = 0;

				input.read(buffer);
				
//				input.read(buffer);   TODO 这里无法重复读，会阻塞，没有找到原因

				System.out.println("recv:" + new String(buffer));

				s.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	private final static String PASSWORD = "123456";

	private void initSocket() {

		SSLContext sslContext = initSSLContext();

		try {
			SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) sslContext
					.getServerSocketFactory();
			SSLServerSocket serverSocket = (SSLServerSocket) sslServerSocketFactory
					.createServerSocket(port);

			serverSocket.setNeedClientAuth(true);

			System.out.println("ssl server start>>>>");

			while (true) {

				SSLSocket socket = (SSLSocket) serverSocket.accept();

				socket.addHandshakeCompletedListener(new HandShakeListener());

				SocketProcessor socketProcessor = new SocketProcessor(socket);

				socketProcessor.service();

			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printDetailSocket(SSLSocket sslSocket) {

		SSLParameters sslParameters = sslSocket.getSSLParameters();

		String[] protocols = sslParameters.getProtocols();

		System.out.println("protocols:" + Arrays.toString(protocols));

		System.out.println("支持的协议: "
				+ Arrays.asList(sslSocket.getSupportedProtocols()));
		System.out.println("启用的协议: "
				+ Arrays.asList(sslSocket.getEnabledProtocols()));
		System.out.println("支持的加密套件: "
				+ Arrays.asList(sslSocket.getSupportedCipherSuites()));
		System.out.println("启用的加密套件: "
				+ Arrays.asList(sslSocket.getEnabledCipherSuites()));

	}

	private SSLContext initSSLContext() {

		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("SSL");

			// 先设置keymanager
			KeyManagerFactory keyManagerFactory = KeyManagerFactory
					.getInstance("SunX509");

			TrustManagerFactory trustFactory = TrustManagerFactory
					.getInstance("SunX509");

			KeyStore keyStore = KeyStore.getInstance("JKS");

			KeyStore keyStore1 = KeyStore.getInstance("JKS");

			FileInputStream fileInputStream = new FileInputStream(
					"D:\\ssl\\kserver.ks");

			keyStore.load(fileInputStream, null);

			keyManagerFactory.init(keyStore, PASSWORD.toCharArray());

			KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

			FileInputStream fileInputStream1 = new FileInputStream(
					"D:\\ssl\\tserver.ks");

			keyStore1.load(fileInputStream1, PASSWORD.toCharArray());

			trustFactory.init(keyStore1);

			TrustManager[] trustManagers = trustFactory.getTrustManagers();

			sslContext.init(keyManagers, trustManagers, null);
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sslContext;

	}

	public static void main(String[] args) {
		SSLBioServer server = new SSLBioServer(8443);

	}

}
