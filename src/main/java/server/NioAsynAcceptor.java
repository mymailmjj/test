package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NioAsynAcceptor {
	
	private static Logger logger = Logger.getLogger("server.NioAsynAcceptor");
	
	private List<NioAsynProcessor> lists = null;
	
	private Thread[] thread;
	
	private int size;

	public NioAsynAcceptor(int size) {
		this.size = size;
		lists = new ArrayList<NioAsynProcessor>(size);
		thread = new Thread[5];
	}
	
	private void initThread(ServerSocketChannel serverSocketChannel){
		
		for(int i = 0; i<size; i++){
			Thread t = new Thread(new NioAsynProcessor(serverSocketChannel)," thread-"+i);
			thread[i] = t;
			logger.info("初始化线程:"+t.getName());
		}
		
		for(int i = 0; i<thread.length; i++){
			Thread t = thread[i];
			logger.info("启动线程:"+t.getName());
			t.start();
		}
		
	}
	
	public void start(){
		init();
	}
	
	private void init(){
		
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

			serverSocketChannel.bind(new InetSocketAddress(8081));

			serverSocketChannel.configureBlocking(false);

			initThread(serverSocketChannel);
			
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// private static final int TIMEOUT = 3000;

	public static void main(String[] args) throws IOException {
		
		NioAsynAcceptor NioAsynAcceptor = new NioAsynAcceptor(5);
		
		NioAsynAcceptor.start();

	}


}
