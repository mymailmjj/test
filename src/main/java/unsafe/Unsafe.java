/**
 * 
 */
package unsafe;

import java.lang.reflect.Field;


/**
 * @author mujjiang
 *
 */
public class Unsafe {
	
	private int value = 0;
	
	private static sun.misc.Unsafe unsfe = null;
	
	private static long valueoffset ;
	
	static{
		try {
			Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsfe = (sun.misc.Unsafe) field.get(null);
			Field declaredField = Unsafe.class.getDeclaredField("value");
			valueoffset = unsfe.objectFieldOffset(declaredField);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getAndIncrease(){
		for(;;){
			int current = get();
			int next = current + 1;
			if(compareAndSwap(current, next)){
				return next;
			}
		}
		
	}
	
	public boolean compareAndSwap(int expect,int current){
		boolean compareAndSwapInt = unsfe.compareAndSwapInt(this, valueoffset, expect, current);
		return compareAndSwapInt;
	}
	
	
	public int get(){
		return value;
	}
	
	
	public static void main(String[] args) {
		
		Unsafe ss =new Unsafe();
		int andIncrease = ss.getAndIncrease();
		System.out.println(andIncrease);
		
		
	}

}
