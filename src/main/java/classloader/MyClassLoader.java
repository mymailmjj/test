package classloader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader{
	
	

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		
		if(name.equals("singleton.StaticSingleton")||name.equals("singleton.StaticSingleton$StaticSingletionHolder")){
			
			Class<?> findClass = findClass(name);
			
			if(findClass!=null){
				return findClass;
			}
		}
		
		return super.loadClass(name);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		
		
		//这里自己定义查找class的办法
		InputStream inputStream = null;
		try {
			
			
			//StaticSingleton$StaticSingletionHolder.class
			
			String path = null;
			
			if(name.equals("singleton.StaticSingleton")){
				inputStream = new FileInputStream("C://Users//cango//git//test//target//classes//singleton//StaticSingleton.class");
			}else{
				inputStream = new FileInputStream("C://Users//cango//git//test//target//classes//singleton//StaticSingleton$StaticSingletionHolder.class");
			}
			
			
			byte[] bytes = new byte[inputStream.available()];
			
			inputStream.read(bytes);
			
			Class<?> defineClass = defineClass(name,bytes, 0, bytes.length);
			
			return defineClass;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return super.findClass(name);
	}
	
	

}
