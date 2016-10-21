package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

public class SOAPClient {

	private String soapAPIURL;

	public SOAPClient(String url) {
		this.soapAPIURL = url;
	}
	
	public SOAPClient(String url, String proxyHost, String proxyPort) {
		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.port", proxyPort);
		this.soapAPIURL = url;
	}

	public SOAPMessage SOAPSendRequest(String url, String xmlExpanded) {

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

	private SOAPMessage createSOAPRequest(String xml) {
		MessageFactory factory;
		SOAPMessage message = null;
		try {
			factory = MessageFactory.newInstance();
			message = factory.createMessage(new MimeHeaders(),
					new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));

		} catch (SOAPException | IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	public String getSoapContent(SOAPMessage response) throws SOAPException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		response.writeTo(out);
		return new String(out.toByteArray());

	}

	public String execute(String requestAsString) {
		RequestBody body = RequestBody.create(MediaType.parse("text/xml"), requestAsString);
		Request request = new Request.Builder().url(soapAPIURL).post(body).addHeader("content-type", "text/xml")
				.addHeader("cache-control", "no-cache").build();
		return sendRequest(request);
	}
	
	public String execute(String requestAsString, String username, String password) {
		RequestBody body = RequestBody.create(MediaType.parse("text/xml"), requestAsString);
		Request request = new Request.Builder().url(soapAPIURL).post(body).addHeader("content-type", "text/xml")
				.addHeader("Authorization", Credentials.basic(username, password))
				.addHeader("cache-control", "no-cache").build();
		return sendRequest(request);
	}

	private String sendRequest(Request request) {
		String response = null;
		OkHttpClient client = new OkHttpClient();
		client.setConnectTimeout(30, TimeUnit.SECONDS);
		client.setReadTimeout(30, TimeUnit.SECONDS);
		try {
			response = client.newCall(request).execute().body().string();
		} catch (IOException e) {

		}

		return response;
	}

}
