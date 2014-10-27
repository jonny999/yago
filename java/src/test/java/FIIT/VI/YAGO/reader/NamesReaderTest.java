package FIIT.VI.YAGO.reader;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import FIIT.VI.YAGO.domain.Article;

public class NamesReaderTest {

	private final static String DATA = "src//resources//test//yagoMultilingualInstanceLabels.txt";
	private NamesReader reader;

	@Before
	public void setup() throws IOException {
		reader = new NamesReader(DATA);
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
		Assert.assertEquals(4, size);
	}

	@Test
	public void readFirstArticles() throws IOException {
		reader.reload();
		Article a = reader.readArticle();

		Assert.assertEquals("Puyi", a.getName());
		String[] names = new String[] { "بوئي" };

		for (int i = 0; i < names.length; i++) {
			Assert.assertEquals(names[i], a.getNames().get(i).getName());
		}
		Assert.assertEquals(39, a.getNames().size());

		String alternativeNames = a.toAlternativesNames();
		Assert.assertEquals(
				"Puyi	بوئي	Пу И	Pu Yi	Pchu I	Puyi	Pu Yi	Pu Yi	Puyi	Pu Yi	پویی	Puyi	Puyi	푸이	Kaisar Xuantong	Pu Yi	הנרי פו-יי	Henry PuYi	პუი	Puyi	Pu Ji	फू-यी	Puyi	Pu Yi	愛新覚羅溥儀	Puyi	Puyi	Puyi	Pu Yi	Пу И	Puyi	Pu Yi	Puyi	Puyi	จักรพรรดิผู่อี๋	Puyi	Пу І	Phổ Nghi	溥儀	溥仪",
				alternativeNames);
	}

	@Test
	public void readSecondArticles() throws IOException {
		reader.readArticle();
		Article a = reader.readArticle();

		Assert.assertEquals("Edwin_Edwards", a.getName());

		String[] names = new String[] { "Edwin Edwards" };

		for (int i = 0; i < names.length; i++) {
			Assert.assertEquals(names[i], a.getNames().get(i).getName());
		}
		Assert.assertEquals(6, a.getNames().size());
	}
}
