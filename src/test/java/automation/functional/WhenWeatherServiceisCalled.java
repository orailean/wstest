package automation.functional;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import net.webservicex.GlobalWeather;
import net.webservicex.GlobalWeatherSoap;
import util.CSVUtils;
import util.Validators;

public class WhenWeatherServiceisCalled {

	private GlobalWeatherSoap responseSoap12;
	private String countries;

	final Logger LOGGER = Logger.getLogger(WhenWeatherServiceisCalled.class);

	@BeforeTest
	public void setup() {
		GlobalWeather globalWeather = new GlobalWeather();
		responseSoap12 = globalWeather.getGlobalWeatherSoap12();
	}
	
	@DataProvider(name = "invalidCountriesFromCSV")
	public static Object[][] invalidInputFromFile() {
		String csvFilePath = "./src/test/resources/soap/globalweather/countries_invalid.csv";
		return CSVUtils.getData(csvFilePath);
	}

	@DataProvider(name = "validCountriesFromCSV")
	public static Object[][] validInputFromFile() {
		String csvFilePath = "./src/test/resources/soap/globalweather/countries.csv";
		return CSVUtils.getData(csvFilePath);
	}

	@Test(dataProvider = "validCountriesFromCSV")
	
	public void should_return_existing_city_if_country_is_valid(String country, String city) {
		LOGGER.info("Parameters: [" + country + "," + city + "]");
		countries = responseSoap12.getCitiesByCountry(country);
		Assert.assertTrue(countries.contains(city));
	}

	@Test(dataProvider = "invalidCountriesFromCSV")
	public void should_return_empty_set_if_country_not_valid(String country, String city) {
		LOGGER.info("Parameters: [" + country + "," + city + "]");
		countries = responseSoap12.getCitiesByCountry(country);
		Assert.assertEquals(countries, "<NewDataSet />");
	}

	@Test(dataProvider = "validCountriesFromCSV")
	public void should_return_valid_xml_if_country_is_valid(String country, String city) {
		LOGGER.info("Parameters: [" + country + "," + city + "]");
		countries = responseSoap12.getCitiesByCountry(country);
		Assert.assertTrue(Validators.isXMLValid(countries));
	}

	@Test(dataProvider = "invalidCountriesFromCSV")
	public void shoud_return_valid_xml_if_country_not_valid(String country, String city) {
		LOGGER.info("Parameters: [" + country + "," + city + "]");
		countries = responseSoap12.getCitiesByCountry(country);
		Assert.assertTrue(Validators.isXMLValid(countries));
	}

}
