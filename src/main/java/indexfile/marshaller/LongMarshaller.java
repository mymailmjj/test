/**
 * 
 */
package indexfile.marshaller;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author az6367
 *
 */
public class LongMarshaller implements Marshaller<Long> {
	
	private LongMarshaller(){
		
	}
	
	public static LongMarshaller instance = new LongMarshaller();
	
	@Override
	public Long readPayload(DataInput in) {
		try {
			return in.readLong();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}

	@Override
	public void writePayload(Long l,DataOutput os) {
		try {
			os.writeLong(l);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
