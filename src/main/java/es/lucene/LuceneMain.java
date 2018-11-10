package es.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Random;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.lucene50.Lucene50StoredFieldsFormat.Mode;
import org.apache.lucene.codecs.lucene54.Lucene54DocValuesFormat;
import org.apache.lucene.codecs.lucene62.Lucene62Codec;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
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
import org.junit.Test;

import junit.framework.TestCase;

public class LuceneMain extends TestCase {

	private void indexFile(IndexWriter iwriter, File file) throws IOException {

		System.out.println("加入文件:" + file.getName());

		int nextInt = new Random().nextInt();

		System.out.println("index point:" + nextInt);

		Document doc = new Document();

		FileReader reader = new FileReader(file);

		Field stringField = new StringField("title", file.getName(), Store.YES);

		doc.add(stringField);

		doc.add(new IntPoint("titlePoint", nextInt));

		doc.add(new TextField("contents", reader));

		iwriter.addDocument(doc);

	}

	/**
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void testDemoForLucene() throws IOException, ParseException {

		// 开启打印日志功能
		PrintStreamInfoStream stream = new PrintStreamInfoStream(System.out);

		InfoStream.setDefault(stream);

		Analyzer analyzer = new StandardAnalyzer();

		// Store the index in memory:
		// Directory directory = new RAMDirectory();
		// To store an index on disk, use this instead:

		Path path = FileSystems.getDefault().getPath("C://Users//AZ6367//Desktop//0000//index");
		Directory directory = FSDirectory.open(path);
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		config.setOpenMode(OpenMode.CREATE);
		config.setUseCompoundFile(false);
		// config.setUseCompoundFile(false);
		// config.setCodec(new Lucene62Codec(Mode.BEST_SPEED));

		IndexWriter iwriter = new IndexWriter(directory, config);

		Path docpath = FileSystems.getDefault().getPath("C://Users//AZ6367//Desktop//0000//lucene");

		Files.walkFileTree(docpath, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
				indexFile(iwriter, path.toFile());
				return FileVisitResult.CONTINUE;
			}

		});

		iwriter.close();

		// Now search the index:
		/*
		 * DirectoryReader ireader = DirectoryReader.open(directory);
		 * IndexSearcher isearcher = new IndexSearcher(ireader); // Parse a
		 * simple query that searches for "text": QueryParser parser = new
		 * QueryParser("contents", analyzer); Query query =
		 * parser.parse("redis");
		 * 
		 * TopDocs search = isearcher.search(query, 100); ScoreDoc[] hits =
		 * search.scoreDocs; System.out.println(search.totalHits); // Iterate
		 * through the results: for (int i = 0; i < hits.length; i++) { Document
		 * hitDoc = isearcher.doc(hits[i].doc); String string =
		 * hitDoc.get("title"); System.out.println(string); } ireader.close();
		 * directory.close();
		 */

	}

	@Test
	public void testOriginalDemo() throws IOException, ParseException {

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
