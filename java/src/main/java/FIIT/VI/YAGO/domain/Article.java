package FIIT.VI.YAGO.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;

public class Article {

	private final Gson gson = new Gson();

	private String name;
	private String size;
	private String ulrWikipedia;
	private List<Link> linksTo;

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

	public List<Link> getLinksTo() {
		if(linksTo==null){
			linksTo = new ArrayList<Link>();
		}
		
		return linksTo;
	}

	public void setLinksTo(List<Link> linksTo) {
		this.linksTo = linksTo;
	}


	public String toJson() throws JsonGenerationException, JsonMappingException, IOException {
	      ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(this);
		/*Object c= gson.toJsonTree(this);
		
		
		return gson.toJson(this);*/
	}

}
