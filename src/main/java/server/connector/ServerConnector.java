/**
 * 
 */
package server.connector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import server.acceptor.NioAcceptor;
import server.acceptor.SocketAcceptor;

/**
 * @author mujjiang
 * 
 */
public class ServerConnector {

	private static Logger logger = Logger
			.getLogger("server.connector.ServerConnector");

	public final static String BIO = "bio";

	public final static String NIO = "nio";

	private String acceptorType;

	private int size; // 接收器的线程数

	private List<SocketAcceptor> lists = null; // 接收器的容器

	private ServerSocket serverSocket = null;

	private ServerSocketChannel serverSocketChannel = null; // nio方式的

	private HashSet<Thread> hashset = null; // 线程池的容器

	private Class<?> c;
	
	private Class<?> socketClass;

	private int port;

	public ServerConnector(int port, String acceptorType, int size) {
		this.acceptorType = acceptorType;
		this.port = port;
		this.size = size;

		hashset = new HashSet<Thread>(size);

		lists = new ArrayList<SocketAcceptor>(size);
	}

	private void initServer() {
		setAcceptorType();
		String acceptorType2 = this.acceptorType;
		if (acceptorType2.equals(BIO)) {
			try {
				serverSocket = new ServerSocket(this.port);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				serverSocketChannel = ServerSocketChannel.open();
				serverSocketChannel.bind(new InetSocketAddress(port));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ServerConnector(int port,int size) {
		this(port, BIO, size);
	}

	public void setAcceptorType() {

		if (acceptorType == null) {
			acceptorType = BIO;
			logger.info("acceptorType为空，使用的BIO方式");
		}

		if (acceptorType.equals(BIO)) {
			try {
				Class<?> socketAcceptor = Class
						.forName("server.acceptor.SocketAcceptor");
				this.c = socketAcceptor;
				this.socketClass = ServerSocket.class;
				logger.info("使用的BIO方式");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {
				Class<?> socketAcceptor = Class
						.forName("server.acceptor.NioAcceptor");
				this.c = socketAcceptor;
				this.socketClass = ServerSocketChannel.class;
				logger.info("使用的NIO方式");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

	private void initThread() {
		
		if (acceptorType.equals(BIO)) {
			for (int i = 0; i < this.size; i++) {
				
				SocketAcceptor acceptor = new SocketAcceptor(serverSocket);
				String tname = "服务监听线程" + i;
				logger.info("connector添加线程:" + tname);
				Thread t = new Thread(acceptor, tname);
				hashset.add(t);
			}
		}else{
			for (int i = 0; i < this.size; i++) {
				NioAcceptor acceptor = new NioAcceptor(serverSocketChannel);
				String tname = "服务监听线程" + i;
				logger.info("connector添加线程:" + tname);
				Thread t = new Thread(acceptor, tname);
				hashset.add(t);
			}
		}
	}

	private void startThread() {

		Iterator<Thread> iterator = hashset.iterator();

		while (iterator.hasNext()) {
			Thread t = iterator.next();
			logger.info("启动线程:" + t.getName());
			t.start();
		}

	}

	private void init() {

		initServer();

		initThread();

		startThread();
	}

	/**
	 * 启动线程池
	 */
	public void start() {
		init();
	}

}
