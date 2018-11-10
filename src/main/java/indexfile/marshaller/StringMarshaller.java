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
public class StringMarshaller implements Marshaller<String> {
	
	private StringMarshaller(){
		
	}
	
	public static StringMarshaller instance = new StringMarshaller();
	

	@Override
	public String readPayload(DataInput in) {
		try {
			return in.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void writePayload(String str,DataOutput os) {
		try {
			os.writeBytes(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
