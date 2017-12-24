/**
 * 
 */
package test.valve;

/**
 * @author mymai
 *
 */
public class ThirdValve implements Valve<String> {
	
	private Valve<String> next;

	public void setNext(Valve<String> v) {
		this.next = v;
	}

	public Valve<String> getNext() {
		return next;
	}

	public void invoke(String v) {
		String replace = v.replace("b", "n");
		next.invoke(replace);
	}

}
