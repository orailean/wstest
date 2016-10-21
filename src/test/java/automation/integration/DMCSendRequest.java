package automation.integration;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import util.LocalProperties;
import util.SOAPClient;
import util.Utilities;

public class DMCSendRequest {

	File xmlFileTemplate;
	String response;

	@Test
	public void GetDMCVersion() {

		xmlFileTemplate = new File("./src/main/resources/soap12/GetDMCVersion.xml");
		String xmlFileAsString = Utilities.fileToString(xmlFileTemplate);
		String dmcApiUrl = "http://" + LocalProperties.getWebDomain() + LocalProperties.getApiEndPoint();

		SOAPClient soapClient = new SOAPClient(dmcApiUrl, LocalProperties.getProxyHost(),
				LocalProperties.getProxyPort());
		response = soapClient.execute(xmlFileAsString, LocalProperties.getApiUser(), LocalProperties.getApiPassword());

		Assert.assertNotNull(response);

	}
}
