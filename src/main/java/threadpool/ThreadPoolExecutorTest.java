/**
 * 
 */
package threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

/**
 * @author mymai
 *
 */
public class ThreadPoolExecutorTest extends TestCase{
	
	private class MyTask implements Runnable{
		
		private int i;
		
		public MyTask(int i) {
			this.i = i;
		}



		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName()+" current task "+i);
		}
		
	}
	
	
	/**
	 * 提交的任务必须能被线程调用处理，否则会被reject，所以maxNum一般会被设置到最大，即来多少任务开多少个线程。
	 */
	public void testSynchronizedQueue(){
		
		SynchronousQueue synchronousQueue = new SynchronousQueue<>();
		
		ThreadPoolExecutor executors = new ThreadPoolExecutor(5, 30, 0, TimeUnit.SECONDS, synchronousQueue);
		
		for(int i = 0; i < 100; i++){
			executors.execute(new MyTask(i));
		}
		
		executors.shutdown();
		
		
	}
	
	
	/**
	 * 有界的情况
	 * 默认是initsize，如果都忙的话，则创建新的线程，直到达到Max
	 * 然后存放到队列里面，如果队列达到极限，则reject
	 * 
	 */
	public void testArrayBlockQueue(){
		
		ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<>(20);
		
		
		ThreadPoolExecutor executors = new ThreadPoolExecutor(5, 30, 0, TimeUnit.SECONDS, arrayBlockingQueue);
		
		for(int i = 0; i < 100; i++){
			executors.execute(new MyTask(i));
		}
		
		executors.shutdown();
		
	}
	
	
	/**
	 * 无界的情况
	 * 线程池的大小一直是initSize，max无关，任务一直放入队列，一直处理
	 * 
	 */
	public void testLinkedListBlockedQueue(){
		
		LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue<>();
		
		ThreadPoolExecutor executors = new ThreadPoolExecutor(5, 30, 0, TimeUnit.SECONDS, linkedBlockingQueue);
		
		for(int i = 0; i < 100; i++){
			executors.execute(new MyTask(i));
		}
		
		executors.shutdown();
		
	}
	

}
