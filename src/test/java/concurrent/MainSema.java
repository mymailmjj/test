/**
 * 
 */
package concurrent;

import java.util.concurrent.Semaphore;

/**
 * @author mymai
 *
 */
public class MainSema {
	
	public static void main(String[] args) {
		
		Semaphore semaphore = new Semaphore(5);
		
		Thread[] threads = new Thread[20];
		
		for(int i =0; i<20;i++){
			threads[i] = new Thread(new Road(semaphore));
		}
		
		for(int i =0; i<20;i++){
			threads[i].start();
		}
		
		
	}

}
