/**
 * 
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import server.acceptor.SocketAcceptor;

/**
 * @author mujjiang
 * 
 */
public class SocketConnector {

	private static Logger logger = Logger.getLogger("server.SocketConnector");

	private List<SocketAcceptor> lists = null;

	private ServerSocket serverSocket = null;

	private int size;

	private HashSet<Thread> hashset = null;

	public SocketConnector(int size) {
		try {
			serverSocket = new ServerSocket(8081);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.size = size;

		hashset = new HashSet<Thread>(size);

		lists = new ArrayList<SocketAcceptor>(size);

	}

	private void initThread() {

		for (int i = 0; i < this.size; i++) {
			SocketAcceptor acceptor = new SocketAcceptor(serverSocket);
			String tname = "服务监听线程" + i;
			logger.info("connector添加线程:" + tname);
			Thread t = new Thread(acceptor, tname);
			hashset.add(t);
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
