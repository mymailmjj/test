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
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author mymai
 *
 */
public class SSLSocketClient {
	
	private SSLContext sslContext;
	
	private SSLSocket sslSocket;
	
	private int port;
	
	

	public SSLSocketClient(int port) {
		this.port = port;
	}
	
	private void createSocket(){
		
		SSLSocketFactory socketFactory = sslContext.getSocketFactory();
		try {
			sslSocket = (SSLSocket) socketFactory.createSocket("localhost", port);
			
			String[] enabledCipherSuites = sslSocket.getEnabledCipherSuites();
			
			sslSocket.setEnabledCipherSuites(enabledCipherSuites);
			
			OutputStream outputStream = sslSocket.getOutputStream();
			
			outputStream.write("abcde".getBytes());
			
			outputStream.flush();
			
			InputStream inputStream = sslSocket.getInputStream();
			
			byte[] bytes = new byte[inputStream.available()];
			
			inputStream.read(bytes);
			
			System.out.println("receive server:"+new String(bytes));
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void createSSlContext(){
		try {
			sslContext = SSLContext.getInstance("ssl");
			
			KeyStore keyStore = KeyStore.getInstance("jks");
			
			FileInputStream fileInputStream = new FileInputStream("D://security//my.keystore");
			
			keyStore.load(fileInputStream, null);;
			
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			
			keyManagerFactory.init(keyStore, "123456".toCharArray());
			
			KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
			
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
			
			KeyStore keyStore1 = KeyStore.getInstance("jks");
			
			FileInputStream fileInputStream1 = new FileInputStream("D://security//tomcat.keystore");
			
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
	

	public SSLSocket startSlient(){
		createSSlContext();
		createSocket();
		return this.sslSocket;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

			SSLSocketClient sslSocketClient = new SSLSocketClient(8081);
			
			SSLSocket startSlient = sslSocketClient.startSlient();
			
			/*OutputStream outputStream = startSlient.getOutputStream();
			
			outputStream.write("client abc".getBytes());
			
			outputStream.flush();*/

//			startSlient.close();
			
			try {
				TimeUnit.SECONDS.sleep(12);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
