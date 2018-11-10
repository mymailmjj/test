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
public class MessagekeyMarshaller implements Marshaller<Messagekey> {
	
	private MessagekeyMarshaller(){
		
	}
	
	public static MessagekeyMarshaller instance = new MessagekeyMarshaller();
	

	@Override
	public Messagekey readPayload(DataInput in) {
			String messageKeyId = MessageIDMarshaller.instance.readPayload(in);
			Location location = LocationMarshaller.instance.readPayload(in);
			Messagekey messagekey = new Messagekey(location, messageKeyId);
			return messagekey;
	}

	@Override
	public void writePayload(Messagekey l,DataOutput os) {
			MessageIDMarshaller.instance.writePayload(l.getMessagekey(), os);
			Location location = l.getLocation();
			LocationMarshaller.instance.writePayload(location, os);
	}

}
