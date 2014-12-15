package FIIT.VI.YAGO.reader;

import java.io.IOException;
import java.util.regex.Pattern;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.domain.RDFTriplet;
import FIIT.VI.YAGO.util.Replacer;

/**
 * Reader of wikipedia base data
 * @author mm
 *
 */
public class WikiReader extends Reader {

	private static final String REGEX_URL = "<(.*)>\t<(hasWikipediaUrl)>\t?<(.*)?>.";
	private static final String REGEX_ARTICLE = "<(.*)>\t<(hasWikipediaArticleLength)>\t(.*)";

	private static final Pattern PATTERN_WIKI = Pattern.compile(REGEX_ARTICLE);
	private static final Pattern PATTERN_URL = Pattern.compile(REGEX_URL);

	public WikiReader() throws IOException {
		initiliaze();
	}

	public WikiReader(String pathTo) throws IOException {
		path = pathTo;
		initiliaze();
	}

	/**
	 * Read article from defined file and based on REGEX rules
	 * @return
	 * @throws IOException
	 */
	public Article readArticle() throws IOException {

		Article article = new Article();
		boolean found = false;

		if (line != null && isWikiArticle()) {
			RDFTriplet triplet = toRDF();
			article.parseName(triplet.getSubject());
			article.setSize(triplet.getObject());
			found = true;
		}

		while ((line = this.readline()) != null) {

			if (!found && isWikiArticle()) {
				RDFTriplet triplet = toRDF();
				article.parseName(triplet.getSubject());
				article.setSize(triplet.getObject());
				found = true;
			} else if (found) {

				if (isWikiArticle()) {
					break;
				} else if (isWikiURL()) {
					RDFTriplet triplet = toRDF();
					article.setUlrWikipedia(triplet.getObject());
				} else if (isWikiLink()) {

					RDFTriplet triplet = toRDF();
					article.getLinksTo().add(
							Replacer.replaceCharacter(triplet.getObject()));
				}
			}
		}

		if (found) {
			return article;
		}

		return null;
	}

	public boolean isWikiArticle() {
		return PATTERN_WIKI.matcher(line).find();
	}

	public boolean isWikiArticle(String l) {
		return PATTERN_WIKI.matcher(l).find();
	}

	public boolean isWikiURL() {
		return PATTERN_URL.matcher(line).find();
	}

	public boolean isWikiURL(String l) {
		return PATTERN_URL.matcher(l).find();
	}
}
