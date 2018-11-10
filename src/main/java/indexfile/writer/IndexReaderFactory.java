/**
 * 
 */
package indexfile.writer;

/**
 * @author az6367
 *
 */
public class IndexReaderFactory {
	
	private final static String defaultIndexName = "db.data";
	
	private static String indexFileName;
	
	private static String defalutIndexPath = "F://";
	
	
	private IndexReaderFactory(){
		
	}
	

	private static IndexReader indexReader = null;
	
	public static IndexReader getIndexReader(){
		
		if(indexFileName==null){
			indexFileName = defaultIndexName;
		}
		
		
		if(indexReader==null){
			indexReader = new IndexReader(defalutIndexPath+indexFileName);
		}
		
		return indexReader;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
