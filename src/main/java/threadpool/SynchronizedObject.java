/**
 * 
 */
package threadpool;

/**
 * 用于测试类锁和对象锁的区别
 * @author mymai
 *
 */
public class SynchronizedObject {

	
	public static synchronized void staticMethodA(){
		for(int i = 0; i<20;i++){
			System.out.println("staticMethodA:"+i);
		}
	}
	
	public static synchronized void staticMethodB(){
		for(int i = 0; i<20;i++){
			System.out.println("staticMethodB:"+i);
		}
	}
	
	public synchronized void methodA(){
		for(int i = 0; i<20;i++){
			System.out.println("methodA:"+i);
		}
	}
	
	public synchronized void methodB(){
		for(int i = 0; i<20;i++){
			System.out.println("methodB:"+i);
		}
	}
	
}
