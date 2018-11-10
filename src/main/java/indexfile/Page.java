/**
 * 
 */
package indexfile;

/**
 * @author az6367
 *
 */
public class Page<T extends ByteInter> implements ByteInter{
	
	private long id;
	
	T t;

	public Page(long id) {
		this.id = id;
	}

	public Page(long id, T t) {
		this.id = id;
		this.t = t;
	}

	@Override
	public byte[] getByte() {
		
		if (id < 0)
			throw new IllegalArgumentException("pageId不能小于0");

		int newSize = 0;

		byte[] idBytes = Utils.longToByte(id);

		if (t != null) {
			byte[] tBytes = t.getByte();
			newSize = idBytes.length + tBytes.length;

		} else {
			newSize = idBytes.length;
		}

		byte[] b = new byte[newSize];

		System.arraycopy(idBytes, 0, b, 0, idBytes.length);
		
		if (t != null) {
			byte[] tBytes = t.getByte();
			System.arraycopy(tBytes, 0, b, idBytes.length, tBytes.length);
		}
		
		return b;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
	public static Page newEmptyPage(long Pageid){
		Page page = new Page(Pageid);
		return page;
	}

	@Override
	public void parse(byte[] bytes) {
		// TODO Auto-generated method stub
		
	}
	
}
