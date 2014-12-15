package FIIT.VI.YAGO.reader;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import FIIT.VI.YAGO.domain.GeoData;
import FIIT.VI.YAGO.domain.RDFTriplet;

public class GeoDataReaderTest {

	private final static String DATA = "..//data//sample_yagoGeonamesData.txt";
	private GeoDataReader reader;
	private GeoData first;
	private GeoData second;

	@Before
	public void setup() throws IOException {
		reader = new GeoDataReader(DATA);
		first = reader.readGeoData();
		second = reader.readGeoData();
	}

	@Test
	public void testFirst() {
		assertEquals("London_Reefs", first.getName());
		assertEquals("8.85", first.getLatitude());
		assertEquals("112.53333", first.getLongitude());
		assertEquals(6, first.getLabels().size());
	}

	@Test
	public void testSecond() {
		assertEquals("Peru-Chile_Trench", second.getName());
		assertEquals("-26.00005", second.getLatitude());
		assertEquals("-73.99997", second.getLongitude());
		assertEquals(12, second.getLabels().size());
	}

	@After
	public void setupEnd() throws IOException {
		reader.close();
	}

}
