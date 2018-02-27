/**
 * 
 */
package test.valve;

/**
 * @author mymai
 *
 */
public interface Pipe<T> {
	
	public void setBasic(Valve<T> v);
	
	public void addValve(Valve<T> v);
	
	public Valve<T> getBasic();
	
	
	public Valve<T> getFirst();
	
	

}
