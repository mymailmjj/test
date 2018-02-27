/**
 * 
 */
package test.valve;

/**
 * @author mymai
 *
 */
public interface Valve<T> {
	
	public void setNext(Valve<T> v);
	
	public Valve<T> getNext();
	
	public void invoke(T v);

}
