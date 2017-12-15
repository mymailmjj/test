/**
 * 
 */
package singleton;

/**
 * @author mujjiang
 * 
 */
public class Singleton {
	

	private static volatile Singleton sing = null;

	public static Singleton instance() {
		if (sing == null) {
			synchronized (Singleton.class) {
				if (sing == null) {
					sing = new Singleton();
				}
			}

		}

		return sing;

	}
	
	public static void main(String[] args) {
		
		Singleton instance = Singleton.instance();
		
	}

}
