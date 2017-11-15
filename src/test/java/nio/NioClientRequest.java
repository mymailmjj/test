package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NioClientRequest {

	public static void main(String[] args) throws IOException {
		
		SocketChannel socketChannel = SocketChannel.open();
		
		socketChannel.configureBlocking(false);
		
		socketChannel.connect(new InetSocketAddress(8081));
		
		
		Selector selector = Selector.open();
		
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		
		while(true){
			
			if(selector.select()==0){
				continue;
			}
			
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			
			while(iterator.hasNext()){
				
				SelectionKey key = iterator.next();
				
				System.out.println("key status:"+key.interestOps());
				
				if(key.isConnectable()){
					
					while(socketChannel.finishConnect()){
						
						for(int i = 0 ; i< 5; i++){
							
							ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
							
							byteBuffer.clear();
							
							String str = "client send "+i;
							
							byteBuffer.put(str.getBytes());
							
							byteBuffer.flip();
							
							while(byteBuffer.hasRemaining()){
								socketChannel.write(byteBuffer);
							}
							
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}finally{
								
							}
							
						}
						
						break;
						
					}
					
				}
				
				iterator.remove();
				
			}
			
			socketChannel.close();
		}

	}

}
