/**
 * 
 */
package sslserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author cango
 * 基于sslsocket方式的请求客户端
 *
 */
public class SSLClient {
	
	private final static String PASSWORD = "123456";
	
	private SSLSocket sslSocket;
	
	
	private void printDetailSocket(SSLSocket sslSocket) {

		SSLParameters sslParameters = sslSocket.getSSLParameters();

		String[] protocols = sslParameters.getProtocols();

		System.out.println("protocols:" + Arrays.toString(protocols));

	/*	System.out.println("支持的协议: "
				+ Arrays.asList(sslSocket.getSupportedProtocols()));
		System.out.println("启用的协议: "
				+ Arrays.asList(sslSocket.getEnabledProtocols()));
		System.out.println("支持的加密套件: "
				+ Arrays.asList(sslSocket.getSupportedCipherSuites()));
		System.out.println("启用的加密套件: "
				+ Arrays.asList(sslSocket.getEnabledCipherSuites()));*/

	}
	
	
	
	/**
	 * 
	 */
	public SSLClient() {
		createSSlContext();
		SSLContext createSSlContext = createSSlContext();
		SSLSocketFactory socketFactory = createSSlContext.getSocketFactory();
		try {
			sslSocket = (SSLSocket) socketFactory.createSocket("localhost", 8443);
			
			sslSocket.addHandshakeCompletedListener(new HandShakeListener());
			
			SSLParameters sslParameters = sslSocket.getSSLParameters();
			
			String[] protocols = sslParameters.getProtocols();
			
			System.out.println("protocols:"+Arrays.toString(protocols));
			
			printDetailSocket(sslSocket);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startClient(){
		try {
			OutputStream outputStream = sslSocket.getOutputStream();
			
			System.out.println("开始发送");
			
			outputStream.write("client abc".getBytes());
			
			System.out.println("发送结束");
			
			outputStream.flush();
			
			InputStream inputStream = sslSocket.getInputStream();
			
			System.out.println(inputStream);
			
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String str = null;
			
			while((str = bufferedReader.readLine())!=null){
				System.out.println("read str form server:"+str);
			}
					
		
			
			bufferedReader.close();
			
			outputStream.close();
			
			sslSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private SSLContext createSSlContext(){
		
		SSLContext sslContext;
		try {
			// 先设置keymanager
			KeyManagerFactory keyManagerFactory = KeyManagerFactory
					.getInstance("SunX509");

			String defaultType = KeyStore.getDefaultType();

			KeyStore keyStore = KeyStore.getInstance(defaultType);

			FileInputStream fileInputStream = new FileInputStream("D:\\ssl\\kclient.ks");

			keyStore.load(fileInputStream, null);

			keyManagerFactory.init(keyStore, PASSWORD.toCharArray());

			KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

			TrustManagerFactory trustFactory = TrustManagerFactory.getInstance("SunX509");
			
			
			KeyStore keyStore1 = KeyStore.getInstance(defaultType);

			FileInputStream fileInputStream1 = new FileInputStream("D:\\ssl\\tclient.ks");

			keyStore1.load(fileInputStream1, PASSWORD.toCharArray());
			
			trustFactory.init(keyStore1);

			TrustManager[] trustManagers = trustFactory.getTrustManagers();

			sslContext = SSLContext.getInstance("SSL");

			sslContext.init(keyManagers, trustManagers, null);
			return sslContext;
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
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
		}
		

		return null;
		
	}
	
	
	public static void main(String[] args) {
		
		SSLClient sslClient = new SSLClient();
		sslClient.startClient();
		
	}

}
