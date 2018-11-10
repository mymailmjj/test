/**
 * 
 */
package es.lucene.analyzer;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;

/**
 * @author az6367
 *
 */
public class MyAnalyzer extends Analyzer {
	

	@Override
	protected Reader initReader(String fieldName, Reader reader) {
		return reader;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.lucene.analysis.Analyzer#createComponents(java.lang.String)
	 */
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		
		ComTokenizer comTokenizer = new ComTokenizer();

		LowCaseFilter lowCaseFilter = new LowCaseFilter(comTokenizer);

		StopWordFilter stopWordFilter = new StopWordFilter(lowCaseFilter);
		
		TokenStreamComponents tokenStreamComponents = new TokenStreamComponents(comTokenizer, stopWordFilter);
		
		return tokenStreamComponents;
	}

}
