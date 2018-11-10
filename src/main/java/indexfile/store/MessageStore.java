/**
 * 
 */
package indexfile.store;

import indexfile.Location;
import indexfile.message.Message;

/**
 * @author az6367
 *
 */
public interface MessageStore {
	
	public void addMessage(Message message);
	
	public Location findMessage(String messageKey);
	
	public void updateIndex(Location location,Message message);
	
	public Message loadMessage(Location location);
	
	
}
