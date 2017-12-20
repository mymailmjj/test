/**
 * 
 */
package server1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author mymai
 *
 */
public class SSLBIOServer {

	private SSLServerSocketFactory sslServerSocketFactory;

	private int port;
	
	private SSLContext sslContext;
	
	private void createSSlContext(){
		try {
			sslContext = SSLContext.getInstance("ssl");
			
			KeyStore keyStore = KeyStore.getInstance("jks");
			
			FileInputStream fileInputStream = new FileInputStream("D://security//tomcat.keystore");
			
			keyStore.load(fileInputStream, null);;
			
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			
			keyManagerFactory.init(keyStore, "123456".toCharArray());
			
			KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
			
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
			
			KeyStore keyStore1 = KeyStore.getInstance("jks");
			
			FileInputStream fileInputStream1 = new FileInputStream("D://security//my.keystore");
			
			keyStore1.load(fileInputStream1, "123456".toCharArray());;
			
			trustManagerFactory.init(keyStore1);
			
			TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
			
			sslContext.init(keyManagers, trustManagers, null);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class SocketProcessor implements Runnable{

		private Socket socket;
		
		public SocketProcessor(Socket socket) {
			this.socket = socket;
		}



		public void run() {
			try {
				
				System.out.println("处理请求");
				
				InputStream inputStream = this.socket.getInputStream();
				
				byte[] bytes = new byte[inputStream.available()];
				
				inputStream.read(bytes);
				
				System.out.println(new String(bytes));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void initCreateSocket(){
		
		sslServerSocketFactory = (SSLServerSocketFactory) sslContext.getServerSocketFactory();
		
		try {
			
			SSLServerSocket createServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
			
			createServerSocket.setNeedClientAuth(true);
			
			String[] enabledCipherSuites = createServerSocket.getEnabledCipherSuites();
			
			createServerSocket.setEnabledCipherSuites(enabledCipherSuites);
			
			while(true){
				
				Socket accept = createServerSocket.accept();
				
				System.out.println("收到请求："+accept);
				
//				new Thread(new SocketProcessor(accept)).start();
				
				InputStream inputStream = accept.getInputStream();
				
				byte[] bytes = new byte[inputStream.available()];
				
				inputStream.read(bytes);
				
				System.out.println(new String(bytes));
				
				OutputStream outputStream = accept.getOutputStream();
				
				outputStream.write("welcome !".getBytes());
				
				outputStream.flush();
				
//				outputStream.close();
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startServer(){
		createSSlContext();
		initCreateSocket();
	}

	public static void main(String[] args) {
		
		SSLBIOServer server = new SSLBIOServer(8081);
		
		server.startServer();
		
		
	}

	public SSLBIOServer(int port) {
		this.port = port;
	}

}
