/**
 * 
 */
package aqs;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * aqs lock 锁研究
 * @author mymai
 *
 */
public class AqsLock {
	
	
	static class AddJob implements Runnable{
		
		private Add add;

		public AddJob(Add add) {
			this.add = add;
		}

		@Override
		public void run() {
			add.add();
		}
		
	}
	

	/**
	 *
	 * @author mymai
	 *
	 */
	static class Add{
		
		private Lock lock;
		
		public Add(Lock lock) {
			this.lock = lock;
		}

		private int i = 1;

		private int k = 2;

		public int getK() {
			return k;
		}

		public void setK(int k) {
			this.k = k;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public void add() {
			int j = 0;
			lock.lock();
			while (j++ < 3) {
				
				i++;
				try {
					TimeUnit.MINUTES.sleep(j * 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				k = k + i;
				
			}
			lock.unlock();
			System.out.println(Thread.currentThread().getName()+"j="+j);

		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LocalTime now2 = LocalTime.now();

		Lock lock = new ReentrantLock();

		Add add = new Add(lock);

		int num = 3;

		Thread[] thread = new Thread[num];

		for (int i = 0; i < thread.length; i++) {
			AddJob addjob = new AddJob(add);
			thread[i] = new Thread(addjob, "线程" + i);

		}

		for (int i = 0; i < thread.length; i++) {
			thread[i].start();
		}

		for (int i = 0; i < thread.length; i++) {
			try {
				thread[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		LocalTime n  = LocalTime.now();
		
		Duration cost = Duration.between(n, now2);
		
		System.out.println("执行耗时:"+cost.getSeconds()+"s");

		System.out.println(add.getI());

		System.out.println(add.getK());

	}

}
