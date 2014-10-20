package FIIT.VI.YAGO.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class Configuration {

	private static SimpleFSDirectory indexDirection;
	private static Analyzer analyzer;
	private static IndexWriterConfig indexConfig;
	private static IndexSearcher searcher;

	private final static String PRIMARY_DATA_NAME = "src//resources//data//sample_yago_wiki.txt";
	private final static Charset ENCODING = StandardCharsets.UTF_8;

	private Configuration() {
	}

	public static String getDefaultData() {
		return PRIMARY_DATA_NAME;
	}

	public static Charset getDefaultEncoding() {
		return ENCODING;
	}

	private static String getDefaultFileLocation() {
		return "index\\";
	}

	public static IndexSearcher getIndexSearcher() throws IOException {

		if (searcher == null) {
			searcher = new IndexSearcher(
					DirectoryReader.open(getDefaultIndexDir()));
		}

		return searcher;
	}

	public static SimpleFSDirectory getDefaultIndexDir() throws IOException {

		if (indexDirection == null) {
			indexDirection = new SimpleFSDirectory(new File(
					getDefaultFileLocation()));
		}

		return indexDirection;
	}

	public static Analyzer getDefaultAnalyzer() {

		if (analyzer == null) {
			analyzer = new StandardAnalyzer();
		}

		return analyzer;
	}

	public static IndexWriterConfig getDefaultIndexConfig() {

		if (indexConfig == null) {
			indexConfig = new IndexWriterConfig(Version.LATEST,
					getDefaultAnalyzer());
		}

		return indexConfig;
	}

}
