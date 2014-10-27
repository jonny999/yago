package FIIT.VI.YAGO.reader;

import java.io.IOException;
import java.util.Objects;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.domain.RDFTriplet;

public class CategoryReader extends Reader {

	public CategoryReader() throws IOException {
		initiliaze();
	}

	public CategoryReader(String pathTo) throws IOException {
		path = pathTo;
		initiliaze();
	}

	public Article readArticle() throws IOException {

		Article article = new Article();
		boolean found = false;
		String searchString = null;

		if (line != null && isWikiLink()) {
			RDFTriplet triplet = toRDF();
			article.setName(triplet.getSubject());
			searchString = triplet.getSubject();
			found = true;
			article.getCategories().add(triplet.getObject());
		}

		while ((line = this.readline()) != null) {

			if (!found && isWikiLink()) {

				RDFTriplet triplet = toRDF();
				article.setName(triplet.getSubject());
				searchString = triplet.getSubject();
				found = true;
				article.getCategories().add(triplet.getObject());

			} else if (found) {

				if (isWikiLink()) {

					RDFTriplet triplet = toRDF();
					if (Objects.equals(searchString, triplet.getSubject())) {
						article.getCategories().add(triplet.getObject());
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
