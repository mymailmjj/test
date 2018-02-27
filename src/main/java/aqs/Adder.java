/**
 * 
 */
package aqs;

import java.util.concurrent.locks.Lock;

/**
 * @author cango
 * 
 */
public class Adder implements Runnable {

	private Bag bag;

	private Synchronizer synchronizer;
	
	private int sum = 0;

	public Adder(Bag bag) {
		this.bag = bag;
	}

	public Adder(Bag bag, Synchronizer lock) {
		this.bag = bag;
		this.synchronizer = lock;
	}

	public void addNum() {
		synchronizer.lock();
		int i = bag.getNum() + 1;
		sum+=i;
		bag.setNum(i);
		System.out.println(Thread.currentThread().getName() + "加1,結果："
				+ bag.getNum()+"\tsum:"+sum);
		synchronizer.unlock();
	}

	public void run() {
		addNum();
	}

}
