/**
 * 
 */
package simpledb;

/**
 * @author mymai
 *
 */
public class Index implements ByteInter {

	private int indexSize = 16;

	public void setIndexSize(int indexSize) {
		this.indexSize = indexSize;
	}

	public void setLocation(long location) {
		this.location = location;
	}

	public void setSize(long size) {
		this.size = size;
	}

	private long location; // 文件写入的位置

	private long size; // 写入文件的大小

	public Index(long location, long size) {
		this.location = location;
		this.size = size;
	}

	@Override
	public byte[] getByte() {
		byte[] bytes = new byte[16];
		
		bytes[0] = (byte) (location >>> 56);
		bytes[1] = (byte) (location >>> 48);
		bytes[2] = (byte) (location >>> 40);
		bytes[3] = (byte) (location >>> 32);
		bytes[4] = (byte) (location >>> 24);
		bytes[5] = (byte) (location >>> 16);
		bytes[6] = (byte) (location >>> 8);
		bytes[7] = (byte) (location);

		bytes[8] = (byte) (size >>> 56);
		bytes[9] = (byte) (size >>> 48);
		bytes[10] = (byte) (size >>> 40);
		bytes[11] = (byte) (size >>> 32);
		bytes[12] = (byte) (size >>> 24);
		bytes[13] = (byte) (size >>> 16);
		bytes[14] = (byte) (size >>> 8);
		bytes[15] = (byte) (size);
		
		return bytes;
	}

}
