/**
 * 
 */
package nio;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mujjiang
 * nio客户端代码，访问nioserver
 */
public class SocketTest {
	
	public static void main(String[] args) throws UnknownHostException, IOException, Exception {
		
		int threadnum = 100;
		
		//几个线程并发的访问
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(threadnum);
		
		for(int i = 0; i < threadnum;i++){
			newFixedThreadPool.execute(new ClientRequest());
		}
		
		newFixedThreadPool.shutdown();
		
		/**
		 * 几个线程串行执行
		 */
	/*	Thread t1 = new Thread(new ClientRequest());
		
		Thread t2 = new Thread(new ClientRequest());
		
		t1.start();
		
		t2.start();
		
		t1.join();
		
		t2.join();*/
		
		
	}

}
