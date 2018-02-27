/**
 * 
 */
package test.valve;

/**
 * @author mymai
 *
 */
public class BasicValve implements Valve<String> {
	
	private Valve<String> v;
	
	public void setNext(Valve<String> v) {
		this.v = v;
		
	}

	public Valve<String> getNext() {
		return v;
	}

	public void invoke(String v) {
		System.out.println("最后的结果是"+v);
	}

}
