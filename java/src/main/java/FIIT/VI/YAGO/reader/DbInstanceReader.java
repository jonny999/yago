package FIIT.VI.YAGO.reader;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import FIIT.VI.YAGO.domain.RDFTriplet;

/**
 * Reader of db url for wikipedia
 * @author mm
 *
 */
public class DbInstanceReader extends Reader {

	private static final String DB_INSTANCE_RDF = "<(.*)>\t(owl:sameAs)\t<(.*)>";
	private static final Pattern READER_PATTERN_RDF = Pattern
			.compile(DB_INSTANCE_RDF);

	public DbInstanceReader() throws IOException {
		initiliaze();
	}

	public DbInstanceReader(String pathTo) throws IOException {
		path = pathTo;
		initiliaze();
	}

	/**
	 * Read articles based on REGEX rules
	 * @return
	 * @throws IOException
	 */
	public RDFTriplet readArticle() throws IOException {

		RDFTriplet triplet = null;

		while ((line = this.readline()) != null) {

			if (isReader()) {
				triplet = toRDF();
				break;
			}

		}

		return triplet;
	}

	public boolean isReader() {
		return READER_PATTERN_RDF.matcher(line).find();
	}

	@Override
	public RDFTriplet toRDF() {
		Matcher m = READER_PATTERN_RDF.matcher(line);
		if (m.find()) {
			return new RDFTriplet(m.group(1), m.group(2), m.group(3));
		}

		return null;
	}
}
