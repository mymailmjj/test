/**
 * 
 */
package indexfile.marshaller;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import indexfile.Location;
import indexfile.Messagekey;
import indexfile.message.Message;
import indexfile.message.MessageFactory;

/**
 * @author az6367
 *
 */
public class MessageMarshaller implements Marshaller<Message> {
	
	private MessageMarshaller(){
		
	}
	
	public static MessageMarshaller instance = new MessageMarshaller();
	

	@Override
	public Message readPayload(DataInput in) {
			String messageKeyId = MessageIDMarshaller.instance.readPayload(in);
			Location location = LocationMarshaller.instance.readPayload(in);
//			Messagekey messagekey = new Messagekey(location, messageKeyId);
			return null;
	}
	
	
	public Message readPayload(DataInput in,Location location) {
		byte[] bytes = new byte[location.getSize()];
			try {
				int type = in.readInt();
				in.readFully(bytes);
				return MessageFactory.createMessage(type,bytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return null;
}
	
	

	@Override
	public void writePayload(Message l,DataOutput os) {
		try {
			os.writeInt(l.dataType());
			os.write(l.getByte());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
