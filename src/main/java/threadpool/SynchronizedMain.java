package threadpool;

/**
 * 测试对象锁和类锁的区别
 * @author mymai
 *
 */
public class SynchronizedMain {
	

	public static void main(String[] args) {
		
		SynchronizedObject synchronizedObject = new SynchronizedObject();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronizedObject.methodA();   //这里是对象锁，对象锁和类锁不会发生互斥，只有持有同一种锁才会互斥
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronizedObject.methodB();
			}
		}).start();
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SynchronizedObject.staticMethodB();  //这里是类锁，所有调用这个方法的线程都会发生同步
			}
		}).start();
		
		
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				SynchronizedObject.staticMethodA();
			}
		}).start();*/
		
	}

}
