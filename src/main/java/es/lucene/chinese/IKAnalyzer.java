/**
 * 
 */
package es.lucene.chinese;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

/**
 * @author az6367
 *
 */
public final class IKAnalyzer extends Analyzer {

	private Reader reader;

	@Override
	protected Reader initReader(String fieldName, Reader reader) {
		this.reader = reader;
		return super.initReader(fieldName, reader);
	}

	private boolean useSmart;

	public boolean useSmart() {
		return useSmart;
	}

	public void setUseSmart(boolean useSmart) {
		this.useSmart = useSmart;
	}

	/**
	 * IK鍒嗚瘝鍣↙ucene Analyzer鎺ュ彛瀹炵幇绫�
	 *
	 * 榛樿缁嗙矑搴﹀垏鍒嗙畻娉�
	 */
	public IKAnalyzer() {
		this(false);
	}

	/**
	 * IK鍒嗚瘝鍣↙ucene Analyzer鎺ュ彛瀹炵幇绫�
	 *
	 * @param useSmart
	 *            褰撲负true鏃讹紝鍒嗚瘝鍣ㄨ繘琛屾櫤鑳藉垏鍒�
	 */
	public IKAnalyzer(boolean useSmart) {
		super();
		this.useSmart = useSmart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.lucene.analysis.Analyzer#createComponents(java.lang.String)
	 */
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer _IKTokenizer = new IKTokenizer(reader, this.useSmart());
		return new TokenStreamComponents(_IKTokenizer);
	}

}
