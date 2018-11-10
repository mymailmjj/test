/**
 * 
 */
package simpledb;

/**
 * @author mymai
 *
 */
public interface FileOper<T> {
	
	public void write(T t);
	
	public T read(int id);
	
	public T[] readArrays();
	
	public void close();

}
