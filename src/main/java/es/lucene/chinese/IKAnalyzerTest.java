package es.lucene.chinese;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;

/**
 * 这个是对IKAnalyzer中文分析的解析,jar包名ikanalyzer-2012_u6
 * @author az6367
 *
 */
public class IKAnalyzerTest {

	public static void main(String[] args) throws IOException {
		
		ClassLoader classLoader = IKAnalyzerTest.class.getClassLoader();
		
		InputStream is = classLoader.getResourceAsStream("test/es/lucene/chinese/quantifier.dic");
		
	/*	Configuration config = DefaultConfig.getInstance();
		
		config.setUseSmart(true);
		
		Dictionary dictionary = Dictionary.initial(config);*/
		
		InputStreamReader inputStreamReader = new InputStreamReader(is);
		
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		
		String line = null;
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C://Users//AZ6367//Desktop//0000//技术储备//中文搜索//chinesedic//quantifier.dic"));
		
		try {
			while((line=bufferedReader.readLine())!=null){
				System.out.println(line);
				bufferedWriter.write(line);
				bufferedWriter.write("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		bufferedWriter.close();

	}

}
