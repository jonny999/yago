package FIIT.VI.YAGO.reader;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import FIIT.VI.YAGO.domain.RDFTriplet;

public class DbInstanceReaderTest {

	private final static String DATA = "..//data//sample_yagoDBpediaInstances.txt";
	private DbInstanceReader reader;
	private RDFTriplet first;
	private RDFTriplet second;

	@Before
	public void setup() throws IOException {
		reader = new DbInstanceReader(DATA);
		first = reader.readArticle();
		second = reader.readArticle();
	}

	@Test
	public void testFirst() {
		assertEquals("A", first.getSubject());
		assertEquals("http://dbpedia.org/resource/A", first.getObject());
	}

	@Test
	public void testSecond() {
		assertEquals("Alabama", second.getSubject());
		assertEquals("http://dbpedia.org/resource/Alabama", second.getObject());
	}

	@After
	public void setupEnd() throws IOException {
		reader.close();
	}

}
