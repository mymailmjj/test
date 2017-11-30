/**
 * 
 */
package server.acceptor;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import server.NioAsynProcessor;

/**
 * @author mujjiang
 * 
 */
public class AcceptorRunner {

	private static Logger logger = Logger
			.getLogger("server.acceptor.AcceptorRunner");

	private List<NioAsynProcessor> lists = null;

	private Thread[] thread;

	private int size;

	public AcceptorRunner(int size) {
		this.size = size;
		lists = new ArrayList<NioAsynProcessor>(size);
		thread = new Thread[size];
	}

	/**
	 * 初始化
	 * 
	 */
	public void init() {
		initThread();
	}

	/**
	 *  启动容器
	 */
	public void start() {
		for (int i = 0; i < size; i++) {
			Thread t = thread[i];
			logger.info("启动acceptor线程:" + t.getName());
			t.start();
		}
	}

	private void initThread() {
		System.out.println("adddd");
		for (int i = 0; i < size; i++) {

			NioAsynProcessor nioAsynProcessor = new NioAsynProcessor();
			Thread t = new Thread(nioAsynProcessor, " thread-" + i);
			thread[i] = t;
			logger.info("初始化线程:" + t.getName());

			lists.add(nioAsynProcessor);
		}

	}
	
	
	/**
	 * connector把socket提交给acceptor
	 * @param socketChannel
	 */
	public void submitJob(SocketChannel socketChannel){
		
		//先选出processor
		ListIterator<NioAsynProcessor> listIterator = lists.listIterator();
		
		while(listIterator.hasNext()){
			NioAsynProcessor nioAsynProcessor = listIterator.next();
			
			boolean busy = nioAsynProcessor.isBusy();
			
			if(!busy){
				logger.info("找到acceptor:" + nioAsynProcessor);
				//提交nio
				nioAsynProcessor.read(socketChannel);
				break;
			}
		}
		
		
	}

}
