package FIIT.VI.YAGO.parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import FIIT.VI.YAGO.domain.RDFTriplet;

public class YagoParserTest{

	private String rdfTest;
	private String rdfTest2;
	private String rdfTest3;
	
	
	@Before
	public void setup(){
		rdfTest = "<Susilo_Bambang_Yudhoyono>	<hasWikipediaUrl>	<http://en.wikipedia.org/wiki/Susilo_Bambang_Yudhoyono> .";
		rdfTest2 = "<Kid_Rock>	<hasWikipediaUrl>	<http://en.wikipedia.org/wiki/Kid_Rock> .";
		rdfTest3 = "aaaaaaaaaaalslalsalslaslals";
	}
	
	@Test
	public void parseTextOK(){
		RDFTriplet triptet = YagoParser.processRdfTriplet(rdfTest2);

		assertNotNull(triptet);
		assertEquals("Kid_Rock", triptet.getSubject());
		assertEquals("hasWikipediaUrl", triptet.getRelation());
		assertEquals("http://en.wikipedia.org/wiki/Kid_Rock", triptet.getObject());
	}
	
	@Test
	public void parseText(){
		RDFTriplet triptet =YagoParser.processRdfTriplet(rdfTest);
		assertNotNull(triptet);
		assertEquals("Susilo_Bambang_Yudhoyono", triptet.getSubject());
		assertEquals("hasWikipediaUrl", triptet.getRelation());
		assertEquals("http://en.wikipedia.org/wiki/Susilo_Bambang_Yudhoyono", triptet.getObject());

	}
	
	@Test
	public void parseTextFAIL(){
		RDFTriplet triptet =YagoParser.processRdfTriplet(rdfTest3);
		assertNull(triptet);
	}
}
