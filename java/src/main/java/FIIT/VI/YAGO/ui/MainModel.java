package FIIT.VI.YAGO.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.SimpleFSDirectory;

import FIIT.VI.YAGO.parser.WikiIndex;

public class MainModel {

	private WikiIndex indexFolder;

	private static SimpleFSDirectory indexDirection;
	private final static String INDEX = "/media/mm/Data/index";

	public MainModel() throws IOException {
		indexDirection = new SimpleFSDirectory(new File(INDEX));
		indexFolder = new WikiIndex(indexDirection);
	}

	public List<Document> searchIndex(String searchString) {
		try {
			return indexFolder.searchIndex(searchString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Document>();
	}

}
