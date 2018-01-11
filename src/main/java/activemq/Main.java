package activemq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

	public static void main(String[] args) throws IOException {
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("C:/Users/cango/Desktop/log/mqmain.log")));
		
		BufferedWriter bufferedReaderW = new BufferedWriter(new FileWriter(new File("C:/Users/cango/Desktop/log/mq1.log")));
		
		String str = "";
		
		String laststr = "";
		
		while((str=bufferedReader.readLine())!=null){
			
			if(!str.equals(laststr)){
				laststr = str;
				bufferedReaderW.write(laststr+"\n");
				
			}
			
			if((str=bufferedReader.readLine())!=null&&str.equals(laststr)){
				continue;
			}else{
				bufferedReaderW.write(str+"\n");
				laststr = str;
			}
		}
		
		bufferedReader.close();
		
		bufferedReaderW.close();

	}

}
