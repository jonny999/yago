package FIIT.VI.YAGO.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Domain entity represent wiki article
 * @author mm
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Article {

	private static final ObjectMapper mapper = new ObjectMapper();
	
	/** YAGO identifier name for article, is unique*/
	private String name;
	
	/** Information about size of article, count o chars*/
	private String size;
	
	/** Wikipedia name of article*/
	private String wikiName;
	
	/** Wikipedia URl*/
	private String ulrWikipedia;
	
	/** DPpedia URl*/
	private String urlAlternative;
	
	/** Wikipedia references to other articles*/
	private List<String> linksTo;
	
	/** Wikipedia alternative names*/
	private List<Names> names;
	
	/** Tags of wikipedia*/
	private List<String> categories;

	public String getName() {
		return name;
	}
	
	/**
	 * Parse unique name for json file
	 * @return classic YAGO name without special codes, like /_
	 */
	public String parseFilesName(){
		return name.replaceAll("_", "").replaceAll("/", "");
	}

	/**
	 * Set classical unique name and parse wikipedia name
	 * @param name unique YAGO name
	 */
	public void parseName(String name) {
		this.setName(name);
		this.setWikiName(name.replaceAll("_", " "));
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUlrWikipedia() {
		return ulrWikipedia;
	}

	public void setUlrWikipedia(String ulrWikipedia) {
		this.ulrWikipedia = ulrWikipedia;
	}

	public List<String> getLinksTo() {
		if (linksTo == null) {
			linksTo = new ArrayList<String>();
		}

		return linksTo;
	}

	public void setLinksTo(List<String> linksTo) {
		this.linksTo = linksTo;
	}

	/**
	 * Map article entity to JSON format
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String toJson() throws JsonGenerationException,
			JsonMappingException, IOException {

		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
	}

	/**
	 * Parse lucene document from variables 
	 * @return Lucene document 
	 */
	public Document document() {
		Document doc = new Document();

		doc.add(new TextField("name", name, Field.Store.YES));
		doc.add(new TextField("ulrWikipedia", ulrWikipedia, Field.Store.YES));

		for (String category : this.getCategories()) {
			doc.add(new TextField("category", category, Field.Store.YES));
		}

		for (String link : this.getLinksTo()) {
			doc.add(new TextField("link", link, Field.Store.YES));
		}

		for (Names name : this.getNames()) {
			doc.add(new TextField("name", name.getName(), Field.Store.YES));
		}

		return doc;
	}

	/**
	 * Parse alternative names of wikipedia article
	 * @return String list of alternatives names
	 */
	public String toAlternativesNames() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getName());

		for (Names n : this.getNames()) {
			builder.append("\t" + n.getName());
		}
		builder.append("\n");
		return builder.toString();
	}

	/**
	 * Select tags of article
	 * @return String list of tags
	 */
	public String toCategoriesNames() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getName());

		for (String n : this.getCategories()) {
			builder.append("\t" + n);
		}
		builder.append("\n");
		return builder.toString();
	}

	public List<Names> getNames() {
		if (names == null) {
			names = new ArrayList<Names>();
		}

		return names;
	}

	public void setNames(List<Names> names) {
		this.names = names;
	}

	public List<String> getCategories() {
		if (categories == null) {
			this.categories = new ArrayList<String>();
		}

		return categories;
	}

	public void processCategory(String category) {
		String parseCategory = category.replaceAll("_", " ");
		this.getCategories().add(parseCategory);
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public String getUrlAlternative() {
		return urlAlternative;
	}

	public void setUrlAlternative(String urlAlternative) {
		this.urlAlternative = urlAlternative;
	}

	public String getWikiName() {
		return wikiName;
	}

	public void setWikiName(String wikiName) {
		this.wikiName = wikiName;
	}

}
