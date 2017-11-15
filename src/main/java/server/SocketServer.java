package server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用socket实现的server，阻塞行
 * BIO
 * @author mujjiang
 *
 */
public class SocketServer {
	
	public static void main(String[] args){
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8081);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true){
			try {
				Socket clientSocket = serverSocket.accept();
				
				InputStream inputStream = clientSocket.getInputStream();
				BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
				
				StringBuffer string = new StringBuffer();
				
				int index = 0;
				
				byte[] bytes = new byte[1024];
				
				
				while((index = bufferedInputStream.read(bytes))!=-1){
					String str = new String(bytes);
					System.out.println(str);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

}
