package nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class Request {

	/**
	 * 使用socketnio的方式请求
	 */
	public static void requestnio() {
		Thread currentThread = Thread.currentThread();
		System.out.println(currentThread.getName() + "启动");
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		SocketChannel socketChannel = null;
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			socketChannel.connect(new InetSocketAddress("localhost", 8081));

			if (socketChannel.finishConnect()) {
				int i = 0;

				while (true) {
					TimeUnit.SECONDS.sleep(3);
					String info = "I'm information from client" + i
							+ "\t threadname:" + currentThread.getName();
					buffer.clear();
					buffer.put(info.getBytes());
					buffer.flip();
					while (buffer.hasRemaining()) {
						socketChannel.write(buffer);
					}

					if (i++ == 3)
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (socketChannel != null) {
					socketChannel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 使用socket的方式请求
	 */
	public static void requestSocket() {
		Thread currentThread = Thread.currentThread();
		System.out.println(currentThread.getName() + "启动");

		Socket socket = null;

		try {
			socket = new Socket("39.107.103.45", 8081);

			OutputStream outputStream = socket.getOutputStream();

			int i = 1;

			// while(true){

			String c = "client thread:" + currentThread.getName()
					+ "send content:" + i;

			outputStream.write(c.getBytes());

			TimeUnit.SECONDS.sleep(1);
			// }

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
