/**
 * 
 */
package singleton;

/**
 * 注意这里内部类的加载顺序
 * 
 * 内部类只有使用的时候才会被加载
 * 每个类加载的时候执行static方法，初始化变量
 * 这个方法利用了class loader只会加载一个class的情况
 * 但是存在一个问题，如果不同的类加载器则失败
 * @author cango
 *
 */
public class StaticSingleton {
	
	static{
		System.out.println("load father");
	}
	
	private StaticSingleton(){
		System.out.println("a");
		ClassLoader classLoader = this.getClass().getClassLoader();
		System.out.println("a class loader:"+classLoader);
	}
	
	
	private static class StaticSingletionHolder{
		
		static{
			System.out.println("load innser class");
		}
		
		private static StaticSingleton StaticSingleton = new StaticSingleton();
		
		
	}
	
	
	public static StaticSingleton instance(){
		System.out.println("调用StaticSingleton instance");
		return StaticSingletionHolder.StaticSingleton;
	}
	
	
	public static void main(String[] args) {
		
		StaticSingleton.instance();
		
	}
	

}
