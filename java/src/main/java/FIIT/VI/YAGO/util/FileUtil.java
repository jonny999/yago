package FIIT.VI.YAGO.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import FIIT.VI.YAGO.domain.Article;
import FIIT.VI.YAGO.parser.WikiParser;

public final class FileUtil {

	private FileUtil() {
	}

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

}
