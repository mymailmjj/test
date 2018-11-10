/**
 * 
 */
package indexfile;

/**
 * @author az6367
 *
 */
public class StringByteInter implements ByteInter {
	
	private String str;
	
	public StringByteInter(byte[] bytes){
		this.str = new String(bytes);
	}
	
	public StringByteInter(String str) {
		this.str = str;
	}

	/* (non-Javadoc)
	 * @see indexfile.ByteInter#getByte()
	 */
	@Override
	public byte[] getByte() {
		return str.getBytes();
	}

	public static ByteInter newByteInter(byte[] b) {
		StringByteInter stringByteInter = new StringByteInter(b);
		return stringByteInter;
	}

	@Override
	public String toString() {
		return "StringByteInter [" + (str != null ? "str=" + str : "") + "]";
	}

	@Override
	public void parse(byte[] bytes) {
		// TODO Auto-generated method stub
		
	}

}
