package FIIT.VI.YAGO.parser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.reader.YagoReader;

public class YagoReaderTest {

	private final static String PRIMARY_DATA_NAME = "src//resources//test//sample_yago_wiki.txt";
	private YagoReader reader;

	@Before
	public void setup() throws IOException {
		reader = new YagoReader(PRIMARY_DATA_NAME);
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
	public void readArticles() throws IOException {
		reader.reload();
		 FileWriter writer = new FileWriter("src//resources//test//output.json");  
		 List<Article> jsons = new ArrayList<Article>();
		Article a;
		while ((a = reader.readArticle()) != null) {
			jsons.add(a);
		}
	      ObjectMapper mapper = new ObjectMapper();
	      
	      String s = mapper.writeValueAsString(jsons);
	      

		writer.write(mapper.writeValueAsString(jsons));  
		   writer.close();  


	}

}
