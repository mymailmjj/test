/**
 * 
 */
package indexfile;

/**
 * @author az6367
 *
 */
public class MessageId implements Comparable<MessageId> {
	
	private String messageId;
	
	private int id;
	
	public MessageId(String messageId, int id) {
		this.messageId = messageId;
		this.id = id;
	}

	@Override
	public int compareTo(MessageId o) {
		return this.id - o.id;
	}

}
