package automation.integration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.soap.SOAPException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import util.CSVUtils;
import util.LocalProperties;
import util.SOAPClient;
import util.XMLExpand;

public class GetWeatherSendRequest {

	@DataProvider(name = "validCountriesFromCSV")
	public static Object[][] validInputFromFile() {
		String csvFilePath = "./src/test/resources/soap/globalweather/countries.csv";
		return CSVUtils.getData(csvFilePath);
	}

	private SOAPClient SOAPClient;

	@Test(dataProvider = "validCountriesFromCSV")
	public void GetWeather(String country, String city) throws SOAPException, IOException {

		HashMap<String, String> map = new HashMap<>();
		File xmlFileTemplate;
		String response;
		
		map.put("city_string", city);
		map.put("country_string", country);
		xmlFileTemplate = new File("./src/main/resources/soap12/GetWeather.xml");

		SOAPClient = new SOAPClient(LocalProperties.getGlobalWeatherEndpoint());
		
		response = SOAPClient.execute(XMLExpand.perform(xmlFileTemplate, map));
		Assert.assertNotNull(response);

	}

	@Test(dataProvider = "validCountriesFromCSV")
	public void GetCitiesByCountry(String country, String city) throws SOAPException, IOException {

		HashMap<String, String> map = new HashMap<>();
		File xmlFileTemplate;
		String response;

		map.put("country_string", country);
		xmlFileTemplate = new File("./src/main/resources/soap12/GetCitiesByCountry.xml");

		SOAPClient = new SOAPClient(LocalProperties.getGlobalWeatherEndpoint());
		
		response = SOAPClient.execute(XMLExpand.perform(xmlFileTemplate, map));
		Assert.assertNotNull(response);

	}

}
