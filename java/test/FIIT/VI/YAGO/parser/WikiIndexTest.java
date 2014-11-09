package FIIT.VI.YAGO.parser;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.junit.Before;
import org.junit.Test;

public class WikiIndexTest {

	private WikiIndex indexFolder;
	private WikiIndex ramFolder;

	private String PATH = "src//resources//test//wiki//";
	private static SimpleFSDirectory indexDirection;
	private final static String INDEX = "indexTest";

	@Before
	public void setup() throws IOException {
		indexDirection = new SimpleFSDirectory(new File(INDEX));
		indexFolder = new WikiIndex(indexDirection);
		Directory directory = new RAMDirectory();
		File folder = new File(PATH);

		ramFolder = new WikiIndex(directory);

		ramFolder.createIndex(folder, false);
		indexFolder.createIndex(folder, false);
	}

	@Test
	public void testFolder() throws IOException, ParseException {
		List<Document> docs = indexFolder.searchIndex("Kid Rock");
		assertFalse(docs.size() == 0);
	}

	@Test
	public void testRam() throws IOException, ParseException {
		List<Document> docs = ramFolder.searchIndex("Living people");
		assertFalse(docs.size() == 0);
	}

}
