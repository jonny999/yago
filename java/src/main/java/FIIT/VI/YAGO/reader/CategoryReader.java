package FIIT.VI.YAGO.reader;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.domain.RDFTriplet;

/**
 * Reader of wikipedia tags 
 * @author mm
 *
 */
public class CategoryReader extends Reader {

	private static final String CATEGORY_RDF = "<(.*)>\t(.*)\t<wikicategory_(.*)\\b>";
	protected static final Pattern CATEGORY_PATTERN_RDF = Pattern
			.compile(CATEGORY_RDF);

	public CategoryReader() throws IOException {
		initiliaze();
	}

	public CategoryReader(String pathTo) throws IOException {
		path = pathTo;
		initiliaze();
	}

	/**
	 * Read article based on REGEX rules
	 * @return
	 * @throws IOException
	 */
	public Article readArticle() throws IOException {

		Article article = new Article();
		boolean found = false;
		String searchString = null;

		if (line != null && isCategory()) {
			RDFTriplet triplet = toRDF();
			article.parseName(triplet.getSubject());
			searchString = triplet.getSubject();
			found = true;
			article.processCategory((triplet.getObject()));
		}

		while ((line = this.readline()) != null) {

			if (!found && isCategory()) {

				RDFTriplet triplet = toRDF();
				article.parseName(triplet.getSubject());
				searchString = triplet.getSubject();
				found = true;
				article.processCategory((triplet.getObject()));

			} else if (found) {

				if (isCategory()) {

					RDFTriplet triplet = toRDF();
					if (Objects.equals(searchString, triplet.getSubject())) {
						article.processCategory((triplet.getObject()));
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

	public boolean isCategory() {
		return CATEGORY_PATTERN_RDF.matcher(line).find();
	}

	@Override
	public RDFTriplet toRDF() {
		Matcher m = CATEGORY_PATTERN_RDF.matcher(line);
		if (m.find()) {
			return new RDFTriplet(m.group(1), m.group(2), m.group(3));
		}

		return null;
	}
}
