/**
 * 
 */
package indexfile.writer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import javax.xml.crypto.Data;

import indexfile.FileOper;
import indexfile.Index;

/**
 * @author az6367
 *
 */
public class IndexReader implements DataInput,DataOutput {
	
	private String fileName;
	
	private long size;
	
	private Index[]	 indexes;
	
	private RandomAccessFile  indexFileWriter;
	
	public final static int HEADNUM_SIZE = 8;
	
	public final static int INDEX_SIZE = 16;
	
	public IndexReader(String fileName) {
		this.fileName = fileName;
		indexes = new Index[1];
		this.size = 0;
		try {
			indexFileWriter = new RandomAccessFile(fileName, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void write(byte[] bytes){
		try {
			indexFileWriter.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(Index t) {
		
		if(size>=indexes.length*0.75){
			indexes = Arrays.copyOf(indexes, indexes.length*2);
		}
		
		byte[] byte1 = t.getByte();
		try {
			indexFileWriter.write(byte1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		indexes[(int)this.size++] = t;
		
		writeHeadNum();
	}
	
	public long currentHead(){
		return HEADNUM_SIZE+size*INDEX_SIZE;
	}
	
	private void writeHeadNum(){
		try {
			indexFileWriter.seek(0);
			indexFileWriter.writeLong(size);
			indexFileWriter.seek(currentHead());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Index read(long id) {
		long index = HEADNUM_SIZE+(id-1)*INDEX_SIZE;
		byte[] bytes = null;
		try {
			indexFileWriter.seek(index);
			bytes = new byte[INDEX_SIZE];
			indexFileWriter.read(bytes, 0, INDEX_SIZE);
			return Index.bytesToIndex(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void close() {
		try {
			indexFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void seek(long pos){
		try {
			indexFileWriter.seek(pos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public long length() throws IOException{
		long length = indexFileWriter.length();
		return length;
	}


	@Override
	public void write(int b) throws IOException {
		indexFileWriter.write(b);
		
	}


	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		indexFileWriter.write(b, off, len);
		
	}


	@Override
	public void writeBoolean(boolean v) throws IOException {
		indexFileWriter.writeBoolean(v);
		
	}


	@Override
	public void writeByte(int v) throws IOException {
		indexFileWriter.writeByte(v);
		
	}


	@Override
	public void writeShort(int v) throws IOException {
		indexFileWriter.writeShort(v);
		
	}


	@Override
	public void writeChar(int v) throws IOException {
		indexFileWriter.writeChar(v);
		
	}


	@Override
	public void writeInt(int v) throws IOException {
		indexFileWriter.writeInt(v);
		
	}


	@Override
	public void writeLong(long v) throws IOException {
		indexFileWriter.writeLong(v);
		
	}


	@Override
	public void writeFloat(float v) throws IOException {
		indexFileWriter.writeFloat(v);
		
	}


	@Override
	public void writeDouble(double v) throws IOException {
		indexFileWriter.writeDouble(v);
		
	}


	@Override
	public void writeBytes(String s) throws IOException {
		indexFileWriter.writeBytes(s);
		
	}


	@Override
	public void writeChars(String s) throws IOException {
		indexFileWriter.writeChars(s);
		
	}


	@Override
	public void writeUTF(String s) throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void readFully(byte[] b) throws IOException {
		indexFileWriter.readFully(b);
		
	}


	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		indexFileWriter.readFully(b, off, len);
		
	}


	@Override
	public int skipBytes(int n) throws IOException {
		return indexFileWriter.skipBytes(n);
	}


	@Override
	public boolean readBoolean() throws IOException {
		return indexFileWriter.readBoolean();
	}


	@Override
	public byte readByte() throws IOException {
		return indexFileWriter.readByte();
	}


	@Override
	public int readUnsignedByte() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public short readShort() throws IOException {
		return indexFileWriter.readShort();
	}


	@Override
	public int readUnsignedShort() throws IOException {
		return indexFileWriter.readUnsignedShort();
	}


	@Override
	public char readChar() throws IOException {
		return indexFileWriter.readChar();
	}


	@Override
	public int readInt() throws IOException {
		return indexFileWriter.readInt();
	}


	@Override
	public long readLong() throws IOException {
		return indexFileWriter.readLong();
	}


	@Override
	public float readFloat() throws IOException {
		return indexFileWriter.readFloat();
	}


	@Override
	public double readDouble() throws IOException {
		return indexFileWriter.readDouble();
	}


	@Override
	public String readLine() throws IOException {
		return indexFileWriter.readLine();
	}


	@Override
	public String readUTF() throws IOException {
		return indexFileWriter.readUTF();
	}
	

}
