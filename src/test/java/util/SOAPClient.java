package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class SOAPClient {

	public static SOAPMessage SOAPSendRequest(String url, String xmlExpanded) {

		SOAPMessage soapResponse = null;

		// Create SOAP Connection
		SOAPConnectionFactory soapConnectionFactory;
		try {
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			soapResponse = soapConnection.call(createSOAPRequest(xmlExpanded), url);

			soapConnection.close();
		} catch (UnsupportedOperationException | SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return soapResponse;

	}

	private static SOAPMessage createSOAPRequest(String xml) {
		MessageFactory factory;
		SOAPMessage message = null;
		try {
			factory = MessageFactory.newInstance();
			message = factory.createMessage(new MimeHeaders(),
					new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
		} catch (SOAPException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

	public static String getSoapContect(SOAPMessage response) throws SOAPException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		response.writeTo(out);
		return new String(out.toByteArray());

	}

}
