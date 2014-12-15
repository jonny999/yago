package FIIT.VI.YAGO.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.SimpleFSDirectory;

import FIIT.VI.YAGO.index.WikiIndex;

/**
 * Main model 
 * @author mm
 *
 */
public class MainModel {

	private WikiIndex indexFolder;

	private static SimpleFSDirectory indexDirection;
	private final static String INDEX = "/media/mm/Data/index";

	public MainModel() throws IOException {
		indexDirection = new SimpleFSDirectory(new File(INDEX));
		indexFolder = new WikiIndex(indexDirection);
	}

	/**
	 * Service of search index
	 * @param searchString search String
	 * @return list of lucene documents
	 */
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
