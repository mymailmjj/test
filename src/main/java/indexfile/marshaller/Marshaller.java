/**
 * 
 */
package indexfile.marshaller;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * @author az6367
 *
 */
public interface Marshaller<T> {
	
	public T readPayload(DataInput in);
	
	public void writePayload(T t,DataOutput os);

}
