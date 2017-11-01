/**
 * 
 */
package difficultproblem;

/**
 * @author mujjiang
 *
 */
public class Test {
	
	
	public int getInt(){
		int x ;
		try {
			x =0;
			System.out.println("try");
			int c = 2/x;
			return x;
		} catch (Exception e) {
			x = 2;
			System.out.println("exception");
			return x;
		} finally{
			System.out.println("finally");
			x = 3;
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test t= new Test();
		int int1 = t.getInt();
		System.out.println(int1);

	}

}
