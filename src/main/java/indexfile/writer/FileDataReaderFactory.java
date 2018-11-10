/**
 * 
 */
package indexfile.writer;

import indexfile.ByteInter;

/**
 * @author az6367
 *
 */
public class FileDataReaderFactory {
	
	private final static String defaultDataName = "db.log";
	
	private static String defalutIndexPath = "F://";
	
	private static String dataFileName;
	
	
	private FileDataReaderFactory(){
		if(dataFileName==null){
			dataFileName = defaultDataName;
		}
	}
	

	private static FileDataReader<ByteInter> fileDataReader = null;
	
	public static FileDataReader<ByteInter> getFileDataReader(){
		
		if(dataFileName==null){
			dataFileName = defaultDataName;
		}
		
		if(fileDataReader==null){
			fileDataReader = new FileDataReader(defalutIndexPath+dataFileName);
		}
		
		return fileDataReader;
	}

}
