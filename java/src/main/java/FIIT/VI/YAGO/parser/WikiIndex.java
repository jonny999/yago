package FIIT.VI.YAGO.parser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import FIIT.VI.YAGO.domain.Article;

public class WikiIndex {

	private final static List<String> SEARCH_VARIABLES = Arrays.asList("name",
			"ulrWikipedia", "category", "link", "name");

	private Analyzer analyzer = new SimpleAnalyzer();
	private Directory directory;
	private IndexWriterConfig writerConfig = new IndexWriterConfig(
			Version.LATEST, analyzer);

	public WikiIndex(Directory directory) {
		this.directory = directory;
	}

	public void createIndex(final File folder) throws IOException {
		IndexWriter writter = new IndexWriter(directory, this.reloadConfig());
		String pathToFile;

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				continue;
			} else {
				if (fileEntry.isFile()) {
					pathToFile = fileEntry.getName();
					if ((pathToFile.substring(pathToFile.lastIndexOf('.') + 1,
							pathToFile.length()).toLowerCase()).equals("json")) {

						Article a = WikiParser.loadArticle(fileEntry.getAbsolutePath());

						if (a != null) {
							writter.addDocument(a.document());
						}
					}
				}
			}
		}

		writter.close();
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

}
