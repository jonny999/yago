package FIIT.VI.YAGO.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import junit.framework.Assert;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

public class YagoParserIndexTest {

	private final static String TEST_DATA_NAME = "src//resources//test//sample_yago_wiki.txt";
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private final static String INDEX = "indexTest";
	
	private static SimpleFSDirectory indexDirection;
	private static IndexSearcher searcher;
	

	@Before
	public void createIndex() throws IOException {
		indexDirection = new SimpleFSDirectory(new File(INDEX));
		
		if (!DirectoryReader.indexExists(indexDirection)) {
			
			YagoParser.processYagoData(TEST_DATA_NAME, ENCODING,
					new IndexWriter(indexDirection, new IndexWriterConfig(
							Version.LATEST, new StandardAnalyzer())));
		}
		
		IndexReader reader = DirectoryReader.open(indexDirection);
		searcher = new IndexSearcher(reader);
	}

	public ScoreDoc[] searchString(String searchString) throws ParseException,
			IOException {

		BooleanQuery query = new BooleanQuery();
		QueryParser q1 = new QueryParser("subject", new StandardAnalyzer());
		
		query.add(q1.parse(searchString), Occur.SHOULD);

		TopScoreDocCollector collector = TopScoreDocCollector.create(100, true);
		searcher.search(query, collector);

		return collector.topDocs().scoreDocs;
	}

	@Test
	public void searchIndex() throws ParseException, IOException {

		ScoreDoc[] hits = searchString("Kid_Rock");
		Assert.assertTrue(1 <= hits.length);
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			assertEquals(d.get("object"),
					"http://en.wikipedia.org/wiki/Kid_Rock");
			assertEquals(d.get("subject"), "Kid_Rock");
		}
	}

	@Test
	public void searchIndexSecond() throws ParseException, IOException {

		ScoreDoc[] hits = searchString("asasasas");
		assertEquals(0, hits.length);
	}
}
