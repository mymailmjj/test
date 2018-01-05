/**
 * 
 */
package aqs;

import java.util.concurrent.locks.Lock;

/**
 * @author cango
 *
 */
public class Suber implements Runnable{
	
	private Bag bag;
	
	private Lock lock;

	public Suber(Bag bag, Lock lock) {
		this.bag = bag;
		this.lock = lock;
	}

	public Suber(Bag bag) {
		this.bag = bag;
	}
	
	public void sub(){
		int i = bag.getNum()-1;
		bag.setNum(i);
		System.out.println(Thread.currentThread().getName()+"減1,結果："+bag.getNum());
	}

	public void run() {
		sub();
	}

}
