/**
 * 
 */
package es.lucene.analyzer;

import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;

/**
 * @author az6367
 *
 */
public class ComTokenizer extends Tokenizer {

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.TokenStream#incrementToken()
	 */
	@Override
	public final boolean incrementToken() throws IOException {
		
		
		return true;
	}

}
