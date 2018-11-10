/**
 * 
 */
package indexfile.os;

import java.io.DataInput;
import java.io.IOException;

/**
 * @author az6367
 *
 */
public class BufferedInputStream implements DataInput{
	
	private int pageSize;
	
	public BufferedInputStream(int pageSize) {
		this.pageSize = pageSize;
	}
	
	private byte[] bytes = new byte[pageSize];
	
	private int i = 0;
	
	public BufferedInputStream(int pageSize,byte[] bytes){
		this(pageSize);
		this.bytes = bytes;
	}

	@Override
	public void readFully(byte[] b) throws IOException {
		System.arraycopy(bytes, i, b, 0, b.length);
		i = i+b.length;
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		try {
			for(int i = off; i<off+len;i++){
				bytes[i-off] = b[i];
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int skipBytes(int n) throws IOException {
		i+=n;
		return i;
	}

	@Override
	public boolean readBoolean() throws IOException {
		byte b = bytes[i++];
		boolean f = (b&0x01)==1;
		return f;
	}

	@Override
	public byte readByte() throws IOException {
		return bytes[i++];
	}

	@Override
	public int readUnsignedByte() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short readShort() throws IOException {
		return 0;
	}

	@Override
	public int readUnsignedShort() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char readChar() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readInt() throws IOException {
		byte[] intbytes = new byte[4];
		intbytes[3] = bytes[i++];
		intbytes[2] = bytes[i++];
		intbytes[1] = bytes[i++];
		intbytes[0] = bytes[i++];
		int a = (intbytes[3]&0xFF)<<24|(intbytes[3]&0xFF)<<16|(intbytes[2]&0xFF)<<8|(intbytes[0]&0xFF);
		return a;
	}

	@Override
	public long readLong() throws IOException {
		byte[] intbytes = new byte[8];
		intbytes[0] = bytes[i++];
		intbytes[1] = bytes[i++];
		intbytes[2] = bytes[i++];
		intbytes[3] = bytes[i++];
		intbytes[4] = bytes[i++];
		intbytes[5] = bytes[i++];
		intbytes[6] = bytes[i++];
		intbytes[7] = bytes[i++];
		long result = (intbytes[0]&0xFF)<<56|(intbytes[1]&0xFF)<<48|(intbytes[2]&0xFF)<<40|(intbytes[3]&0xFF)<<32|(intbytes[4]&0xFF)<<24|(intbytes[5]&0xFF)<<16|(intbytes[6]&0xFF)<<8|(intbytes[7]&0xFF);
		return result;
	}

	@Override
	public float readFloat() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double readDouble() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String readLine() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readUTF() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] args) throws IOException {
		
		byte[] bytes = new byte[8];
		bytes[7] = 29;
		bytes[6] = 1;
		
		
		BufferedInputStream bufferedInputStream = new BufferedInputStream(8, bytes);
		
		long readLong = bufferedInputStream.readLong();
		
		System.out.println(readLong);
		
	}


}
