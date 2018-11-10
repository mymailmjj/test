/**
 * 
 */
package indexfile.tree;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * @author az6367
 *
 */
public interface Marshaller<Key,Value> {
	
	public void readPayload(DataInput in);
	
	public void writePayload(DataOutput os);

}
