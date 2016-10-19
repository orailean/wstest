package util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class XMLExpand {

	public static String perform(File xmlFileTemplate, HashMap<String, String> map) {

		Set<Entry<String, String>> set = map.entrySet();
		Iterator<Entry<String, String>> iterator = set.iterator();

		String content = null;
		try {
			content = FileUtils.readFileToString(xmlFileTemplate);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (iterator.hasNext()) {

			Entry<String, String> entry = iterator.next();
			content = content.replaceAll("%" + entry.getKey() + "%", entry.getValue());

		}

		return content;

	}

}
