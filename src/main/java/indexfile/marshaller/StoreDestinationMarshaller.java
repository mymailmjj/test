package indexfile.marshaller;

/**
 * 
 */

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import indexfile.StoreDestination;

/**
 * @author az6367
 *
 */
public class StoreDestinationMarshaller implements Marshaller<StoreDestination> {
	
	@Override
	public StoreDestination readPayload(DataInput in) {
		StoreDestination storeDestination = new StoreDestination();
		try {
			storeDestination.loadIndex(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return storeDestination;
	}

	@Override
	public void writePayload(StoreDestination t, DataOutput os) {
		t.storeIndex(os);
	}


}
