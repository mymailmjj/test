/**
 * 
 */
package test.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author mujjiang
 * 
 */
public class Main {

	static List<byte[]> lists = new ArrayList<byte[]>();

	public static void main(String[] args) {

		for (int i = 0; i < 100; i++) {
			try {
				byte[] bytes = new byte[2 * 1024 * 1024];
				System.out.println("添加bytes:"+bytes);
				lists.add(bytes);
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
