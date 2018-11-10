/**
 * 
 */
package indexfile.store;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import indexfile.ByteInter;
import indexfile.Location;
import indexfile.Page;
import indexfile.StoreDestination;
import indexfile.lifecycle.LifecycleBase;
import indexfile.marshaller.StoreDestinationMarshaller;
import indexfile.message.Message;
import indexfile.os.BufferedInputStream;
import indexfile.os.BufferedOutputStream;
import indexfile.tree.BTreeIndex;
import indexfile.tree.BTreeNode;
import indexfile.tree.PageCache;
import indexfile.writer.IndexReader;
import indexfile.writer.IndexReaderFactory;

/**
 * @author az6367
 *
 */
public class PageFile extends LifecycleBase{
	
	private static PageFile pageFile;
	
	public static PageFile instance() {
		if (pageFile == null) {
			synchronized (PageFile.class) {
				if (pageFile == null) {
					pageFile = new PageFile();
				}
			}
		}
		return pageFile;
	}
	
	private PageFile(){
		
	}
	
	public final static int DEFAULT_PAGE_SIZE = 1024;
	
	public static int pageSize = DEFAULT_PAGE_SIZE;
	
	private static long pageId = 0;
	
	private StoreDestination storeDestination;
	
	public StoreDestination getStoreDestination() {
		return storeDestination;
	}

	public void setStoreDestination(StoreDestination storeDestination) {
		this.storeDestination = storeDestination;
	}

	private final static String MAGIC_HEADER_STRING = "INDEX_PAGE_HEAER";
	
	private final static String MAGIC_FOOTER_STRING = "INDEX_PAGE_FOOTER";
	
	private StoreDestinationMarshaller storeDestinationMarshaller = new StoreDestinationMarshaller();
	
	private float version = 1.0f;
	
	private Meta meta = new Meta();
	
	private boolean loaded = false;
	
	public void load(){
		loadMeta();
		loadStoreDestination();
	}
	
	
	private void loadMeta(){
		IndexReader indexReader = IndexReaderFactory.getIndexReader();
		try {
			if(indexReader.length()>0){
				
				indexReader.seek(0);
				
				long r = indexReader.readLong();
				
				meta.setMaxPageid(r);
				meta.setPageCount(r);
				
				float f = indexReader.readFloat();
				meta.setVersion(f);
			}else{
				nextEmptyPage();
				indexReader.seek(0);
				meta.setMaxPageid(0);
				meta.setPageCount(0);
				indexReader.writeLong(0);
				indexReader.writeFloat(version);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void start() {
		if(loaded) return;
		load();
		loaded = true;
	}

	@Override
	public void stop() {
		if(!loaded) return;
		storeDestination = null;
	}

	private class Meta{
		
		private long maxPageid;
		
		private long pageCount;

		private float version;
		
		public float getVersion() {
			return version;
		}

		public void setVersion(float version) {
			this.version = version;
		}

		public long getMaxPageid() {
			return maxPageid;
		}

		public void setMaxPageid(long maxPageid) {
			this.maxPageid = maxPageid;
		}

		public long getPageCount() {
			return pageCount;
		}

		public void setPageCount(long pageCount) {
			this.pageCount = pageCount;
		}
		
		public void increasePageCount(){
			this.pageCount++;
		}
		
		public void increaseMaxPageId(){
			this.maxPageid++;
		}
		
		public void update(){
			IndexReader indexReader = IndexReaderFactory.getIndexReader();
			indexReader.seek(0);
			try {
				indexReader.writeLong(maxPageid);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public static long nextPageId(){
		return pageId++;
	}
	
	public static Page nextEmptyNodePage(BTreeIndex index){
		Page<BTreeNode> page = Page.newEmptyPage(pageId++);
		BTreeNode node = new BTreeNode<>(index);
		node.setEmpty();
		node.setPageId(pageId);
		page.setT(node);
		IndexReader indexReader = IndexReaderFactory.getIndexReader();
		System.out.println("写入空白页,pageId:"+pageId);
		long findOffSet = findOffSet(pageId);
		indexReader.seek(findOffSet);
		indexReader.write(new byte[pageSize]);
		return page;
	}
	
	public static Page nextEmptyPage(){
		Page<BTreeNode> page = Page.newEmptyPage(pageId++);
		IndexReader indexReader = IndexReaderFactory.getIndexReader();
		indexReader.write(new byte[pageSize]);
		return page;
	}
	
	public static void storeNodePage(){
		
	}
	
	//用于启动时加载一个meta索引
	private void loadStoreDestination(){
		
		IndexReader indexReader = IndexReaderFactory.getIndexReader();
		long storeDestinationOffset = pageSize;
		indexReader.seek(storeDestinationOffset);
		if(meta.pageCount>0){
			storeDestination = (StoreDestination) storeDestinationMarshaller.readPayload(indexReader);
		}else{
			nextEmptyPage();
			resetPageId();
			storeDestination = new StoreDestination();
			storeDestination.initIndex();
			indexReader.seek(storeDestinationOffset);
			storeDestinationMarshaller.writePayload(storeDestination, indexReader);
			meta.setMaxPageid(pageId);
			meta.setPageCount(pageId);
			meta.update();
		}
		
		
	}
	
	private void resetPageId(){
		pageId = 0;
	}
	
	private static long findOffSet(long pageId){
		return (2+pageId)*pageSize;
	}
	
	public static <T extends ByteInter> Page<T> loadPage(long pageId,Class<T> c){
		long findOffSet = findOffSet(pageId);
		IndexReader indexReader = IndexReaderFactory.getIndexReader();
		indexReader.seek(findOffSet);
		long pId;
		try {
			pId = indexReader.readLong();
			Page<T> page = new Page<>(pId);
		return page;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static DataOutput loadPageOutput(){
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(pageSize);
		return bufferedOutputStream;
	}
	
	
	public static DataInput loadPageToBuffer(long pageId) throws IOException{
		IndexReader indexReader = IndexReaderFactory.getIndexReader();
		long findOffSet = findOffSet(pageId);
		indexReader.seek(findOffSet);
		byte[] byteds = new byte[pageSize];
		
		try {
			indexReader.readFully(byteds);
		} catch (Exception e) {
			System.err.println("这里操作页面:"+pageId);
			e.printStackTrace();
		}
		BufferedInputStream bufferedInputStream = new BufferedInputStream(pageSize,byteds);
		return bufferedInputStream;
	}
	
	public void updateIndex(Location location,Message message){
		storeDestination.updateIndex(location,message);
	}
	
	
	public static void storePage(Page page){
		
		long findOffSet = findOffSet(page.getId());
		
		IndexReader indexReader = IndexReaderFactory.getIndexReader();
		
		indexReader.seek(findOffSet);
		
		indexReader.write(page.getByte());
		
	}
	
	public void flush(){
		
		
		
	}
	
	public static void main(String[] args) {
		
		PageFile pageFile = new PageFile();
		
		pageFile.load();
		
	}
	
}
