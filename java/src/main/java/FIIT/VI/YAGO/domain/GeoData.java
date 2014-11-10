package FIIT.VI.YAGO.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class GeoData {

	private static final ObjectMapper mapper = new ObjectMapper();

	private String name;
	private String latitude;
	private String longitude;
	private List<String> labels;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLatitude() {
		return latitude;
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

	public String toJson() throws JsonGenerationException,
			JsonMappingException, IOException {

		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
	}

}
