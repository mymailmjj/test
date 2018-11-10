/**
 * 
 */
package es.lucene.analyzer;

import java.io.IOException;

import org.apache.lucene.analysis.CharacterUtils;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * @author az6367
 *
 */
public class LowCaseFilter extends TokenFilter {

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

	protected LowCaseFilter(TokenStream input) {
		super(input);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.lucene.analysis.TokenStream#incrementToken()
	 */
	@Override
	public final boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			CharacterUtils.toLowerCase(termAtt.buffer(), 0, termAtt.length());
			return true;
		} else
			return false;
	}

}
