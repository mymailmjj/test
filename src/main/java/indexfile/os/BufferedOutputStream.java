/**
 * 
 */
package indexfile.os;

import java.io.DataOutput;
import java.io.IOException;

import indexfile.writer.IndexReader;
import indexfile.writer.IndexReaderFactory;

/**
 * @author az6367
 *
 */
public class BufferedOutputStream implements DataOutput{
	
	private int pageSize;
	
	private IndexReader indexReader = IndexReaderFactory.getIndexReader();
	
	public BufferedOutputStream(int pageSize) {
		this.pageSize = pageSize;
	}
	
	private byte[] bytes = new byte[pageSize];
	
	private int i = 0;
	
	public void writeEmptyPage(){
		indexReader.write(new byte[pageSize]);
	}
	
	public void seek(long pos){
		indexReader.seek(pos);
	}

	@Override
	public void write(int b) throws IOException {
		indexReader.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		indexReader.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		indexReader.write(b, off, len);
	}

	@Override
	public void writeBoolean(boolean v) throws IOException {
		indexReader.writeBoolean(v);
	}

	@Override
	public void writeByte(int v) throws IOException {
		indexReader.writeByte(v);
	}

	@Override
	public void writeBytes(String s) throws IOException {
		indexReader.writeBytes(s);
		
	}

	@Override
	public void writeChar(int v) throws IOException {
		indexReader.writeChar(v);
		
	}

	@Override
	public void writeChars(String s) throws IOException {
		indexReader.writeChars(s);
		
	}

	@Override
	public void writeDouble(double v) throws IOException {
		indexReader.writeDouble(v);
	}

	@Override
	public void writeFloat(float v) throws IOException {
		indexReader.writeFloat(v);
	}

	@Override
	public void writeInt(int v) throws IOException {
		indexReader.writeInt(v);
		
	}

	@Override
	public void writeLong(long v) throws IOException {
		indexReader.writeLong(v);
		
	}

	@Override
	public void writeShort(int v) throws IOException {
		indexReader.writeShort(v);
		
	}

	@Override
	public void writeUTF(String s) throws IOException {
		indexReader.writeUTF(s);
		
	}
	
	
	public void flush(){
	}

}
