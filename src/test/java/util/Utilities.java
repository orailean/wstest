package util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Utilities {

	public static String fileToString(File file) {

		String fileContent = null;
		try {
			fileContent = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileContent;
	}

}
