package FIIT.VI.YAGO.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import FIIT.VI.YAGO.configuration.Configuration;
import FIIT.VI.YAGO.domain.RDFTriplet;

/**
 * Based reader
 * @author mm
 *
 */
public class Reader {

	/**Based of manipulation with files is buffer reader*/
	protected BufferedReader reader;
	
	/**Actual line for processing*/
	protected String line;

	protected String path = Configuration.getDefaultData();
	private final static Charset ENCODING = StandardCharsets.UTF_8;

	private static final String REGEX_RDF = "<(.*)>\t(.*)\t<?(.*)\\b>?";
	protected static final Pattern PATTERN_RDF = Pattern.compile(REGEX_RDF);
	
	/**
	 * Initialize of reader based variables
	 * @throws IOException
	 */
	protected void initiliaze() throws IOException {

		Path pathToFile = Paths.get(path);
		reader = Files.newBufferedReader(pathToFile, ENCODING);
	}
	
	public String readline() throws IOException{
		return reader.readLine();
	}
	
	public void close() throws IOException{
		reader.close();
	}
	
	/**
	 * Close old stream and reload to start
	 * @throws IOException
	 */
	public void reload() throws IOException{
		this.close();
		this.initiliaze();
	}
	
	/**
	 * Count of lines in file 
	 * @return count of lines
	 * @throws IOException
	 */
	public long linesCount() throws IOException{
		LineNumberReader lineReader = new LineNumberReader(new FileReader(path));
		lineReader.skip(Long.MAX_VALUE);
		long size =lineReader.getLineNumber(); 
		lineReader.close();
		return size;
	}	
	
	/**
	 * Default converter based on default REGEX
	 * @return rdf entity of actual processing string
	 */
	public RDFTriplet toRDF() {
		Matcher m = PATTERN_RDF.matcher(line);
		if (m.find()) {
			return new RDFTriplet(m.group(1), m.group(2), m.group(3));
		}

		return null;
	}
	
	/**
	 * Checker if actual processing line match default REGEX pattern
	 * @return
	 */
	public boolean isWikiLink() {
		return PATTERN_RDF.matcher(line).find();
	}
}
