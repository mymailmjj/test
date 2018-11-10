/**
 * 
 */
package indexfile.message;

/**
 * @author az6367
 *
 */
public class MessageFactory {
	
	
	public static Message createMessage(int type,byte[] bytes){
		
		Message message = null;
		
		switch (type) {
		case Message.TYPE_TEXT:
			message = new TextMessage();
			message.parse(bytes);
			break;

		default:
			break;
		}
		
		return message;
	}

}
