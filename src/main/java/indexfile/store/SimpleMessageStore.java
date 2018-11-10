/**
 * 
 */
package indexfile.store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import indexfile.Location;
import indexfile.Messagekey;
import indexfile.lifecycle.LifecycleBase;
import indexfile.message.Message;
import indexfile.message.TextMessage;

/**
 * @author az6367
 *
 */
public class SimpleMessageStore extends LifecycleBase implements MessageStore {
	
	private Journal journal = Journal.instatnce();
	
	private PageFile pageFile = PageFile.instance();

	@Override
	public void addMessage(Message message) {
		Location location = journal.write(message);
		pageFile.updateIndex(location,message);
		
	}

	@Override
	public void updateIndex(Location location,Message message) {
		pageFile.updateIndex(location,message);
	}
	

	@Override
	public Location findMessage(String messageKey) {
		Long ranids = pageFile.getStoreDestination().getMessageIdIndex().get(messageKey);
		Messagekey messagekey = pageFile.getStoreDestination().getOrderIndex().get(ranids);
		return messagekey.getLocation();
	}

	@Override
	public void start() {
		journal.start();
		pageFile.start();
	}

	@Override
	public void stop() {
		journal.stop();
		pageFile.stop();
	}
	
	@Override
	public Message loadMessage(Location location) {
		return journal.loadMessage(location);
	}
	
	
	private static void saveStr() throws IOException{
		
		SimpleMessageStore simpleMessageStore = new SimpleMessageStore();
		simpleMessageStore.start();
		
		BufferedReader ubBufferedReader = new BufferedReader(new FileReader("C://Users//mymai//Desktop//0000//log.out"));
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("F://key.log"));
		
		String str = null;
		
		int i = 0;
		
		do{
			str = ubBufferedReader.readLine();
			
			if(str!=null&&!str.trim().equals("")){
				
				
				String key = indexfile.utils.KeyGenerator.genKey();
				
				System.out.println("*******************************new message key:"+key+" start****************************");
				
				bufferedWriter.write(i+"\t"+key+"\t"+str+"\r\n");
				
				TextMessage textMessage = new TextMessage(key,str);
				simpleMessageStore.addMessage(textMessage);
				
				System.out.println("*******************************new message key:"+key+" end****************************");
				
				bufferedWriter.flush();
				
			}
			
		}while(str!=null&&i++<50);
		
		bufferedWriter.close();
		
		ubBufferedReader.close();
		
	}
	
	
	public static void loadMessage(){
		
		SimpleMessageStore simpleMessageStore = new SimpleMessageStore();
		simpleMessageStore.start();
		
		
	/*	TextMessage textMessage1 = new TextMessage("textbook12","aaabvcdeeeeeee");
		simpleMessageStore.addMessage(textMessage1);
		
		TextMessage textMessage2 = new TextMessage("textbook34","aaabvcdeeeeeeeffffgggg");
		simpleMessageStore.addMessage(textMessage2);  */
		
		Location location = simpleMessageStore.findMessage("2dabad31aaf942d1");
		
		System.out.println("取到位置:"+location);
		
		Message message = simpleMessageStore.loadMessage(location);
		
		System.out.println(((TextMessage)message).getText());
		
		
	}
	
	
	
	public static void main(String[] args) throws IOException {
		
//		saveStr();
	
		loadMessage();
		
	/*	int[] aa = {1,2,3,4,5,6,7};
		
		int[] bb = new int[10];
		
		int[] cc = new int[10];
		
		System.arraycopy(aa, 0, bb, 0, 3);
		
		System.arraycopy(aa, 3, cc, 0, 3);
		
		System.out.println(Arrays.toString(bb));
		
		System.out.println(Arrays.toString(cc));*/
		
	}

	

}
