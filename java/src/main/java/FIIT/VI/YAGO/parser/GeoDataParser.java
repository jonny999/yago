package FIIT.VI.YAGO.parser;

import java.io.IOException;

import FIIT.VI.YAGO.domain.GeoData;
import FIIT.VI.YAGO.reader.GeoDataReader;
import FIIT.VI.YAGO.util.FileUtil;

public class GeoDataParser {

	public static void createGeoData(String path, GeoDataReader reader)
			throws IOException {
		GeoData a;

		while ((a = reader.readGeoData()) != null) {
			FileUtil.createOrUpdateAndSave(a.toJson(), a.parseFilesName(), path);
		}
	}

}
