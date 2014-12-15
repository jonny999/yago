package FIIT.VI.YAGO.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.parser.WikiParser;

/**
 * Util class for work with files
 * @author mm
 *
 */
public final class FileUtil {

	private FileUtil() {
	}

	/**
	 * Connect files .json to one from folder
	 * @param folder folder where search 
	 * @param toFile results file
	 * @throws IOException
	 */
	public static void createOneFile(final File folder, final String toFile)
			throws IOException {

		File file = new File(toFile);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				continue;
			} else {
				if (fileEntry.isFile()) {
					String pathToFile = fileEntry.getName();
					if ((pathToFile.substring(pathToFile.lastIndexOf('.') + 1,
							pathToFile.length()).toLowerCase()).equals("json")) {

						Article a = WikiParser.loadArticle(fileEntry
								.getAbsolutePath());

						if (a != null) {
							bw.append(a.toJson()+"\n");
						}
					}
				}
			}
		}

		bw.close();

	}

	/**
	 * Save or replace file with json
	 * @param json data for input
	 * @param parseFilesName name of output file 
	 * @param path path where to save
	 * @throws IOException
	 */
	public static void createOrUpdateAndSave(String json,
			String parseFilesName, String path) throws IOException {
		File file = new File(path + parseFilesName + ".json");

		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(file.getAbsolutePath());
		BufferedWriter bw = new BufferedWriter(writer);

		bw.write(json);
		
		bw.close();
		
	}

}
