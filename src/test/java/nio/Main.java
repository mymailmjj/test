package nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class Main {

	public static void main(String[] args) {

		try {
			FileInputStream fileInputStream = new FileInputStream("");

			FileChannel channel = fileInputStream.getChannel();

			ByteBuffer allocate = ByteBuffer.allocate(1024);

			Selector selector = Selector.open();

			Set<SelectionKey> selectedKeys = selector.selectedKeys();

			Iterator<SelectionKey> iterator = selectedKeys.iterator();

			while (iterator.hasNext()) {
				
				SelectionKey key = iterator.next();

				if (key.isAcceptable()) {

				} else if (key.isConnectable()) {
					
				} else if (key.isReadable()) {
					
				} else if (key.isWritable()) {
					
				}
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
