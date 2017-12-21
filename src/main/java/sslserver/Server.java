package sslserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

	private ExecutorService newFixedThreadPool;

	private Lock lock = new ReentrantLock();

	public Server() {

	}

	public void start() {

		newFixedThreadPool = Executors.newFixedThreadPool(5);

		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel
					.open();

			serverSocketChannel.bind(new InetSocketAddress("localhost", 8081));

			serverSocketChannel.configureBlocking(false);

			Selector selector = Selector.open();

			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			Service service = new Service(selector);

			newFixedThreadPool.execute(service);

			for (int i = 0; i < 1; i++) {

				KeyKiller keyKiller1 = new KeyKiller();
				newFixedThreadPool.execute(keyKiller1);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		newFixedThreadPool.shutdown();

	}

	//用于读取任务的缓存
	private CopyOnWriteArrayList<SelectionKey> keysets = new CopyOnWriteArrayList();
	
	/**
	 * 用于去处理的缓存
	 */
	private CopyOnWriteArrayList<SelectionKey> processorCache = new CopyOnWriteArrayList();

	class KeyKiller implements Runnable {

		public void run() {

			// check keysets

			while (true) {

				if (!keysets.isEmpty()) {

					lock.lock();

					ListIterator<SelectionKey> iterator = keysets
							.listIterator();

					if (iterator.hasNext()) {

						SelectionKey key = iterator.next();

						if (key.isAcceptable()) {
							System.out.println(Thread.currentThread().getName()
									+ "线程:key:" + key + "\taccept");
							hanleAccept(key);

						} else if (key.isReadable()) {
							System.out.println(Thread.currentThread().getName()
									+ "线程:key:" + key + "\tread");
							hanleRead(key);
						}

						// iterator.remove();
						keysets.remove(key);
					}

					lock.unlock();

				} else {
					try {
						TimeUnit.MICROSECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}

	public void hanleAccept(SelectionKey key) {
		try {
			ServerSocketChannel socketChannel = (ServerSocketChannel) key
					.channel();

			SocketChannel accept = socketChannel.accept();

			accept.configureBlocking(false);

			accept.register(key.selector(), SelectionKey.OP_READ);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hanleRead(SelectionKey key) {
		try {
			SocketChannel socketChannel = (SocketChannel) key.channel();

			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

			int index = socketChannel.read(byteBuffer);

			System.out.print("输入:");

			while (index > 0) {

				byteBuffer.flip();

				while (byteBuffer.hasRemaining()) {

					byte b = byteBuffer.get();

					System.out.print((char) b);

				}

				System.out.println();

				byteBuffer.compact();

				index = socketChannel.read(byteBuffer);
			}

			if (index <= 0) {
				socketChannel.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	class SocketProcessor implements Runnable{
		
		private SocketChannel socketChannel;
		
		public SocketProcessor(SocketChannel socketChannel) {
			this.socketChannel = socketChannel;
		}


		//参数的准备，最后去调用协议处理器
		public void run() {
			
			
			
			
		}
		
	}
	

	class Service implements Runnable {

		private Selector selector;

		public Service(Selector selector) {
			this.selector = selector;
		}

		public void run() {

			while (true) {

				try {
					if (selector.selectNow() != 0) {
						Set<SelectionKey> selectedKeys = selector
								.selectedKeys();

						Iterator<SelectionKey> iterator = selectedKeys
								.iterator();

						while (iterator.hasNext()) {

							SelectionKey key = iterator.next();

							if (!keysets.contains(key)) {
								keysets.add(key);
							}

							iterator.remove();

						}
					}

					//
					/*
					 * if(selector.selectNow()!=0) { Thread.yield(); }
					 */

					TimeUnit.MICROSECONDS.sleep(1);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

	}

	public static void main(String[] args) {

		Server server = new Server();

		server.start();

	}

}
