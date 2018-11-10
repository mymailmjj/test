/**
 * 
 */
package indexfile.message;

import indexfile.ByteInter;

/**
 * @author az6367
 *
 */
public interface Message extends ByteInter{
	
	public final static int TYPE_TEXT = 1;
	
	public String getMessageId();
	
	public void setMessageId(String messageId);
	
	public int dataType();
	
}
