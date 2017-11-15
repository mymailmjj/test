/**
 * 
 */
package server.acceptor;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * @author mujjiang
 *
 */
public class SocketAcceptor extends AbstractAcceptor implements Runnable{
	
	private static Logger logger = Logger.getLogger("server.SocketAcceptor");
	
	private ServerSocket serverSocket;
	
	public SocketAcceptor(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	
	public void run() {
	
		int i = 0;
		
		while(true){
			
			try {
				
				Thread currentThread = Thread.currentThread();
				
				Socket socket = serverSocket.accept();
				
				logger.info(currentThread.getName()+"线程接收任务");
				
				InputStream inputStream = socket.getInputStream();
				
				while(inputStream.available()==0){
					
				}
				
				int capacity = inputStream.available();
				
				byte[] bytes = new byte[capacity];
				
				inputStream.read(bytes);
				
				i++;
				
				System.out.println(currentThread.getName()+"线程读出"+new String(bytes)+"\t第"+i+"次操作");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	

}
