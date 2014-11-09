package FIIT.VI.YAGO.parser;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.store.SimpleFSDirectory;
import org.junit.Before;
import org.junit.Test;

public class WikiIndexTest {

	private WikiIndex index;
	private String PATH = "src//resources//test//wiki//";
	private static SimpleFSDirectory indexDirection;
	private final static String INDEX = "indexTest";

	@Before
	public void setup() throws IOException {
		indexDirection = new SimpleFSDirectory(new File(INDEX));
		index = new WikiIndex(indexDirection);
		File folder = new File(PATH);
		index.createIndex(folder);
	}
	
	@Test
	public void test() {
		// TODO Auto-generated constructor stub
	}
	
}
