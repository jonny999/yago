package FIIT.VI.YAGO.index;

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
import FIIT.VI.YAGO.parser.WikiParser;

/**
 * Wikipedia index entity for index and search
 * @author mm
 *
 */
public class WikiIndex {

	/** Default search variables*/
	private final static List<String> SEARCH_VARIABLES = Arrays.asList("name",
			"ulrWikipedia", "category", "link", "wikiName");
	
	/**Default count of result for search*/
	private final static int COUNT = 10;

	/** Default analyzer*/
	private Analyzer analyzer = new SimpleAnalyzer();
	
	/** Directory for save index files*/
	private Directory directory;
	
	/**Default writer config*/
	private IndexWriterConfig writerConfig = new IndexWriterConfig(
			Version.LATEST, analyzer);

	public WikiIndex(Directory directory) {
		this.directory = directory;
	}

	/**
	 * Constructor for copy index data from old directory to new
	 * @param oldDirectory old directory for index
	 * @param newDirectory new directory for index
	 * @throws IOException
	 */
	public WikiIndex(Directory oldDirectory, Directory newDirectory)
			throws IOException {

		IndexWriter writter = new IndexWriter(newDirectory, this.reloadConfig());
		Assert.assertTrue(DirectoryReader.indexExists(oldDirectory));
		writter.addIndexes(oldDirectory);
		writter.close();
		this.directory = newDirectory;
	}

	/** Check if index exist*/
	public boolean existIndex() throws IOException {
		Assert.assertNotNull(directory);
		return DirectoryReader.indexExists(directory);
	}

	/** Create index index in file, override if exist
	 * 
	 * @param folder folder for create indexes
	 * @param override information if override old index
	 * @throws IOException
	 */
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

	/**
	 * Search in index based on default settings
	 * @param searchString search string 
	 * @return list of lucene documents
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Document> searchIndex(String searchString) throws IOException,
			ParseException {
		return searchIndex(searchString, SEARCH_VARIABLES, COUNT);
	}

	/**
	 * Search in index based on input settings
	 * @param searchString search string
	 * @param where which variables has search
	 * @param count count of max results
	 * @return list of lucene documents
	 * @throws IOException
	 * @throws ParseException
	 */
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

	/**
	 * Search in index based on default settings
	 * @param search search string
	 * @return lucene scoredocs
	 * @throws ParseException
	 * @throws IOException
	 */
	public ScoreDoc[] searchScoreDoc(String search) throws ParseException, IOException{
		return searchString(this.loadSearcher(),search,SEARCH_VARIABLES,COUNT);
	}
	
	/**
	 * Search in index based on input settings
	 * @param searcher index where to search
	 * @param searchString search string
	 * @param where which variables has search
	 * @param count max count of results
	 * @return lucene scoredocs
	 * @throws ParseException
	 * @throws IOException
	 */
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

	/**
	 * Reload actual config
	 * @return actual index writer config
	 */
	private IndexWriterConfig reloadConfig() {
		this.writerConfig = new IndexWriterConfig(Version.LATEST, analyzer);
		return writerConfig;
	}

	/**
	 * Load actual index searcher
	 * @return actual index search based on actual state
	 * @throws IOException
	 */
	private IndexSearcher loadSearcher() throws IOException {
		IndexReader reader = DirectoryReader.open(directory);
		return new IndexSearcher(reader);
	}
}
