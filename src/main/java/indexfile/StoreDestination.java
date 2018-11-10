/**
 * 
 */
package indexfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import indexfile.marshaller.LocationMarshaller;
import indexfile.marshaller.LongMarshaller;
import indexfile.marshaller.MessageIDMarshaller;
import indexfile.marshaller.MessagekeyMarshaller;
import indexfile.marshaller.StringMarshaller;
import indexfile.message.Message;
import indexfile.store.PageFile;
import indexfile.tree.BTreeIndex;

/**
 * @author az6367
 *
 */
public class StoreDestination {
	
	private AtomicLong nextMessageid = new AtomicLong();
	
	private BTreeIndex<Long, Messagekey> orderIndex;
	
	private BTreeIndex<Location, Long> locationIndex;
	
	private BTreeIndex<String,Long> messageIdIndex;
	
	public BTreeIndex<Long, Messagekey> getOrderIndex() {
		return orderIndex;
	}


	public void setOrderIndex(BTreeIndex<Long, Messagekey> orderIndex) {
		this.orderIndex = orderIndex;
	}


	public BTreeIndex<Location, Long> getLocationIndex() {
		return locationIndex;
	}


	public void setLocationIndex(BTreeIndex<Location, Long> locationIndex) {
		this.locationIndex = locationIndex;
	}


	public BTreeIndex<String, Long> getMessageIdIndex() {
		return messageIdIndex;
	}


	public void setMessageIdIndex(BTreeIndex<String, Long> messageIdIndex) {
		this.messageIdIndex = messageIdIndex;
	}


	public void loadIndex(DataInput dataInput) throws IOException{
		
		long orderIndexRoot = dataInput.readLong();
		orderIndex = new BTreeIndex<Long, Messagekey>(orderIndexRoot);
		long locationIndexRoot = dataInput.readLong();
		locationIndex = new BTreeIndex<Location, Long>(locationIndexRoot);
		long messageIdIndexRoot = dataInput.readLong();
		messageIdIndex = new BTreeIndex<String,Long>(messageIdIndexRoot);
		
		setMarshaller();
	}
	
	
	private void setMarshaller(){
		orderIndex.setKeyMarshaller(LongMarshaller.instance);
		orderIndex.setValueMarshaller(MessagekeyMarshaller.instance);
		
		locationIndex.setKeyMarshaller(LocationMarshaller.instance);
		locationIndex.setValueMarshaller(LongMarshaller.instance);
		
		messageIdIndex.setKeyMarshaller(MessageIDMarshaller.instance);
		messageIdIndex.setValueMarshaller(LongMarshaller.instance);
	}
	
	public void initIndex(){
		//new empty
		orderIndex = new BTreeIndex<Long, Messagekey>(PageFile.nextEmptyPage().getId());
		locationIndex = new BTreeIndex<Location, Long>(PageFile.nextEmptyPage().getId());
		messageIdIndex = new BTreeIndex<String,Long>(PageFile.nextEmptyPage().getId());
		
		setMarshaller();
	}
	
	public void updateIndex(Location location,Message message){
		long ranId = nextMessageid.getAndIncrement();
		Messagekey messagekey = new Messagekey(location, message.getMessageId());
		Messagekey messagekey2 = orderIndex.put(ranId, messagekey);
		if(messagekey2==null){
			locationIndex.put(location, ranId);
			messageIdIndex.put(message.getMessageId(), ranId);
		}
	}
	
	
	public void storeIndex(DataOutput dataOutput){
		try {
			dataOutput.writeLong(orderIndex.getPageid());
			dataOutput.writeLong(locationIndex.getPageid());
			dataOutput.writeLong(messageIdIndex.getPageid());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
