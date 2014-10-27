package FIIT.VI.YAGO.parser;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import FIIT.VI.YAGO.reader.WikiReader;

public class WikiParserTest {

	private final static String DATA = "src//resources//test//sample_yago_wiki.txt";
	private WikiReader reader;

	@Before
	public void setup() throws IOException {
		reader = new WikiReader(DATA);
		WikiParser.createWikiArticles("src//resources//test//wiki//",reader);
	}
	
	@Test
	public void test() {
	}

}
