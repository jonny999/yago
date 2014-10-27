package FIIT.VI.YAGO.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Article {

	private static final ObjectMapper mapper = new ObjectMapper();

	private String name;
	private String size;
	private String ulrWikipedia;
	private List<String> linksTo;
	private List<Names> names;

	public String getName() {
		return name;
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

	public String toJson() throws JsonGenerationException,
			JsonMappingException, IOException {
		return mapper.writeValueAsString(this);
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

}
