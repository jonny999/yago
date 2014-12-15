package FIIT.VI.YAGO.parser;

import java.io.IOException;

import FIIT.VI.YAGO.domain.GeoData;
import FIIT.VI.YAGO.reader.GeoDataReader;
import FIIT.VI.YAGO.util.FileUtil;

/**
 * GEO data parser 
 * @author mm
 *
 */
public class GeoDataParser {

	/**
	 * Create list of json documents based on input data
	 * @param path path to file for parse
	 * @param reader georeader with actual settings
	 * @throws IOException
	 */
	public static void createGeoData(String path, GeoDataReader reader)
			throws IOException {
		GeoData a;

		while ((a = reader.readGeoData()) != null) {
			FileUtil.createOrUpdateAndSave(a.toJson(), a.parseFilesName(), path);
		}
	}

}
