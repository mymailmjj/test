/**
 * 
 */
package es.lucene.analyzer;

import java.io.IOException;

import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;

/**
 * @author az6367
 *
 */
public class StopWordFilter extends FilteringTokenFilter {

	public StopWordFilter(TokenStream in) {
		super(in);
	}

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.FilteringTokenFilter#accept()
	 */
	@Override
	protected boolean accept() throws IOException {
		return false;
	}

}
