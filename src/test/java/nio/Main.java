package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {

	public static void main(String[] args) {
		
		try {
			FileInputStream inputStream  = new FileInputStream(new File("C:\\Users\\mujjiang\\Desktop\\0001\\test.txt"));
			
			FileChannel channel = inputStream.getChannel();
			
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			
			int read = channel.read(buffer);
			
			while(read!=-1){
				buffer.flip();
				while(buffer.hasRemaining()){
					byte b = buffer.get();
					System.out.println((char)b);
				}
				
				buffer.compact();
				read = channel.read(buffer);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
