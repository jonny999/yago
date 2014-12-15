package FIIT.VI.YAGO.util;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FileUtilTest {

	private String PATH = "src//resources//test//data//";
	private String FILE_PATH = "src//resources//test//output.txt";

	@Test
	public void createFile() throws IOException {
		File folder = new File(PATH);
		FileUtil.createOneFile(folder, FILE_PATH);
	}
}
