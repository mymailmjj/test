/**
 * 
 */
package utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author mymai
 *
 */
public class NumberUtils {
	
	
	public static int generateNumbers(int maxNum){
		ThreadLocalRandom current = ThreadLocalRandom.current();
		int nextInt = current.nextInt(maxNum);
		return nextInt;
	}
	

}
