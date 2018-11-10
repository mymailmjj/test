/**
 * 
 */
package es.lucene.bitcompress;

/**
 * 
 * 位压缩技术,byte为两个字节，int为4个字节，如果我们需要3个字节，则存在浪费空间的问题
 * 
 * @author az6367
 *
 */
public class Packed8ThreeBlocks {

	private byte[] blocks = new byte[3];

	public void setValue(long value) {
		blocks[0] = (byte) (value >>> 16);
		blocks[1] = (byte) (value >>> 8);
		blocks[2] = (byte) value;
	}

	public long getvalue() {
		return (blocks[0] & 0xFFL) << 16 | (blocks[1] & 0xFFL) << 8 | (blocks[2] & 0xFFL);
	}

	public static void main(String[] args) {
		Packed8ThreeBlocks packed8ThreeBlocks = new Packed8ThreeBlocks();
		packed8ThreeBlocks.setValue(0x1E6E47);
		System.out.println(packed8ThreeBlocks.getvalue());
	}

}
