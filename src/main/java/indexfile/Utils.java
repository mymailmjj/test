/**
 * 
 */
package indexfile;

import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * @author az6367
 *
 */
public class Utils {

	public static byte[] longToByte(long... l) {
		
		int num = l.length;
		
		byte[] bytes = new byte[8*num];
		
		while(num>0){
			int mul = num-1;
			bytes[0+8*mul] = (byte) (l[mul] >>> 56);
			bytes[1+8*mul] = (byte) (l[mul] >>> 48);
			bytes[2+8*mul] = (byte) (l[mul] >>> 40);
			bytes[3+8*mul] = (byte) (l[mul] >>> 32);
			bytes[4+8*mul] = (byte) (l[mul] >>> 24);
			bytes[5+8*mul] = (byte) (l[mul] >>> 16);
			bytes[6+8*mul] = (byte) (l[mul] >>> 8);
			bytes[7+8*mul] = (byte) (l[mul]);
			num--;
		}
	
		return bytes;
	}

	public static long byteToLong(byte[] bytes, int fromIndex) {
		long s = (bytes[fromIndex]& 0xFFL) << 56  | (bytes[fromIndex + 1]& 0xFFL) << 48
				| (bytes[fromIndex + 2]& 0xFFL) << 40  | (bytes[fromIndex + 3]& 0xFFL) << 32
				| (bytes[fromIndex + 4]& 0xFFL) << 24 | (bytes[fromIndex + 5]& 0xFFL) << 16
				| (bytes[fromIndex + 6]& 0xFFL) << 8 | (bytes[fromIndex + 7]& 0xFFL);

		return s;
	}

	
	public static byte[] subBytesArray(byte[] bytes,int fromIndex,int toIndex){
		byte[] bytes1 = new byte[toIndex-fromIndex];
		System.arraycopy(bytes, fromIndex, bytes1, 0, (toIndex-fromIndex));
		return bytes1;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {

		byte[] longToByte = longToByte(5642117484147L,564211766666244L);
		System.out.println(Arrays.toString(longToByte));
		long byteToLong = byteToLong(longToByte, 0);
		System.out.println(byteToLong);
		/*long byteToLong1 = byteToLong(longToByte, 8);
		System.out.println(byteToLong1);*/

	}

}
