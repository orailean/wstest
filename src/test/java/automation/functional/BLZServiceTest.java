package automation.functional;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.thomas_bayer.blz.BLZService;
import com.thomas_bayer.blz.BLZServicePortType;
import com.thomas_bayer.blz.DetailsType;

public class BLZServiceTest {

	private BLZServicePortType responseSoap12;

	@BeforeTest
	public void setup() {
		BLZService blzService = new BLZService();
		responseSoap12 = blzService.getBLZServiceSOAP12PortHttp();
	}

	@Test
	public void DeutscheBank() {
		DetailsType bankDetails = responseSoap12.getBank("70070024");
		Assert.assertTrue(bankDetails.getBezeichnung().contains("Deutsche Bank"));
		Assert.assertEquals(bankDetails.getOrt(), "München");
		Assert.assertEquals(bankDetails.getPlz(), "80271");
	}

}
