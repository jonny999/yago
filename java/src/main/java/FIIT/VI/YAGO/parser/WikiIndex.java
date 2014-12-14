package FIIT.VI.YAGO.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
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
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.junit.Assert;

import FIIT.VI.YAGO.domain.Article;

public class WikiIndex {

	private final static List<String> SEARCH_VARIABLES = Arrays.asList("name",
			"ulrWikipedia", "category", "link", "name");
	private final static int COUNT = 100;

	private Analyzer analyzer = new SimpleAnalyzer();
	private Directory directory;
	private IndexWriterConfig writerConfig = new IndexWriterConfig(
			Version.LATEST, analyzer);

	public WikiIndex(Directory directory) {
		this.directory = directory;
	}

	public WikiIndex(Directory oldDirectory, Directory newDirectory)
			throws IOException {

		IndexWriter writter = new IndexWriter(newDirectory, this.reloadConfig());
		Assert.assertTrue(DirectoryReader.indexExists(oldDirectory));
		writter.addIndexes(oldDirectory);
		writter.close();
		this.directory = newDirectory;
	}

	public boolean existIndex() throws IOException {
		Assert.assertNotNull(directory);
		return DirectoryReader.indexExists(directory);
	}

	public void createIndex(final File folder, boolean override)
			throws IOException {
		IndexWriter writter = new IndexWriter(directory, this.reloadConfig());
		String pathToFile;

		boolean tmp = (this.existIndex() && override) || (!this.existIndex());
		int count =0;
		
		if (tmp) {

			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) {
					continue;
				} else {
					if (fileEntry.isFile()) {
						pathToFile = fileEntry.getName();
						if ((pathToFile.substring(
								pathToFile.lastIndexOf('.') + 1,
								pathToFile.length()).toLowerCase())
								.equals("json")) {

							Article a = WikiParser.loadArticle(fileEntry
									.getAbsolutePath());

							if (a != null) {
								count++;
								System.out.println(count);
								writter.addDocument(a.document());
							}
						}
					}
				}
			}
		}

		writter.close();
	}

	public List<Document> searchIndex(String searchString) throws IOException,
			ParseException {
		return searchIndex(searchString, SEARCH_VARIABLES, COUNT);
	}

	public List<Document> searchIndex(String searchString, List<String> where,
			int count) throws IOException, ParseException {

		IndexSearcher searcher = this.loadSearcher();
		List<Document> documents = new ArrayList<Document>();

		ScoreDoc[] docs = this.searchString(searcher, searchString, where,
				count);

		for (ScoreDoc d : docs) {
			documents.add(searcher.doc(d.doc));
		}

		return documents;
	}

	private ScoreDoc[] searchString(IndexSearcher searcher,
			String searchString, List<String> where, int count)
			throws ParseException, IOException {

		BooleanQuery query = new BooleanQuery();

		for (String s : where) {
			QueryParser qParser = new QueryParser(s, analyzer);
			query.add(qParser.parse(searchString), Occur.SHOULD);
		}

		TopScoreDocCollector collector = TopScoreDocCollector.create(count,
				true);
		searcher.search(query, collector);

		return collector.topDocs().scoreDocs;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	public void setWriterConfig(IndexWriterConfig writerConfig) {
		this.writerConfig = writerConfig;
	}

	private IndexWriterConfig reloadConfig() {
		this.writerConfig = new IndexWriterConfig(Version.LATEST, analyzer);
		return writerConfig;
	}

	private IndexSearcher loadSearcher() throws IOException {
		IndexReader reader = DirectoryReader.open(directory);
		return new IndexSearcher(reader);
	}
}
