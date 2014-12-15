package FIIT.VI.YAGO.parser.ws;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import FIIT.VI.YAGO.parser.WikiParser;
import FIIT.VI.YAGO.reader.CategoryReader;
import FIIT.VI.YAGO.reader.DbInstanceReader;
import FIIT.VI.YAGO.reader.NamesReader;
import FIIT.VI.YAGO.reader.WikiReader;

public class WikiParserIntegrationTest {

	private final static String DATA_WIKI = "src//resources//data//yagoWikipediaInfo.ttl";
	private final static String DATA_CATEGORY = "src//resources//data//yagoTypes.ttl";
	private final static String DATA_NAME = "src//resources//data//yagoMultilingualInstanceLabels.ttl";
	private final static String DATA_URL = "src//resources//data//yagoDBpediaInstances.ttl";

	private WikiReader wikiReader;
	private NamesReader nameReader;
	private CategoryReader categoryReader;
	private DbInstanceReader dbInstanceReader;

	@Before
	public void setup() throws IOException {
		wikiReader = new WikiReader(DATA_WIKI);
		nameReader = new NamesReader(DATA_NAME);
		categoryReader = new CategoryReader(DATA_CATEGORY);
		dbInstanceReader = new DbInstanceReader(DATA_URL);
		String path = "src//resources//test//data//";

		System.out.println("START WIKI");
		WikiParser.createBaseWikiArticles(path, wikiReader);
		System.out.println("START NAMES");
		WikiParser.updateBaseWikiArticlesAlternativeNames(path, nameReader);
		System.out.println("START CATEGORY");
		WikiParser.updateBaseWikiArticlesCategories(path, categoryReader);
		System.out.println("START DB INSTANCE");
		WikiParser.updateBaseWikiArticlesAlternativeLinks(path,
				dbInstanceReader);
	}

	@Test
	public void testSaveFiles() {
		Assert.assertTrue(true);
	}

}
