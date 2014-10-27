package FIIT.VI.YAGO.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.reader.WikiReader;

public class WikiParser {

	public static void createBaseWikiArticles(String path, WikiReader reader)
			throws IOException {
		Article a;
		
		while ((a = reader.readArticle()) != null) {
			createAndSave(a.toJson(), a.getName(), path);
		}
	}
	
	
	private static void createAndSave(String json, String name, String path)
			throws IOException {
		File file = new File(path + name + ".json");

		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file.getAbsolutePath());
		BufferedWriter bw = new BufferedWriter(writer);

		bw.write(json);
		bw.close();
	}
}
