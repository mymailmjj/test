/**
 * 
 */
package selfxing;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bo.User;

/**
 * @author mujjiang
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void main(String[] args) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		User user = new User();
		
		BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
		
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			Method writeMethod = propertyDescriptor.getWriteMethod();
			Class<?> propertyType = propertyDescriptor.getPropertyType();
			if(propertyType==String.class){
				writeMethod.invoke(user, "abcd");
			}else if(int.class == propertyType ){
				writeMethod.invoke(user, 36);
			}
		}
		
		MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
		
		for (int i = 0; i < methodDescriptors.length; i++) {
			MethodDescriptor methodDescriptor = methodDescriptors[i];
			
		}
		
		System.out.println(user);
		
		
	}

}
