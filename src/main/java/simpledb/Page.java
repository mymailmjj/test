/**
 * 
 */
package simpledb;

/**
 * @author mymai
 *
 */
public class Page<T extends ByteInter> implements ByteInter{
	
	private long id;
	
	private T t;
	
	public Page(long id, T t) {
		this.id = id;
		this.t = t;
	}

	@Override
	public byte[] getByte() {
		byte[] byte1 = t.getByte();
		byte[] byte2 = new byte[8+byte1.length];
		byte2[0] = (byte)(id>>>56);
		byte2[1] = (byte)(id>>>48);
		byte2[2] = (byte)(id>>>40);
		byte2[3] = (byte)(id>>>32);
		byte2[4] = (byte)(id>>>24);
		byte2[5] = (byte)(id>>>16);
		byte2[6] = (byte)(id>>>8);
		byte2[7] = (byte)(id);
		System.arraycopy(byte1, 0, byte2, 8, byte1.length);
		return byte2;
	}

}
