package automation;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import net.webservicex.GlobalWeather;
import net.webservicex.GlobalWeatherSoap;
import util.Validators;

public class WeatherTest {

	private GlobalWeatherSoap responseSoap12;
	private String countryName;
	private String countries;

	@BeforeTest
	public void setup() {
		GlobalWeather globalWeather = new GlobalWeather();
		responseSoap12 = globalWeather.getGlobalWeatherSoap12();
	}

	@Test
	public void CountriesResponsePositiveTest() {
		countryName = "Romania";
		countries = responseSoap12.getCitiesByCountry(countryName);
		Assert.assertTrue(countries.contains("Iasi"));
	}
	
	@Test
	public void CountriesResponseNegativeTest() {
		countryName = "xxx";
		countries = responseSoap12.getCitiesByCountry(countryName);
		Assert.assertEquals(countries, "<NewDataSet />");
	}
	
	@Test
	public void XMLValidationPositiveTest() {
		countryName = "Germany";
		countries = responseSoap12.getCitiesByCountry(countryName);
		Assert.assertTrue(Validators.isXMLValid(countries));
	}
	
	@Test
	public void XMLValidationNegativeTest() {
		countryName = "xxx";
		countries = responseSoap12.getCitiesByCountry(countryName);
		System.out.println(countries);
		Assert.assertTrue(Validators.isXMLValid(countries));
	}

}
