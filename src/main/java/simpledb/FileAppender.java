/**
 * 
 */
package simpledb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author mymai
 *
 */
public class FileAppender implements FileOper<Page<StringByteInter>>{
	
	private String fileName;
	
	private RandomAccessFile randomAccessFile;
	
	private FileIndexAppender fileIndexAppender;
	

	public FileAppender(String fileName, String rwmode) {
		this.fileName = fileName;
		try {
			this.randomAccessFile = new RandomAccessFile(fileName, rwmode);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	

	public FileAppender(String fileName, String rwmode, FileIndexAppender fileIndexAppender) {
		this(fileName,rwmode);
		this.fileIndexAppender = fileIndexAppender;
	}



	@Override
	public void write(Page<StringByteInter> t) {
		try {
			randomAccessFile.write(t.getByte());
			//下面的部分写入索引
			long filePointer = randomAccessFile.getFilePointer();
			long head = fileIndexAppender.head();    //求出当前索引中head的位置
			long size = filePointer - head;
			Index index = new Index(filePointer, size);
			this.fileIndexAppender.write(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Page<StringByteInter> read(int id) {
		
		return null;
	}

	@Override
	public Page<StringByteInter>[] readArrays() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		try {
			randomAccessFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.fileIndexAppender.close();
		
	}


}
