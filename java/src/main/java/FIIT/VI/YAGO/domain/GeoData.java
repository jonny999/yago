package FIIT.VI.YAGO.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Domain entity represent geo data
 * @author mm
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class GeoData {

	private static final ObjectMapper mapper = new ObjectMapper();

	/** YAGO identifier name for geo, is unique*/
	private String name;
	
	/** Latitude of position*/
	private String latitude;
	
	/** Longitude of position*/
	private String longitude;
	
	/** Labels, tags of geo data*/
	private List<String> labels;

	public String getName() {
		return name;
	}
	
	/**
	 * Parse unique name for json file
	 * @return classic YAGO name without special codes, like /_
	 */
	public String parseFilesName(){
		return name.replaceAll("_", "").replaceAll("/", "");
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLatitude() {
		return latitude;
	}

	public void parseLatitude(String latitude) {
		this.setLatitude(latitude.replaceAll("\"", ""));
	}

	public void parseLongitude(String longitude) {
		this.setLongitude(longitude.replaceAll("\"", ""));
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public List<String> getLabels() {
		if (labels == null) {
			labels = new ArrayList<String>();
		}

		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	/**
	 * Map article entity to JSON format
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String toJson() throws JsonGenerationException,
			JsonMappingException, IOException {

		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
	}

}
