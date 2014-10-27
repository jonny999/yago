package FIIT.VI.YAGO.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.index.IndexWriter;

import FIIT.VI.YAGO.domain.RDFTriplet;

public class YagoParser {

	private static final String WIKI_URL = "<(.*)>\t<(hasWikipediaUrl)>\t?<(.*)?>.";
	private static final Pattern PATTERN = Pattern.compile(WIKI_URL);
	
	private YagoParser() {
	}
	
	public static void processYagoData(String fileName,Charset encoding,IndexWriter writter) throws IOException{
		
		Path path = Paths.get(fileName);
	    BufferedReader reader = Files.newBufferedReader(path, encoding);
	      String line = null;
	      while ((line = reader.readLine()) != null) {

	    	  RDFTriplet triplet = processRdfTriplet(line);
	    	  if(triplet!=null){
	    		  
	    		  writter.addDocument(triplet.document());
	    	  }
	      }      
		writter.close();
		reader.close();
	}
	
	
	public static RDFTriplet processRdfTriplet(String line) {

		Matcher m = PATTERN.matcher(line);
		if (m.find()) {
			return new RDFTriplet(m.group(1), m.group(2), m.group(3));
		}

		return null;
	}
}
