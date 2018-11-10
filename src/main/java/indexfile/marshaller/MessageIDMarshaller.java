/**
 * 
 */
package indexfile.marshaller;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import indexfile.Location;
import indexfile.Messagekey;

/**
 * @author az6367
 *
 */
public class MessageIDMarshaller implements Marshaller<String> {
	
	public final static int leyLength= 16;
	
	private MessageIDMarshaller(){
		
	}
	
	public static MessageIDMarshaller instance = new MessageIDMarshaller();
	

	@Override
	public String readPayload(DataInput in) {
		try {
			byte[] bytes = new byte[leyLength];
			in.readFully(bytes);
			return new String(bytes).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void writePayload(String str,DataOutput os) {
		try {
			byte[] bytes = new byte[leyLength];
			byte[] bytes2 = str.getBytes();
			System.arraycopy(bytes2, 0, bytes, 0, bytes2.length);
			os.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String readBytes(byte[] bytes){
		byte[] bytes2 = new byte[leyLength];
		System.arraycopy(bytes, 0, bytes2, 0, bytes2.length);
		return new String(bytes2).trim();
	}
	
	
	public byte[] writeBytes(String str){
		byte[] bytes = new byte[leyLength];
		byte[] bytes2 = str.getBytes();
		System.arraycopy(bytes2, 0, bytes, 0, bytes2.length);
		return bytes;
	}
	

}
