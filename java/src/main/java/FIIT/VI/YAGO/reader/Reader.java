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

import FIIT.VI.YAGO.configuration.Configuration;

public class Reader {

	protected BufferedReader reader;
	protected String path = Configuration.getDefaultData();
	private final static Charset ENCODING = StandardCharsets.UTF_8;

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
	
	
	public void reload() throws IOException{
		this.close();
		this.initiliaze();
	}
	
	public long linesCount() throws IOException{
		return linesCount(path);
	}

	public long linesCount(String aFile) throws IOException {

		LineNumberReader lineReader = new LineNumberReader(new FileReader(aFile));
		lineReader.skip(Long.MAX_VALUE);
		long size =lineReader.getLineNumber(); 
		lineReader.close();
		return size;
	}	
}
