/**
 * 
 */
package indexfile.marshaller;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import indexfile.Location;

/**
 * @author az6367
 *
 */
public class LocationMarshaller implements Marshaller<Location> {
	
	private LocationMarshaller(){
		
	}
	
	public static LocationMarshaller instance = new LocationMarshaller();
	

	@Override
	public Location readPayload(DataInput in) {
		try {
			
			long dataField = in.readLong();
			long offset = in.readLong();
			int length = in.readInt();
			Location location = new Location(dataField, offset);
			location.setSize(length);
			return location;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void writePayload(Location l,DataOutput os) {
		try {
			os.writeLong(l.getDataField());
			os.writeLong(l.getOffset());
			os.writeInt(l.getSize());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
