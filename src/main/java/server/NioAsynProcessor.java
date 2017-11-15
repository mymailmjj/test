/**
 * 
 */
package server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * 
 * 使用nio的多个线程的实现，核心处理器
 * 
 * @author mujjiang
 * 
 */
public class NioAsynProcessor implements Runnable {

	private final static int BUFFER_SIZE = 1024;
	
	private final static int TIME_OUT = 2000;
	
	private ServerSocketChannel serverSocketChannel;
	
	private ThreadLocalSelectHolder selectorLocal = new ThreadLocalSelectHolder();
	
	class SelectHolder{
		Selector selector;
		
		SelectHolder(){
			try {
				selector = Selector.open();
				System.out.println(selector+"注册"+serverSocketChannel);
				serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	class ThreadLocalSelectHolder extends ThreadLocal<SelectHolder>{

		@Override
		protected SelectHolder initialValue() {
			return new SelectHolder();
		}
		
	}
	
	public static void handleAccept(SelectionKey key) throws IOException {
		ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
		SocketChannel sc = ssChannel.accept();
		sc.configureBlocking(false);
		sc.register(key.selector(), SelectionKey.OP_READ,
				ByteBuffer.allocateDirect(BUFFER_SIZE));
	}

	public static void handleRead(SelectionKey key) throws IOException {
		SocketChannel sc = (SocketChannel) key.channel();
		ByteBuffer buf = (ByteBuffer) key.attachment();
		long bytesRead = sc.read(buf);
		while (bytesRead > 0) {
			buf.flip();
			while (buf.hasRemaining()) {
				System.out.print((char) buf.get());
			}
			System.out.println();
			buf.compact();
			bytesRead = sc.read(buf);
		}

		// 这个代码结束
		if (bytesRead == -1) {
			sc.close();
		}
	}


	public NioAsynProcessor(ServerSocketChannel serverSocketChannel) {
		this.serverSocketChannel = serverSocketChannel;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		while (true) {

			SelectHolder selectHolder = selectorLocal.get();
			
			System.out.println(Thread.currentThread().getName()+"获取到selector:"+selectHolder.selector);
			
			// 如果没有key，则等待
			try {
				if (selectHolder.selector.select(TIME_OUT) == 0) {
					System.out.println(Thread.currentThread().getName()+" ====");
					continue;
				}
				
				// 这里的方法是阻塞的，请求的时候会触发想要的keytype,然后继续往下执行
				Set<SelectionKey> selectedKeys = selectHolder.selector.selectedKeys();

				Iterator<SelectionKey> iterator = selectedKeys.iterator();

				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					if (key.isAcceptable()) {
						handleAccept(key);
					} else if (key.isReadable()) {
						handleRead(key);
					} else if (key.isWritable()) {
						// handleWrite(key);
					} else if (key.isConnectable()) {

					}

					iterator.remove();

				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
