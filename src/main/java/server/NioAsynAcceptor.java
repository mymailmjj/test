package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import server.acceptor.AcceptorRunner;

/**
 * 
 * 对于tomcat源码的分析，、
 * 如何解耦nio socketchanel 和 selector的关系
 * Accepor -> 建立 socketchannel
 * PushEvent -> register read 
 * Poller -> processkey真正的处理请求的逻辑
 * 
 * 
 * 
 * 
 * @author mujjiang
 *
 */
public class NioAsynAcceptor {

	private static Logger logger = Logger.getLogger("server.NioAsynAcceptor");

	AcceptorRunner acceptorRunner;

	/**
	 * acceptor size
	 */
	private int size;

	public NioAsynAcceptor(int size) {
		this.size = size;
		acceptorRunner = new AcceptorRunner(size);
	}

	private void initThread() {

	}

	public void start() {
		init(); // 先初始化所有内容
		acceptorRunner.start();
	}

	private void initAcceptorRunner() {
		acceptorRunner.init();
	}

	/**
	 * 初始化用于监听socket的内容
	 * 
	 */
	private void initSocket() {
		new Thread(new Runnable() {

			public void run() {

				try {
					ServerSocketChannel serverSocketChannel = ServerSocketChannel
							.open();

					serverSocketChannel.bind(new InetSocketAddress(8081));

					logger.info("start socket listener...");

					// serverSocketChannel.configureBlocking(true);

					while(true){
						SocketChannel accept = serverSocketChannel.accept();
						logger.info("receive socket...");
						acceptorRunner.submitJob(accept);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	private void init() {

		initSocket();

		initThread();

		initAcceptorRunner();

	}

	// private static final int TIMEOUT = 3000;

	public static void main(String[] args) throws IOException {

		NioAsynAcceptor NioAsynAcceptor = new NioAsynAcceptor(5);

		NioAsynAcceptor.start();

	}

}
