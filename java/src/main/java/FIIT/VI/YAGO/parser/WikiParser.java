package FIIT.VI.YAGO.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.reader.WikiReader;

public class WikiParser {

	public static void createWikiArticles(String path, WikiReader reader)
			throws IOException {
		Article a;
		FileWriter writer;
		File file;
		BufferedWriter bw;

		while ((a = reader.readArticle()) != null) {

			file = new File(path + a.getName() + ".json");

			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new FileWriter(file.getAbsolutePath());
			bw = new BufferedWriter(writer);

			bw.write(a.toJson());
			bw.close();
		}
	}
}
