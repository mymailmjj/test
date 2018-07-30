/**
 * 
 */
package algorithm.random;

/**
 * @author az6367
 *
 */
public class Random {
	
	private static int offset = 10;  //干扰源
	
	public static int genNum(){
		
		//使用时间作为seed
		long currentTimeMillis = System.currentTimeMillis();  
		
		int a0 = (int) (currentTimeMillis%100000+offset++);
		
		int b = 25;
		
		int c= 53;
		
		int m = 253;
		
		int an = (b*a0+c)%m;
		
		return an;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(genNum());
		System.out.println(genNum());
		System.out.println(genNum());
		
		
		
	}

}
