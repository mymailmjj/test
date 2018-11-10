/**
 * 
 */
package indexfile;

/**
 * @author az6367
 *
 */
public class Messagekey {
	
	private Location location;
	
	private String messagekey;

	public Messagekey(Location location, String messagekey) {
		this.location = location;
		this.messagekey = messagekey;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getMessagekey() {
		return messagekey;
	}

	public void setMessagekey(String messagekey) {
		this.messagekey = messagekey;
	}

	@Override
	public String toString() {
		return "Messagekey [" + (location != null ? "location=" + location + ", " : "")
				+ (messagekey != null ? "messagekey=" + messagekey : "") + "]";
	}

}
