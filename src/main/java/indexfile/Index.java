/**
 * 
 */
package indexfile;

/**
 * @author az6367
 *
 */
public class Index implements ByteInter{
	
	private long location;
	
	private long contentLength;
	
	public Index(long location, long contentLength) {
		this.location = location;
		this.contentLength = contentLength;
	}

	public long getLocation() {
		return location;
	}

	public void setLocation(long location) {
		this.location = location;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	@Override
	public byte[] getByte() {
		return Utils.longToByte(location,contentLength);
	}
	
	
	public static Index bytesToIndex(byte[] bytes){
		long location = Utils.byteToLong(bytes, 0);
		long currentLen = Utils.byteToLong(bytes, 8);
		return new Index(location, currentLen);
	}

	@Override
	public void parse(byte[] bytes) {
		// TODO Auto-generated method stub
		
	}

}
