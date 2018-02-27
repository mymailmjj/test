/**
 * 
 */
package concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author mymai
 *
 */
public class Road implements Runnable {

	public Road(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	Semaphore semaphore;

	public void run() {
		try {
			semaphore.acquire();
			String name = Thread.currentThread().getName();
			System.out.println("线程" + name + "开始通过");
			int nextInt = ThreadLocalRandom.current().nextInt(10);
			System.out.println("线程" + name + "需要花费：" + nextInt + "秒");
			TimeUnit.SECONDS.sleep(nextInt);
			System.out.println("线程" + name + "已经通过");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		semaphore.release();
	}

}
