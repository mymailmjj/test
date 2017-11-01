/**
 * 
 */
package test.jvm;

/**
 * @author mujjiang
 *
 */
public class Singletion1 {
	
	private static Singleton sing = null;
	
	static{
		sing = new Singleton();
	}
	
	
	public static Singleton instance(){
		return sing;
	}
	
	
	static class Singleton{
		private Singleton(){
			
		}
	}

}
