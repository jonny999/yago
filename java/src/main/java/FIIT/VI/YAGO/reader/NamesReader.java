package FIIT.VI.YAGO.reader;

import java.io.IOException;
import java.util.Objects;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.domain.Names;
import FIIT.VI.YAGO.domain.RDFTriplet;

public class NamesReader extends Reader {

	public NamesReader() throws IOException {
		initiliaze();
	}

	public NamesReader(String pathTo) throws IOException {
		path = pathTo;
		initiliaze();
	}

	public Article readArticle() throws IOException {

		Article article = new Article();
		boolean found = false;
		String searchString = null;

		if (line != null && isWikiLink()) {
			RDFTriplet triplet = toRDF();
			article.parseName(triplet.getSubject());
			searchString = triplet.getSubject();
			found = true;
			article.getNames().add(new Names(triplet.getObject()));
		}

		while ((line = this.readline()) != null) {

			if (!found && isWikiLink()) {

				RDFTriplet triplet = toRDF();
				article.parseName(triplet.getSubject());
				searchString = triplet.getSubject();
				found = true;
				article.getNames().add(new Names(triplet.getObject()));

			} else if (found) {

				if (isWikiLink()) {
				
					RDFTriplet triplet = toRDF();
					if (Objects.equals(searchString, triplet.getSubject())) {
						article.getNames().add(new Names(triplet.getObject()));
					} else {
						break;
					}
				}
			}
		}

		if (found) {
			return article;
		}

		return null;
	}

}
