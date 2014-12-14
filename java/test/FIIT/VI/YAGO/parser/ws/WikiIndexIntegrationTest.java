package FIIT.VI.YAGO.parser.ws;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.junit.Before;
import org.junit.Test;

import FIIT.VI.YAGO.index.WikiIndex;

public class WikiIndexIntegrationTest {

	private WikiIndex indexFolder;

	private String PATH = "src//resources//test//data//";
	private static SimpleFSDirectory indexDirection;
	private final static String INDEX = "/media/mm/Data/index";

	@Before
	public void setup() throws IOException {
		indexDirection = new SimpleFSDirectory(new File(INDEX));
		indexFolder = new WikiIndex(indexDirection);
		File folder = new File(PATH);

		indexFolder.createIndex(folder, false);
	}

	@Test
	public void testFolder() throws IOException, ParseException {
		List<Document> docs = indexFolder.searchIndex("Kid Rock");
		assertFalse(docs.size() == 0);
	}
}
