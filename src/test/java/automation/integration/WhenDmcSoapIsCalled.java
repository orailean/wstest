package automation.integration;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import util.LocalProperties;
import util.SOAPClient;
import util.Utilities;
import util.XMLParseUtils;

public class WhenDmcSoapIsCalled {

	File xmlFileTemplate;
	String response;

	@Test
	public void should_return_the_application_version() {

		xmlFileTemplate = new File("./src/main/resources/soap12/GetDMCVersion.xml");
		String xmlFileAsString = Utilities.fileToString(xmlFileTemplate);
		String dmcApiUrl = "http://" + LocalProperties.getWebDomain() + LocalProperties.getApiEndPoint();

		SOAPClient soapClient = new SOAPClient(dmcApiUrl, LocalProperties.getProxyHost(),
				LocalProperties.getProxyPort());
		response = soapClient.execute(xmlFileAsString, LocalProperties.getApiUser(), LocalProperties.getApiPassword());

		XMLParseUtils xmlUtils = new XMLParseUtils();
		String version = xmlUtils.getTextFromNode(response, "version");

		Assert.assertNotNull(version);

	}
}
