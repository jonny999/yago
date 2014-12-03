package FIIT.VI.YAGO.reader;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import FIIT.VI.YAGO.domain.GeoData;
import FIIT.VI.YAGO.domain.RDFTriplet;

public class GeoDataReader extends Reader {

	private static final String LATITUDE_RDF = "<(.*)> +<(hasLatitude)> +(.*)\\^\\^<degrees>";
	private static final String LONGITUDE_RDF = "<(.*)> +<(hasLongitude)> +(.*)\\^\\^<degrees>";
	private static final String LABEL_RDF = "<(.*)> +(rdfs:label) +(.*)\\b";

	private static final Pattern LATITUDE_RDF_PATTERN = Pattern
			.compile(LATITUDE_RDF);
	private static final Pattern LONGITUDE_RDF_PATTERN = Pattern
			.compile(LONGITUDE_RDF);
	private static final Pattern LABEL_RDF_PATTERN = Pattern.compile(LABEL_RDF);

	public GeoDataReader() throws IOException {
		initiliaze();
	}

	public GeoDataReader(String pathTo) throws IOException {
		path = pathTo;
		initiliaze();
	}

	public GeoData readGeoData() throws IOException {

		GeoData geoData = new GeoData();
		boolean found = false;

		if (line != null && isLatitude()) {
			RDFTriplet triplet = toRDF(LATITUDE_RDF_PATTERN);
			geoData.setName(triplet.getSubject());
			geoData.parseLatitude(triplet.getObject());
			found = true;
		}

		while ((line = this.readline()) != null) {

			if (!found && isLatitude()) {
				RDFTriplet triplet = toRDF(LATITUDE_RDF_PATTERN);
				geoData.setName(triplet.getSubject());
				geoData.parseLatitude(triplet.getObject());
				found = true;
			} else if (found) {

				if (isLatitude()) {
					break;
				} else if (isLongitude()) {
					RDFTriplet triplet = toRDF(LONGITUDE_RDF_PATTERN);
					geoData.parseLongitude(triplet.getObject());
				} else if (isLabel()) {

					RDFTriplet triplet = toRDF(LABEL_RDF_PATTERN);
					geoData.getLabels().add(triplet.getObject());
				}
			}
		}

		if (found) {
			return geoData;
		}

		return null;
	}

	private boolean isLatitude() {
		return LATITUDE_RDF_PATTERN.matcher(line).find();
	}

	private boolean isLabel() {
		return LABEL_RDF_PATTERN.matcher(line).find();
	}

	private boolean isLongitude() {
		return LONGITUDE_RDF_PATTERN.matcher(line).find();
	}

	public RDFTriplet toRDF(Pattern patterrn) {
		Matcher m = patterrn.matcher(line);
		if (m.find()) {
			return new RDFTriplet(m.group(1), m.group(2), m.group(3));
		}

		return null;
	}
}
