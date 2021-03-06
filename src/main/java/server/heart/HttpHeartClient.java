/**
 * 
 */
package server.heart;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author cango
 * 
 */
public class HttpHeartClient {

	private int port;
	
	private String host = "127.0.0.1";

	private CountDownLatch countDownLatch = new CountDownLatch(1);

	public HttpHeartClient(int port) {
		this.port = port;
		init();
	}
	

	public HttpHeartClient(String host,int port) {
        this.port = port;
        this.host = host;
    }



    class HeartBeat implements Runnable{

		public void run() {
			
			while(true){
				
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				
				try {
					long bytesRead = socketChannel.read(buffer);
					while (bytesRead > 0) {
						buffer.flip();
						while (buffer.hasRemaining()) {
							System.out.print((char) buffer.get());
						}
						System.out.println();
						buffer.compact();
						bytesRead = socketChannel.read(buffer);
					}
					buffer.compact();
					buffer.clear();
				} catch (IOException e) {
					e.printStackTrace();
					try {
						socketChannel.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					break;
				}
				
			}
			
		}
		
	}
	
	
	private void init() {

		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			socketChannel.connect(new InetSocketAddress(host, port));

			if (socketChannel.finishConnect()) {

				countDownLatch.countDown();

				Thread thread = new Thread(new ServerIdleCheck());

				thread.setDaemon(true);

				thread.start();
				
				Thread thread1 = new Thread(new HeartBeat());

				thread1.setDaemon(true);

				thread1.start();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void send(String str) {

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		byte[] bytes = str.getBytes();

		ByteBuffer buffer = ByteBuffer.allocate(bytes.length);

		buffer.put(bytes);

		buffer.flip();

		try {
			socketChannel.write(buffer);
			System.out.println("消息已经发送");
			socketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private SocketChannel socketChannel;

	class ServerIdleCheck implements Runnable {

		Thread currentThread = Thread.currentThread();

		ByteBuffer buffer = ByteBuffer.wrap("client heat beat".getBytes());

		public void run() {

			int i = 0;

			while (true) {

				try {
					socketChannel.write(buffer);
					buffer.clear();
					// 每隔三秒钟发送一次
					TimeUnit.SECONDS.sleep(2);
					
				} catch (IOException e) {
					e.printStackTrace();
					
					try {
						socketChannel.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					break;
					
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}
			}
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		HttpHeartClient httpHeartClient = new HttpHeartClient("39.107.103.45",8081);

		httpHeartClient.send("abcd");

	}

}
