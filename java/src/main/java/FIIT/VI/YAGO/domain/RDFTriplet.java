package FIIT.VI.YAGO.domain;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

public class RDFTriplet {

	private String subject;
	private String relation;
	private String object;

	public RDFTriplet() {
	}

	public RDFTriplet(String subject, String relation, String object) {
		this.subject = subject;
		this.object = object;
		this.relation = relation;
	}
	
	
	public Document document() {
		Document doc = new Document();

		doc.add(new TextField("subject", subject, Field.Store.YES));
		doc.add(new TextField("object", object, Field.Store.YES));

		return doc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
}
