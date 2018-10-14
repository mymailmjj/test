/**
 * 
 */
package lucene;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.RangeQueryBuilder;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import junit.framework.TestCase;

/**
 * @author az6367
 *
 */
public class QueryTest extends TestCase {

	private Path path = null;

	private Directory directory = null;

	private DirectoryReader ireader = null;

	private Analyzer analyzer = null;

	protected void setUp() throws Exception {

		path = FileSystems.getDefault().getPath("C://Users//mymai//Desktop//0005//index");
		directory = FSDirectory.open(path);

		ireader = DirectoryReader.open(directory);

		analyzer = new StandardAnalyzer();

		super.setUp();
	}

	private void executeQuery(Query query) {
		try {
			IndexSearcher isearcher = new IndexSearcher(ireader);

			TopDocs search = isearcher.search(query, 100);
			ScoreDoc[] hits = search.scoreDocs;
			System.out.println(search.totalHits);
			// Iterate through the results:
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				String string = hitDoc.get("contents");
				System.out.println(string);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testTermQuery() {

		Query termQuery = new TermQuery(new Term("contents", "announced"));

		executeQuery(termQuery);

	}

	public void testPrefixQuery() {

		Query prefixQuery = new PrefixQuery(new Term("contents", "announced"));

		executeQuery(prefixQuery);

	}

	public void testFuzzyQuery() {
		Query fuzzyQuery = new FuzzyQuery(new Term("title", "tomcat"));
		executeQuery(fuzzyQuery);
	}

	/**
	 * 通配符
	 */
	public void testWildCardQuery() {
		WildcardQuery wildcardQuery = new WildcardQuery(new Term("contents", "SYS_157"));
		executeQuery(wildcardQuery);
	}

	public void rangeQuery() {
		RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder();

	}

	public void testPhraseQuery() {
		PhraseQuery phraseQuery = new PhraseQuery("contents", "redis");
		// phraseQuery
		executeQuery(phraseQuery);
	}

	public void testMultiFieldQuery() throws ParseException {

		String[] stringQuery = { "lucense", "tomcat" };
		// 待查找字符串对应的字段
		String[] fields = { "contents", "title" };
		// Occur.MUST表示对应字段必须有查询值， Occur.MUST_NOT
		// 表示对应字段必须没有查询值，Occur.SHOULD表示对应字段应该存在查询值（但不是必须）
		Occur[] occ = { Occur.SHOULD, Occur.SHOULD };

		Query query = MultiFieldQueryParser.parse(stringQuery, fields, occ, analyzer);

		executeQuery(query);

	}

	@Override
	protected void tearDown() throws Exception {
		ireader.close();
		directory.close();

		super.tearDown();
	}

}
