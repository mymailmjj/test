/**
 * 
 */
package indexfile;

/**
 * @author az6367
 *
 */
public class Location implements Comparable<Location>{
	
	private long dataField;
	
	private long offset;
	
	private int size;
	
	public Location(long dataField, long offset) {
		this.dataField = dataField;
		this.offset = offset;
	}
	

	public int getSize() {
		return size;
	}



	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public int compareTo(Location o) {
		
		if(dataField!=o.dataField)
			return (int) (dataField-o.dataField);
		
		return (int) (offset - o.offset);
	}
	

	public long getDataField() {
		return dataField;
	}

	public void setDataField(long dataField) {
		this.dataField = dataField;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return "Location [dataField=" + dataField + ", offset=" + offset + "]";
	}

}
