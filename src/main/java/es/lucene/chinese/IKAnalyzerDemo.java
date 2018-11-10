/**
 * 
 */
package es.lucene.chinese;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

/**
 * 中文解析原理
 * 这里分析IKAnalyzer的使用方法，以及解析原理
 * 核心的数据结构是DictSegment
 * 核心的算法是org.wltea.analyzer.core.CJKSegmenter.analyze(AnalyzeContext)方法
 *  调用Dictionary的match方法
 * @author az6367
 *
 */
public class IKAnalyzerDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    Analyzer analyzer = new IKAnalyzer(true);
	    
	    StringReader reader = new StringReader(
		          "好姑娘，我在中国吃饭了IKAnalyer can analysis english text too");
	    

	    TokenStream ts = null;
	    try {
	      ts = analyzer.tokenStream("myfield", reader);
	      OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
	      CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
	      TypeAttribute type = ts.addAttribute(TypeAttribute.class);

	      ts.reset();
	      while (ts.incrementToken()) {
	        System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : "
	            + term.toString() + " | " + type.type());
	      }
	      ts.end(); // Perform end-of-stream operations, e.g. set the final offset.

	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      if (ts != null) {
	        try {
	          ts.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	    }

	}

}
