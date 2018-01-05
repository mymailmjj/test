/**
 * 
 */
package aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author cango
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Synchronizer synchronizer = new Synchronizer();

		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

		Bag bag = new Bag();

		Adder adder = new Adder(bag, synchronizer);

		// Suber suber = new Suber(bag,lock);

		for (int i = 0; i < 100; i++) {
			newFixedThreadPool.execute(adder);
		}

		// for (int i = 0; i < 10; i++) {
		// newFixedThreadPool.execute(suber);
		// }

		newFixedThreadPool.shutdown();

		while (!newFixedThreadPool.isTerminated()) {

		}

		System.out.println(bag.getNum());

	}

}
