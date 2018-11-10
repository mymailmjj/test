/**
 * 
 */
package indexfile;

/**
 * @author az6367
 *
 */
public interface FileOper<T> {

	public void write(T t);

	public T read(long id);

	public void close();

}
