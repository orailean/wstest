package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class CSVUtils {

	public static Object[][] getData(String csvFilePath) {

		List<String[]> allRows = Collections.emptyList();
		
		int i, j;
		
		Object[][] csvData = null;
		try {
			CSVReader reader = new CSVReader(new FileReader(csvFilePath));
			allRows = reader.readAll();
			csvData = new Object[allRows.size()][2];
			reader.close();
			i = 0;
			for (String[] row: allRows) {
				j = 0;
				for (String elem: row) {
					csvData[i][j++] = elem;
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return csvData;

	}
}
