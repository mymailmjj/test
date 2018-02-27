/**
 * 
 */
package concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author mymai
 *
 */
public class Runner extends Thread {

	private String name;
	

	private CountDownLatch countDownLatch;

	public Runner(String name, CountDownLatch countDownLatch) {
		this.name = name;
		this.countDownLatch = countDownLatch;
	}

	public Runner(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println(name + " waiting...");
		countDownLatch.countDown();
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(name + "beging run");

	}

}
