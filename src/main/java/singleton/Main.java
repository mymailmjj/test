/**
 * 
 */
package singleton;

/**
 * @author cango
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ClassLoader classLoader = Main.class.getClassLoader();
		
		try {
			Class<StaticSingleton> singleton = (Class<StaticSingleton>) classLoader.loadClass("singleton.StaticSingleton");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
