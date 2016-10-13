package automation;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import net.webservicex.GlobalWeather;
import net.webservicex.GlobalWeatherSoap;
import util.Validators;

public class WeatherTest {

	private GlobalWeatherSoap responseSoap12;
	private String countries;

	@BeforeTest
	public void setup() {
		GlobalWeather globalWeather = new GlobalWeather();
		responseSoap12 = globalWeather.getGlobalWeatherSoap12();
	}

	@DataProvider(name = "validCountries")
	public static Object[][] validInput() {
		return new Object[][] { { "Romania", "Iasi" }, { "Germany", "Munich" }, { "Sweden", "Stockholm" } };
	}

	@DataProvider(name = "nonCountries")
	public static Object[][] invalidInput() {
		return new Object[][] { { "xxx" }, { "Landistan" }, { "Sweeden" }, { "Rümänien" } };
	}

	@Test(dataProvider = "validCountries")
	public void CountriesResponsePositiveTest(String country, String city) {
		countries = responseSoap12.getCitiesByCountry(country);
		Assert.assertTrue(countries.contains(city));
	}

	@Test(dataProvider = "nonCountries")
	public void CountriesResponseNegativeTest(String country) {
		countries = responseSoap12.getCitiesByCountry(country);
		Assert.assertEquals(countries, "<NewDataSet />");
	}

	@Test(dataProvider = "validCountries")
	public void XMLValidationPositiveTest(String country,  String city) {
		countries = responseSoap12.getCitiesByCountry(country);
		Assert.assertTrue(Validators.isXMLValid(countries));
	}

	@Test(dataProvider = "nonCountries")
	public void XMLValidationNegativeTest(String country) {
		countries = responseSoap12.getCitiesByCountry(country);
		Assert.assertTrue(Validators.isXMLValid(countries));
	}

}
