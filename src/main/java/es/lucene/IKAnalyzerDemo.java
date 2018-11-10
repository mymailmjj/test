package es.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;

public class IKAnalyzerDemo {

	public static void main(String[] args) {

		try {
			// 建立索引
			String text1 = "The Lucene PMC is pleased to announce the release of Apache Lucene 6.6.5 and Apache Solr 6.6.5";
			String title1 = "title1";
			String text2 = "The new IntervalQuery implements proximity search based on minimum-interval semantics";
			String title2 = "title2";
			String text3 = "New soft-delete mechanism with a configurable retention policy";
			String title3 = "title3";
			String fieldName = "contents";
			Analyzer analyzer = new StandardAnalyzer();
			RAMDirectory directory = new RAMDirectory();
			IndexWriterConfig writerConfig = new IndexWriterConfig(analyzer);
			IndexWriter indexWriter = new IndexWriter(directory, writerConfig);
			Document document1 = new Document();
			document1.add(new StringField("ID", "1", Field.Store.YES));
			document1.add(new TextField("title", title1, Field.Store.YES));
			document1.add(new TextField(fieldName, text1, Field.Store.YES));
			indexWriter.addDocument(document1);

			Document document2 = new Document();
			document2.add(new StringField("ID", "2", Field.Store.YES));
			document2.add(new TextField("title", title2, Field.Store.YES));
			document2.add(new TextField(fieldName, text2, Field.Store.YES));
			indexWriter.addDocument(document2);

			Document document3 = new Document();
			document3.add(new StringField("ID", "3", Field.Store.YES));
			document3.add(new TextField("title", title3, Field.Store.YES));
			document3.add(new TextField(fieldName, text3, Field.Store.YES));
			indexWriter.addDocument(document3);
			indexWriter.close();

			// 搜索
			DirectoryReader indexReader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(indexReader);
			// 要查找的字符串数组
			String[] stringQuery = { "IntervalQuery", "Apache" };
			// 待查找字符串对应的字段
			String[] fields = { "content", "title" };
			// Occur.MUST表示对应字段必须有查询值， Occur.MUST_NOT
			// 表示对应字段必须没有查询值，Occur.SHOULD表示对应字段应该存在查询值（但不是必须）
			Occur[] occ = { Occur.SHOULD, Occur.SHOULD };

			try {
				Query query = MultiFieldQueryParser.parse(stringQuery, fields, occ, analyzer);
				TopDocs topDocs = searcher.search(query, 5);
				System.out.println("命中数:" + topDocs.totalHits);
				ScoreDoc[] docs = topDocs.scoreDocs;
				for (ScoreDoc doc : docs) {
					Document d = searcher.doc(doc.doc);
					System.out.println("内容:" + d.get(fieldName));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} finally {
				if (indexReader != null) {
					try {
						indexReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (directory != null) {
					try {
						directory.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
