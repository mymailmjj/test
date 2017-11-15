/**
 * 
 */
package server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author mujjiang
 *
 */
public class NioSyncServer {
	
	public static void main(String[] args) throws Exception {
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		
		serverSocketChannel.bind(new InetSocketAddress(8081));
		
		while(true){
			SocketChannel accept = serverSocketChannel.accept();
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			
			int index = accept.read(byteBuffer);
			
			while(index > 0){
				byteBuffer.flip();   //position  ......   limit ... capacity
				while(byteBuffer.hasRemaining()){
					byte b = byteBuffer.get();
					System.out.print((char)b);
				}
				
				System.out.println();
				
				byteBuffer.compact();
				
				index = accept.read(byteBuffer);
			}
			
			
		}
		
	}
	

}
