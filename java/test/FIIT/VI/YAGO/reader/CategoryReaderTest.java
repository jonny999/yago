package FIIT.VI.YAGO.reader;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import FIIT.VI.YAGO.domain.Article;

public class CategoryReaderTest {

	private final static String DATA = "..//data//sample_yago_types.txt";
	private CategoryReader reader;

	@Before
	public void setup() throws IOException {
		reader = new CategoryReader(DATA);
	}

	@Test
	public void countLines() throws IOException {
		long size = reader.linesCount();
		Assert.assertEquals(102, size);
	}

	@Test
	public void readArticlesCount() throws IOException {
		long size = 0;
		while (reader.readArticle() != null) {
			size++;
		}
		Assert.assertEquals(11, size);
	}

	@Test
	public void readFirstArticles() throws IOException {
		reader.reload();
		Article a = reader.readArticle();
		Assert.assertEquals("Puyi", a.getName());
		Assert.assertEquals(4, a.getCategories().size());

		String[] categories = new String[] { "Manchu people",
				"People of the Xinhai Revolution",
				"People of Manchukuo",
				"Child rulers from Asia" };
		for (int i = 0; i < a.getCategories().size(); i++) {
			Assert.assertEquals(categories[i], a.getCategories().get(i));
		}

	}

	@Test
	public void readSecondArticles() throws IOException {
		reader.readArticle();
		Article a = reader.readArticle();
		Assert.assertEquals("Edwin_Edwards", a.getName());
		Assert.assertEquals(5, a.getCategories().size());

		String[] categories = new String[] {
				"American people of French descent",
				"People from Crowley, Louisiana",
				"Cajun people",
				"People from Avoyelles Parish, Louisiana",
				"Living people" };
		for (int i = 0; i < a.getCategories().size(); i++) {
			Assert.assertEquals(categories[i], a.getCategories().get(i));
		}
	}
}
