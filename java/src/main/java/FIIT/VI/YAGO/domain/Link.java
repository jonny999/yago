package FIIT.VI.YAGO.domain;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class Link {

	private static final ObjectMapper mapper = new ObjectMapper();
	
	private String relation;
	private String object;

	public Link() {
	}

	public Link(String relation, String object) {
		this.relation = relation;
		this.object = object;

	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String toJson() throws JsonGenerationException, JsonMappingException, IOException {
		return mapper.writeValueAsString(this);
	}
}
