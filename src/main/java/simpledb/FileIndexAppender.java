/**
 * 
 */
package simpledb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * @author mymai
 *
 */
public class FileIndexAppender implements FileOper<Index> {
	
	private long[] index;
	
	private String fileName;
	
	private RandomAccessFile randomAccessFile;
	
	private long size;
	

	public FileIndexAppender(String fileName, String rwmode) {
		this.fileName = fileName;
		try {
			this.randomAccessFile = new RandomAccessFile(fileName, rwmode);
			index = new long[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(Index t) {
		if(size>index.length*0.75){
			long[] newIndexArray = Arrays.copyOf(index, 2*index.length);
			index = newIndexArray;
		}
		byte[] byte1 = t.getByte();
		try {
			randomAccessFile.write(byte1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.size++;
		
		resetHeadNum();
	}
	
	private void resetHeadNum(){
		try {
			randomAccessFile.seek(0);
			randomAccessFile.writeLong(size);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void resetHeadPointer(){
		try {
			randomAccessFile.seek(head());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public long head(){
		return position(this.size);
	}

	public long position(long id){
		return 8+id*8;
	}
	
	@Override
	public Index read(int id) {
		return null;
	}

	@Override
	public Index[] readArrays() {
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
		
	}

}
