package FIIT.VI.YAGO.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.reader.CategoryReader;
import FIIT.VI.YAGO.reader.NamesReader;
import FIIT.VI.YAGO.reader.WikiReader;

public class WikiParser {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static void createBaseWikiArticles(String path, WikiReader reader)
			throws IOException {
		Article a;

		while ((a = reader.readArticle()) != null) {
			createOrUpdateAndSave(a.toJson(), a.getName(), path);
		}
	}

	public static void updateBaseWikiArticlesCategories(String path,
			CategoryReader reader) throws IOException {
		Article a;
		Article update;

		while ((a = reader.readArticle()) != null) {
			update = loadArticle(a.getName(), path);
			update.setCategories(a.getCategories());
			createOrUpdateAndSave(update.toJson(), a.getName(), path);
		}
	}

	public static void updateBaseWikiArticlesAlternativeNames(String path,
			NamesReader reader) throws IOException {
		Article a;
		Article update;

		while ((a = reader.readArticle()) != null) {
			update = loadArticle(a.getName(), path);
			update.setNames(a.getNames());
			createOrUpdateAndSave(update.toJson(), a.getName(), path);
		}
	}

	
	private static Article loadArticle(String name, String path) throws JsonParseException, JsonMappingException, IOException{
		return loadArticle(path + name + ".json");
	}
	
	public static Article loadArticle(String path)
			throws JsonParseException, JsonMappingException, IOException {

		File file = new File(path);

		if (!file.exists()) {
			return null;
		}

		return MAPPER.readValue(file, Article.class);
	}

	private static void createOrUpdateAndSave(String json, String name,
			String path) throws IOException {
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
