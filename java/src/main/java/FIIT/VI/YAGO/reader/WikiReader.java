package FIIT.VI.YAGO.reader;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.domain.RDFTriplet;

public class WikiReader extends Reader {

	private static final String REGEX_URL = "<(.*)>\t<(hasWikipediaUrl)>\t?<(.*)?>.";
	private static final String REGEX_ARTICLE = "<(.*)>\t<(hasWikipediaArticleLength)>\t(.*)";

	private static final Pattern PATTERN_WIKI = Pattern.compile(REGEX_ARTICLE);
	private static final Pattern PATTERN_URL = Pattern.compile(REGEX_URL);

	private String previousLine;

	public WikiReader() throws IOException {
		initiliaze();
	}

	public WikiReader(String pathTo) throws IOException {
		path = pathTo;
		initiliaze();
	}

	public Article readArticle() throws IOException {

		Article article = new Article();
		boolean found = false;

		if(previousLine!=null && isWikiArticle(previousLine)){
			RDFTriplet triplet = toRDF(previousLine);
			article.setName(triplet.getSubject());
			article.setSize(triplet.getObject());
			found=true;
		}
		
		while ((line = this.readline()) != null) {

			if (!found && isWikiArticle()) {
				RDFTriplet triplet = toRDF();
				article.setName(triplet.getSubject());
				article.setSize(triplet.getObject());
				found=true;
			} else if (found) {

				if (isWikiArticle()) {
					previousLine = line;
					break;
				} else if (isWikiURL()) {
					RDFTriplet triplet = toRDF();
					article.setUlrWikipedia(triplet.getObject());
				} else if (isWikiLink()) {

					RDFTriplet triplet = toRDF();
					article.getLinksTo().add(triplet.getObject());
				}
			}
			previousLine=line;
		}

		if (found) {
			return article;
		}

		return null;
	}

	public RDFTriplet toRDF() {
		Matcher m = PATTERN_RDF.matcher(line);
		if (m.find()) {
			return new RDFTriplet(m.group(1), m.group(2), m.group(3));
		}

		return null;
	}

	public RDFTriplet toRDF(String l) {
		Matcher m = PATTERN_RDF.matcher(l);
		if (m.find()) {
			return new RDFTriplet(m.group(1), m.group(2), m.group(3));
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

	public boolean isWikiLink() {
		return PATTERN_RDF.matcher(line).find();
	}

	public boolean isWikiLink(String l) {
		return PATTERN_RDF.matcher(l).find();
	}
}
