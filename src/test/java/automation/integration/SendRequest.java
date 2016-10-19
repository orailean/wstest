package automation.integration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import util.CSVUtils;
import util.SOAPClient;
import util.XMLExpand;

public class SendRequest {

	@DataProvider(name = "validCountriesFromCSV")
	public static Object[][] validInputFromFile() {
		String csvFilePath = "./src/test/resources/soap/globalweather/countries.csv";
		return CSVUtils.getData(csvFilePath);
	}

	@Test(dataProvider = "validCountriesFromCSV")
	public void f1(String country, String city) throws SOAPException, IOException {

		HashMap<String, String> map = new HashMap<>();
		File xmlFileTemplate;
		SOAPMessage response;

		map.put("city_string", city);
		map.put("country_string", country);

		xmlFileTemplate = new File("./src/main/resources/soap12/GetWeather.xml");
		String xmlExpanded = XMLExpand.perform(xmlFileTemplate, map);

		response = SOAPClient.SOAPSendRequest("http://www.webservicex.net/globalweather.asmx", xmlExpanded);

		Assert.assertNotNull(response);

	}

	@Test(dataProvider = "validCountriesFromCSV")
	public void f2(String country, String city) throws SOAPException, IOException {

		HashMap<String, String> map = new HashMap<>();
		File xmlFileTemplate;
		SOAPMessage response;

		map.put("country_string", country);

		xmlFileTemplate = new File("./src/main/resources/soap12/GetCitiesByCountry.xml");
		String xmlExpanded = XMLExpand.perform(xmlFileTemplate, map);
		response = SOAPClient.SOAPSendRequest("http://www.webservicex.net/globalweather.asmx", xmlExpanded);

		Assert.assertNotNull(response);

	}

}
