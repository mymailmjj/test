package lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.InfoStream;
import org.apache.lucene.util.PrintStreamInfoStream;

import junit.framework.TestCase;

public class LuceneMain extends TestCase {
	
	private void indexFile(IndexWriter iwriter,File file) throws IOException{
		
		System.out.println("加入文件:"+file.getName());
		
		Document doc = new Document();
		
		FileReader reader = new FileReader(file);
		
		StringField stringField = new StringField("title", file.getName(), Store.YES);
		
		doc.add(stringField);
		
		doc.add(new TextField("contents", reader));
		
		iwriter.addDocument(doc);
		
	}
	

	/**
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public void testDemoForLucene() throws IOException{
		
		PrintStreamInfoStream stream = new PrintStreamInfoStream(System.out);
		
		InfoStream.setDefault(stream);

		Analyzer analyzer = new StandardAnalyzer();

		// Store the index in memory:
		// Directory directory = new RAMDirectory();
		// To store an index on disk, use this instead:

		Path path = FileSystems.getDefault().getPath("C://Users//mymai//Desktop//0005//index");
		Directory directory = FSDirectory.open(path);
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		config.setUseCompoundFile(false);
		config.setOpenMode(OpenMode.CREATE);
		IndexWriter iwriter = new IndexWriter(directory, config);
		
		Path docpath = FileSystems.getDefault().getPath("C://Users//mymai//Desktop//0005//lucene");
		
		Files.walkFileTree(docpath, new SimpleFileVisitor<Path>(){

			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
				indexFile(iwriter, path.toFile());
				return FileVisitResult.CONTINUE;
			}
			
		});
		
		iwriter.close();
		

		// Now search the index:
	/*	DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		// Parse a simple query that searches for "text":
		QueryParser parser = new QueryParser("contents", analyzer);
		Query query = parser.parse("redis");
		
		TopDocs search = isearcher.search(query, 100);
		ScoreDoc[] hits = search.scoreDocs;
		System.out.println(search.totalHits);
		// Iterate through the results:
		for (int i = 0; i < hits.length; i++) {
			Document hitDoc = isearcher.doc(hits[i].doc);
			String string = hitDoc.get("title");
			System.out.println(string);
		}
		ireader.close();
		directory.close();*/

	}

	public void testOriginalDemo() throws IOException, ParseException{
		
		PrintStreamInfoStream stream = new PrintStreamInfoStream(System.out);
		
		InfoStream.setDefault(stream);
		

		Analyzer analyzer = new StandardAnalyzer();

		// Store the index in memory:
		Directory directory = new RAMDirectory();
		// To store an index on disk, use this instead:
		// Directory directory = FSDirectory.open("/tmp/testindex");
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter iwriter = new IndexWriter(directory, config);
		Document doc = new Document();
		String text = "This is the text to be indexed.";
		doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
		iwriter.addDocument(doc);
		iwriter.close();

		// Now search the index:
		DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		// Parse a simple query that searches for "text":
		QueryParser parser = new QueryParser("fieldname", analyzer);
		Query query = parser.parse("text");
		ScoreDoc[] hits = isearcher.search(query, 1000, Sort.RELEVANCE).scoreDocs;
		assertEquals(1, hits.length);
		// Iterate through the results:
		for (int i = 0; i < hits.length; i++) {
			Document hitDoc = isearcher.doc(hits[i].doc);
			assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
		}
		ireader.close();
		directory.close();

	}

}
