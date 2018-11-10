package indexfile.writer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import indexfile.ByteInter;
import indexfile.Index;

public class FileDataReader<T extends ByteInter> implements DataOutput,DataInput {
	
	private String fileName;
	
	private RandomAccessFile dataFileWriter;

	public FileDataReader(String fileName) {
		this.fileName = fileName;
		try {
			dataFileWriter  = new RandomAccessFile(fileName, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	public void write(T t) {
		byte[] byte1 = t.getByte();
		try {
			long filePointer = dataFileWriter.getFilePointer();
			dataFileWriter.write(byte1);
			long currentHead = byte1.length;
			Index index = new Index(filePointer, currentHead);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void seek(long pos){
		try {
			dataFileWriter.seek(pos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void close() {
		try {
			dataFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void readFully(byte[] b) throws IOException {
		dataFileWriter.readFully(b);
		
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		dataFileWriter.readFully(b, off, len);
		
	}

	@Override
	public int skipBytes(int n) throws IOException {
		return dataFileWriter.skipBytes(n);
	}
	
	
	public long position(){
		try {
			return dataFileWriter.getFilePointer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public boolean readBoolean() throws IOException {
		return dataFileWriter.readBoolean();
	}

	@Override
	public byte readByte() throws IOException {
		return dataFileWriter.readByte();
	}

	@Override
	public int readUnsignedByte() throws IOException {
		return dataFileWriter.readUnsignedByte();
	}

	@Override
	public short readShort() throws IOException {
		return dataFileWriter.readShort();
	}

	@Override
	public int readUnsignedShort() throws IOException {
		return dataFileWriter.readUnsignedShort();
	}

	@Override
	public char readChar() throws IOException {
		return dataFileWriter.readChar();
	}

	@Override
	public int readInt() throws IOException {
		return dataFileWriter.readInt();
	}

	@Override
	public long readLong() throws IOException {
		return dataFileWriter.readLong();
	}

	@Override
	public float readFloat() throws IOException {
		return dataFileWriter.readFloat();
	}

	@Override
	public double readDouble() throws IOException {
		return dataFileWriter.readDouble();
	}

	@Override
	public String readLine() throws IOException {
		return dataFileWriter.readLine();
	}

	@Override
	public String readUTF() throws IOException {
		return dataFileWriter.readUTF();
	}

	@Override
	public void write(int b) throws IOException {
		dataFileWriter.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		dataFileWriter.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		dataFileWriter.write(b, off, len);
	}

	@Override
	public void writeBoolean(boolean v) throws IOException {
		dataFileWriter.writeBoolean(v);
	}

	@Override
	public void writeByte(int v) throws IOException {
		dataFileWriter.writeByte(v);
		
	}

	@Override
	public void writeShort(int v) throws IOException {
		dataFileWriter.writeShort(v);
		
	}

	@Override
	public void writeChar(int v) throws IOException {
		dataFileWriter.writeChar(v);
		
	}

	@Override
	public void writeInt(int v) throws IOException {
		dataFileWriter.writeInt(v);
	}

	@Override
	public void writeLong(long v) throws IOException {
		dataFileWriter.writeLong(v);
	}

	@Override
	public void writeFloat(float v) throws IOException {
		dataFileWriter.writeFloat(v);
	}

	@Override
	public void writeDouble(double v) throws IOException {
		dataFileWriter.writeDouble(v);
	}

	@Override
	public void writeBytes(String s) throws IOException {
		dataFileWriter.writeBytes(s);
	}

	@Override
	public void writeChars(String s) throws IOException {
		dataFileWriter.writeChars(s);
	}

	@Override
	public void writeUTF(String s) throws IOException {
		dataFileWriter.writeUTF(s);
		
	}
}
