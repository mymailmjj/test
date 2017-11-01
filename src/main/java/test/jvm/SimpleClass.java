/**
 * 
 */
package test.jvm;

/**
 * @author mujjiang
 *
 */
public class SimpleClass {
	
	private int a;
	
	private String name;

	public SimpleClass(int a, String name) {
		this.a = a;
		this.name = name;
	}

	@Override
	public String toString() {
		return "SimpleClass [a=" + a + ", "
				+ (name != null ? "name=" + name : "") + "]";
	}
	
}
