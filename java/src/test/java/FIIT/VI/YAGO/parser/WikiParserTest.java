package FIIT.VI.YAGO.parser;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.reader.WikiReader;

public class WikiParserTest {

	private final static String DATA = "src//resources//test//sample_yago_wiki.txt";
	private WikiReader reader;
	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() throws IOException {
		reader = new WikiReader(DATA);
		WikiParser.createBaseWikiArticles("src//resources//test//wiki//", reader);
	}

	@Test
	public void testSaveFiles() throws JsonParseException,
			JsonMappingException, IOException {
		Article a = mapper.readValue(new File(
				"src//resources//test//wiki//Amitabh_Bachchan.json"),
				Article.class);
		Assert.assertEquals("Amitabh_Bachchan", a.getName());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Amitabh_Bachchan",
				a.getUlrWikipedia());
		String[] linksTo = new String[] { "Satyajit_Ray" };

		for (int i = 0; i < linksTo.length; i++) {
			Assert.assertEquals(linksTo[i], a.getLinksTo().get(i));
		}

	}

}
