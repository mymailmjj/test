/**
 * 
 */
package singleton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import classloader.MyClassLoader;

/**
 * 
 * 注意内部类被加载时，要外部类和内部类一起加载
 * @author cango
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		MyClassLoader classLoader1 = new MyClassLoader();
		
		try {
			Class<StaticSingleton> singleton = (Class<StaticSingleton>) classLoader1.loadClass("singleton.StaticSingleton");
			
			//singleton.StaticSingleton.StaticSingletionHolder
			Class<?> singleton1 = classLoader1.loadClass("singleton.StaticSingleton$StaticSingletionHolder");
			
			Method declaredMethod = singleton.getDeclaredMethod("instance", null);
			
			Object sing1 = declaredMethod.invoke(null, null);
			
			System.out.println("sing1:"+sing1);
			
			ClassLoader classLoader = sing1.getClass().getClassLoader();
			
			System.out.println("sing1:"+sing1+"\tclassLoader:"+classLoader);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		MyClassLoader classLoader2= new MyClassLoader();
		
		try {
			Class<StaticSingleton> singleton4 = (Class<StaticSingleton>) classLoader2.loadClass("singleton.StaticSingleton");
			
			//singleton.StaticSingleton.StaticSingletionHolder
			classLoader2.loadClass("singleton.StaticSingleton$StaticSingletionHolder");
			
			Method declaredMethod = singleton4.getDeclaredMethod("instance", null);
			
			Object sing2 = declaredMethod.invoke(null, null);
			
			System.out.println("sing2:"+sing2);
			
			ClassLoader classLoader = sing2.getClass().getClassLoader();
			
			System.out.println("sing2:"+sing2+"\tclassLoader:"+classLoader);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}

}
