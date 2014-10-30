package FIIT.VI.YAGO.reader;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import FIIT.VI.YAGO.domain.Article;

public class CategoryReaderTest {

	private final static String DATA = "src//resources//test//yagoTypes.txt";
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
		Assert.assertEquals(5, a.getCategories().size());

		String[] categories = new String[] { "wikicategory_Manchu_people",
				"wordnet_sovereign_110628644",
				"wikicategory_People_of_the_Xinhai_Revolution",
				"wikicategory_People_of_Manchukuo",
				"wikicategory_Child_rulers_from_Asia" };
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
				"wikicategory_American_people_of_French_descent",
				"wikicategory_People_from_Crowley,_Louisiana",
				"wikicategory_Cajun_people",
				"wikicategory_People_from_Avoyelles_Parish,_Louisiana",
				"wikicategory_Living_people" };
		for (int i = 0; i < a.getCategories().size(); i++) {
			Assert.assertEquals(categories[i], a.getCategories().get(i));
		}
	}
}
