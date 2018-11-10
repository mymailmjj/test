/**
 * 
 */
package indexfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import junit.framework.TestCase;
import indexfile.writer.FileDataReader;
import indexfile.writer.IndexReader;

/**
 * @author az6367
 *
 */
public class QueryTest extends TestCase {
	
	IndexReader indexReader = null;
	
	FileDataReader fileDataReader = null;
	
	BufferedReader bufferedReader = null;
	
	@Override
	protected void setUp() throws Exception {
		
		indexReader = new IndexReader("C://Users//AZ6367//Desktop//0000//技术储备//index//index.log");
		
//		fileDataReader = new FileDataReader("C://Users//AZ6367//Desktop//0000//技术储备//index//data.log", indexReader);
		
		super.setUp();
	}
	
	
	public void testLargeData(){
		
		try {
			bufferedReader = new BufferedReader(
					new FileReader(new File("C://Users//AZ6367//Desktop//0000//hotel_debug.log")));

			String readLine = "";
			
			int i = 0;

			while ((readLine = bufferedReader.readLine()) != null) {
				StringByteInter stringByteInter = new StringByteInter(readLine);
				
				Page<StringByteInter> page = new Page<>(i++,stringByteInter);
				
				fileDataReader.write(page);
			}
			
			System.out.println("总共加载:"+i+"个");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void testWrite(){
		
		StringByteInter stringByteInter = new StringByteInter("a");
		
		Page<StringByteInter> page = new Page<>(1,stringByteInter);
		
		fileDataReader.write(page);
		
		StringByteInter stringByteInter1 = new StringByteInter("b");
		
		Page<StringByteInter> page1 = new Page<>(2,stringByteInter1);
		
		fileDataReader.write(page1);
		
	}
		
	
	
	public void testQuery(){
		
	/*	Log4JStopWatch log4jStopWatch = new Log4JStopWatch();
		
		Page<StringByteInter> page = fileDataReader.read(247664);
		
		StringByteInter t = page.getT();
		
		log4jStopWatch.stop("finish query");
		
		System.out.println(t);*/
		
	}


	@Override
	protected void tearDown() throws Exception {
		
		fileDataReader.close();
		
		super.tearDown();
	}
	

}
