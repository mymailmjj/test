/**
 * 
 */
package es.lucene.analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.TokenStream;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author az6367
 *
 */
public class AnalyzerTest extends TestCase {
	
	public void testAnalyzer() throws IOException{
		
		File file = new File("C://Users//AZ6367//Desktop//0000//lucene//source.txt");
		
		FileReader reader = new FileReader(file);
		
		MyAnalyzer myAnalyzer = new MyAnalyzer();
		
		TokenStream tokenStream = myAnalyzer.tokenStream(null, reader);
		
		tokenStream.reset();
		
		while(tokenStream.incrementToken()){
			
			
		}
		
		tokenStream.end();
		
	}
	

}
