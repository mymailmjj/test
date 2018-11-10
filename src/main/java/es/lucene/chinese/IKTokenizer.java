/**
 * 
 */
package es.lucene.chinese;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * @author az6367
 *
 */
public final class IKTokenizer extends Tokenizer {
	  private IKSegmenter _IKImplement;

	  private final CharTermAttribute termAtt;
	  private final OffsetAttribute offsetAtt;
	  private final TypeAttribute typeAtt;
	  private int endPosition;

	  /**
	   * Lucene 4.0 Tokenizer閫傞厤鍣ㄧ被鏋勯�犲嚱鏁�
	   * @param in
	   * @param useSmart
	   */
	  public IKTokenizer(Reader in, boolean useSmart) {
	    setReader(in);
	    offsetAtt = addAttribute(OffsetAttribute.class);
	    termAtt = addAttribute(CharTermAttribute.class);
	    typeAtt = addAttribute(TypeAttribute.class);
	    _IKImplement = new IKSegmenter(input, useSmart);
	  }

	  /*
	   * (non-Javadoc)
	   * @see org.apache.lucene.analysis.TokenStream#incrementToken()
	   */
	  @Override
	  public boolean incrementToken() throws IOException {
	    clearAttributes();
	    Lexeme nextLexeme = _IKImplement.next();
	    if (nextLexeme != null) {
	      termAtt.append(nextLexeme.getLexemeText());
	      termAtt.setLength(nextLexeme.getLength());
	      offsetAtt.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
	      endPosition = nextLexeme.getEndPosition();
	      typeAtt.setType(nextLexeme.getLexemeTypeString());
	      return true;
	    }
	    // 杩斾細false鍛婄煡璇嶅厓杈撳嚭瀹屾瘯
	    return false;
	  }

	  /*
	   * (non-Javadoc)
	   * @see org.apache.lucene.analysis.Tokenizer#reset(java.io.Reader)
	   */
	  @Override
	  public void reset() throws IOException {
	    super.reset();
	    _IKImplement.reset(input);
	  }

	  @Override
	  public final void end() {
	    // set final offset
	    int finalOffset = correctOffset(this.endPosition);
	    offsetAtt.setOffset(finalOffset, finalOffset);
	  }

}
