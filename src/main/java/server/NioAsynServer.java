/**
 * 
 */
package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * nio实现的socketserver 样板代码
 * 这里用的是nio的异步操作
 * 单线程多通道
 * Not blocking IO Server
 * @author mujjiang
 * 
 */
public class NioAsynServer {

	private final static int BUFFER_SIZE = 1024;

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
		
	//  这个代码结束
	  if (bytesRead == -1) {
			sc.close();
		}
	}

	public static void handleWrite(SelectionKey key) throws IOException {
		ByteBuffer buf = (ByteBuffer) key.attachment();
		buf.flip();
		SocketChannel sc = (SocketChannel) key.channel();
		while (buf.hasRemaining()) {
			sc.write(buf);
		}
		buf.compact();
	}

	// private static final int TIMEOUT = 3000;

	public static void main(String[] args) throws IOException {

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		serverSocketChannel.bind(new InetSocketAddress(8081));

		serverSocketChannel.configureBlocking(false);

		Selector selector = Selector.open();

		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {

			// 如果没有key，则等待
			if (selector.select(2000) == 0) {
				System.out.println("====");
				continue;
			}

			//这里的方法是阻塞的，请求的时候会触发想要的keytype,然后继续往下执行
			Set<SelectionKey> selectedKeys = selector.selectedKeys();

			Iterator<SelectionKey> iterator = selectedKeys.iterator();

			while (iterator.hasNext()) {

				SelectionKey key = iterator.next();
				if (key.isAcceptable()) {
					handleAccept(key);
				} else if (key.isReadable()) {
					handleRead(key);
				} else if (key.isWritable()) {
					handleWrite(key);
				} else if (key.isConnectable()) {

				}

				iterator.remove();

			}

		}

	}

}
