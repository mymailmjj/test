/**
 * 
 */
package simpledb;

/**
 * @author mymai
 *
 */
public class StringByteInter implements ByteInter {
	
	private String str;

	/* (non-Javadoc)
	 * @see simpledb.ByteInter#getByte()
	 */
	@Override
	public byte[] getByte() {
		return str.getBytes();
	}

	public StringByteInter(String str) {
		this.str = str;
	}

}
