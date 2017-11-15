package server.acceptor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

/**
 * nio方式的acceptor
 * @author mujjiang
 *
 */
public class NioAcceptor extends AbstractAcceptor implements Runnable{
	
	private static Logger logger = Logger.getLogger("server.acceptor.NioAcceptor");
	
	private ServerSocketChannel serverSocketChannel;
	
	public NioAcceptor(ServerSocketChannel serverSocketChannel) {
		this.serverSocketChannel = serverSocketChannel;
	}


	public void run() {
		
		int i = 1;
		
		while(true){
			
			try {
				
				Thread currentThread = Thread.currentThread();
				
				SocketChannel socketChannel = serverSocketChannel.accept();
				
				logger.info(currentThread.getName()+"线程接收任务");
				
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				
				int read = socketChannel.read(buffer);
				
				while(read>0){
					buffer.flip();
					while(buffer.hasRemaining()){
						byte b = buffer.get();
						System.out.print((char)b);
						
					}
					
					buffer.compact();
					read = socketChannel.read(buffer);
				}
				
				System.out.println("\t线程"+currentThread.getName()+"\t第"+i+"次读取");
				i++;
				
				if(read < 0){
					socketChannel.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
