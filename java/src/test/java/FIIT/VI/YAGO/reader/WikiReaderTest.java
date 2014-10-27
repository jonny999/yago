package FIIT.VI.YAGO.reader;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import FIIT.VI.YAGO.domain.Article;

public class WikiReaderTest {

	private final static String DATA = "src//resources//test//sample_yago_wiki.txt";
	private WikiReader reader;

	@Before
	public void setup() throws IOException {
		reader = new WikiReader(DATA);
	}

	@Test
	public void countLines() throws IOException {
		long size = reader.linesCount();
		Assert.assertEquals(84, size);
	}

	@Test
	public void readArticlesCount() throws IOException {
		long size = 0;
		while (reader.readArticle() != null) {
			size++;
		}
		Assert.assertEquals(24, size);
	}

	@Test
	public void readFirstArticles() throws IOException {
		reader.reload();
		Article a = reader.readArticle();

		Assert.assertEquals("Puyi", a.getName());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Puyi",
				a.getUlrWikipedia());
		String[] linksTo = new String[] { "Hirohito", "Zhou_Enlai" };

		for (int i = 0; i < linksTo.length; i++) {
			Assert.assertEquals(linksTo[i], a.getLinksTo().get(i));
		}

	}

	@Test
	public void readSecondArticles() throws IOException {
		reader.readArticle();
		Article a = reader.readArticle();

		Assert.assertEquals("Edwin_Edwards", a.getName());
		Assert.assertEquals("http://en.wikipedia.org/wiki/Edwin_Edwards",
				a.getUlrWikipedia());

		Assert.assertEquals(Boolean.TRUE, a.getLinksTo().isEmpty());

	}

}
