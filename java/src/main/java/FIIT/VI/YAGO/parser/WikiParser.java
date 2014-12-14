package FIIT.VI.YAGO.parser;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.domain.RDFTriplet;
import FIIT.VI.YAGO.reader.CategoryReader;
import FIIT.VI.YAGO.reader.DbInstanceReader;
import FIIT.VI.YAGO.reader.NamesReader;
import FIIT.VI.YAGO.reader.WikiReader;
import FIIT.VI.YAGO.util.FileUtil;

/**
 * Wikipedia parser of documents
 * @author mm
 *
 */
public class WikiParser {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * Create based structure of wikipedia article
	 * @param path path to file for creation of article
	 * @param reader reader of wikipedia file
	 * @throws IOException
	 */
	public static void createBaseWikiArticles(String path, WikiReader reader)
			throws IOException {
		Article a;

		while ((a = reader.readArticle()) != null) {
			createOrUpdateAndSave(a.toJson(), a.parseFilesName(), path);
		}
	}

	/**
	 * Update created wikipedia articles of tags, if article not exist, nothing is update
	 * @param path path to file for update of article
	 * @param reader reader for tags of wikipedia
	 * @throws IOException
	 */
	public static void updateBaseWikiArticlesCategories(String path,
			CategoryReader reader) throws IOException {
		Article a;
		Article update;

		while ((a = reader.readArticle()) != null) {
			update = loadArticle(a.getName(), path);
			if (update != null) {
				update.setCategories(a.getCategories());
				createOrUpdateAndSave(update.toJson(), a.parseFilesName(), path);
			}
		}
	}

	/**
	 * Update created wikipedia articles of alternatives name, if article not exist, nothing is update 
	 * @param path path to file for update article
	 * @param reader reader for alternatives names
	 * @throws IOException
	 */
	public static void updateBaseWikiArticlesAlternativeNames(String path,
			NamesReader reader) throws IOException {
		Article a;
		Article update;

		while ((a = reader.readArticle()) != null) {
			update = loadArticle(a.getName(), path);
			if (update != null) {

				update.setNames(a.getNames());
				createOrUpdateAndSave(update.toJson(), a.parseFilesName(), path);
			}
		}
	}

	/**
	 * Update created wikipedia articles of dbpedia url, if article not exist, nothing is update
	 * @param path path to file for update article
	 * @param reader reader for dppedia ulrs
	 * @throws IOException
	 */
	public static void updateBaseWikiArticlesAlternativeLinks(String path,
			DbInstanceReader reader) throws IOException {
		RDFTriplet triplet;
		Article update;

		while ((triplet = reader.readArticle()) != null) {
			update = loadArticle(triplet.getSubject(), path);
			if (update != null) {

				update.setUrlAlternative(triplet.getObject());
				createOrUpdateAndSave(update.toJson(), update.parseFilesName(),
						path);
			}
		}
	}

	/**
	 * Load article from folder and based unique name of entity
	 * @param name unique name of entity
	 * @param path folder path where is entities save 
	 * @return wikipedia article
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private static Article loadArticle(String name, String path)
			throws JsonParseException, JsonMappingException, IOException {
		return loadArticle(path + name + ".json");
	}

	/**
	 * Load article based on path to file
	 * @param path path to file
	 * @return wikipedia article
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Article loadArticle(String path) throws JsonParseException,
			JsonMappingException, IOException {

		File file = new File(path);

		if (!file.exists()) {
			return null;
		}

		return MAPPER.readValue(file, Article.class);
	}

	/**
	 * Create or override file based on data, name and path
	 * @param json data for save
	 * @param name unique name of wikipedia article 
	 * @param path path to folder
	 * @throws IOException
	 */
	private static void createOrUpdateAndSave(String json, String name,
			String path) throws IOException {
		FileUtil.createOrUpdateAndSave(json, name, path);

	}
}
