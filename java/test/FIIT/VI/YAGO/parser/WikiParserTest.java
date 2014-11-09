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
import FIIT.VI.YAGO.parser.WikiParser;
import FIIT.VI.YAGO.reader.CategoryReader;
import FIIT.VI.YAGO.reader.NamesReader;
import FIIT.VI.YAGO.reader.WikiReader;

public class WikiParserTest {

	private final static String DATA_WIKI = "src//resources//test//sample_yago_wiki.txt";
	private final static String DATA_CATEGORY = "src//resources//test//yagoTypes.txt";
	private final static String DATA_NAME = "src//resources//test//yagoMultilingualInstanceLabels.txt";

	private WikiReader wikiReader;
	private NamesReader nameReader;
	private CategoryReader categoryReader;
	
	
	
	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() throws IOException {
		wikiReader = new WikiReader(DATA_WIKI);
		nameReader = new NamesReader(DATA_NAME);
		categoryReader = new CategoryReader(DATA_CATEGORY);
		String path = "src//resources//test//wiki//";
		
		WikiParser.createBaseWikiArticles(path, wikiReader);
		WikiParser.updateBaseWikiArticlesAlternativeNames(path, nameReader);
		WikiParser.updateBaseWikiArticlesCategories(path, categoryReader);
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
