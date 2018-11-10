package simpledb;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		
		FileIndexAppender fileIndexAppender = new FileIndexAppender("C://Users//mymai//Desktop//0000//simpledb//index.log", "rw");
		
		FileAppender fileAppender = new FileAppender("C://Users//mymai//Desktop//0000//simpledb//data.log", "rw",fileIndexAppender);
		
		StringByteInter stringByteInter1 = new StringByteInter("a");
		
		Page page = new Page<>(1,stringByteInter1);
		
		fileAppender.write(page);
		
		StringByteInter stringByteInter2 = new StringByteInter("b");
		
		Page page1 = new Page<>(2,stringByteInter2);
		
		fileAppender.write(page1);
		
		fileAppender.close();

	}

}
