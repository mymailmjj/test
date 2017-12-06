/**
 * 
 */
package server1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author mujjiang
 *
 */
public class Client {
	
	public static void main(String[] args) {
		
		try {
			SocketChannel socketChannel = SocketChannel.open();
			
			socketChannel.connect(new InetSocketAddress("localhost", 8081));
			
			while(true) {
				
				if(socketChannel.finishConnect()) {
					
					ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
					
					byteBuffer.put("abcdef".getBytes());
					
					byteBuffer.flip();
					
					socketChannel.write(byteBuffer);
					
					break;
					
				}
				
			}
			
			socketChannel.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
