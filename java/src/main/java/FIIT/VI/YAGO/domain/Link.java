package FIIT.VI.YAGO.domain;

import com.google.gson.Gson;

public class Link {
	private final Gson gson = new Gson();

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

	public String toJson() {
		return gson.toJson(this);
	}
}
