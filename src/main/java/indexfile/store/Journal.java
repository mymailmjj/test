/**
 * 
 */
package indexfile.store;

import java.io.IOException;

import indexfile.ByteInter;
import indexfile.Location;
import indexfile.lifecycle.LifecycleBase;
import indexfile.marshaller.MessageMarshaller;
import indexfile.message.Message;
import indexfile.message.MessageFactory;
import indexfile.writer.FileDataReader;
import indexfile.writer.FileDataReaderFactory;

/**
 * @author az6367
 *
 */
public class Journal extends LifecycleBase{
	
	private final static String MAGIC_HEADER_STRING = "INDEX_PAGE_HEAER";
	
	private final static String MAGIC_FOOTER_STRING = "INDEX_PAGE_FOOTER";
	
	public final static int DEFAULT_DATAFILE_SIZE = 1024*1204;
	
	private int dataFileSize = DEFAULT_DATAFILE_SIZE;
	
	
	private Journal(){
		
	}
	
	private static Journal journal;
	
	public static Journal instatnce(){
		if(journal==null){
			synchronized (Journal.class) {
				if(journal==null){
					journal = new Journal();
				}
			}
		}
		
		return journal;
		
	}
	
	public Location write(Message message) {

		byte[] content = message.getByte();

		FileDataReader<ByteInter> fileDataReader = FileDataReaderFactory.getFileDataReader();

		long position = fileDataReader.position();
		
		System.out.println("write position:"+position);

		Location location = new Location(1, position);

		location.setSize(content.length); // 设置写的内容的大小

		MessageMarshaller.instance.writePayload(message, fileDataReader);
			
		return location;

	}
	
	public Message loadMessage(Location location){
		long findOffset = findOffset(location);
		FileDataReader<ByteInter> fileDataReader = FileDataReaderFactory.getFileDataReader();
		fileDataReader.seek(findOffset);
		return MessageMarshaller.instance.readPayload(fileDataReader,location);
	}
	
	
	private long findOffset(Location location){
		long offset = dataFileSize*(location.getDataField()-1)+location.getOffset();
		return offset;
	}
	

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

}
