package filechecksum;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.Adler32;

/**
 * 本文展示了如何对输入的文本进行校验
 * @author mymai
 *
 */
public class FileCheckSum {

	public static void main(String[] args) throws IOException {
		
		File file = new File("D://abc.txt");
		
		Adler32 adler32 = new Adler32();
		
		if(!file.exists()) file.createNewFile();
		else {
			file.delete();
			file.createNewFile();
		}
		
		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
		
		randomAccessFile.seek(10000);
		
		String str = "abcde";
		
		byte[] bytes = str.getBytes();
		
		adler32.update(bytes);
		
		long value = adler32.getValue();
		
		System.out.println("checksum:"+value);
		
		randomAccessFile.write(bytes);
		
		randomAccessFile.writeLong(value);
		
		randomAccessFile.seek(20000);
		
		randomAccessFile.writeBytes("hhhh");
		
		randomAccessFile.close();

	}

}
