package util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLParseUtils {

	public File createXmlFile(final String filePath, final String fileContent) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		File fileToBeCreated = new File(filePath);
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(fileContent)));
			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();
			Source src = new DOMSource(document);
			Result dest = new StreamResult(fileToBeCreated);
			aTransformer.transform(src, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileToBeCreated;
	}

	public List<NodeList> getChildNodes(final String xmlString, final String nodeName) {
		ArrayList<NodeList> children = new ArrayList<NodeList>();
		NodeList nodeList = getElementsByTagName(xmlString, nodeName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			NodeList nl = nodeList.item(i).getChildNodes();
			if (nl.getLength() > 0) {
				children.add(nl);
			}
		}
		return children;
	}

	public NodeList getElementsByTagName(final String xmlString, final String nodeName) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		Document document = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			document = docBuilder
					.parse(new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8)));
		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
		NodeList nodeList = document.getElementsByTagName(nodeName);
		return nodeList;
	}

	public NodeList getNodeListUsingXpath(final String xmlString, final String xPathExpression) {
		XPath xpath;
		XPathExpression expr;
		Object result = null;
		Document doc = parseXML(new StringReader(xmlString));
		try {
			xpath = XPathFactory.newInstance().newXPath();
			expr = xpath.compile(xPathExpression);
			result = expr.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return (NodeList) result;
	}

	public int getNumberOfNodesUsingXpath(final String xmlString, final String xPathExpression) {
		return getNodeListUsingXpath(xmlString, xPathExpression).getLength();
	}

	public int getNumberOfTags(final String xmlString, final String nodeName) {
		NodeList nodeList = getElementsByTagName(xmlString, nodeName);
		return nodeList.getLength();
	}

	public String getSiblingTextNodeByXPath(final String xmlString, final String xPathExpression,
			final String token) {
		XPath xpath;
		XPathExpression expr;
		Object result = null;
		Document doc = parseXML(new StringReader(xmlString));
		try {
			xpath = XPathFactory.newInstance().newXPath();
			expr = xpath.compile(xPathExpression);
			result = expr.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		NodeList list = (NodeList) result;
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getTextContent().equals(token)) {
				return list.item(i).getNextSibling().getTextContent();
			}
		}
		return null;
	}

	public String getTextFromNode(final String xmlString, final String nodeName) {
		NodeList nodeList = getElementsByTagName(xmlString, nodeName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeName() == nodeName) {
				return node.getFirstChild().getNodeValue();
			}
		}
		return null;
	}

	public boolean isTextElementPresent(final String xmlString, final String nodeName,
			final String textElement) {
		Document doc = parseXML(new StringReader(xmlString));
		NodeList nodeList = doc.getElementsByTagName(nodeName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (textElement.equals(nodeList.item(i).getTextContent()))
				return true;
		}
		return false;
	}

	private Document parseXML(final Reader reader) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			builder = dbf.newDocumentBuilder();
			doc = builder.parse(new InputSource(reader));
		} catch (ParserConfigurationException | SAXException | IOException e) {

			e.printStackTrace();
		}
		return doc;
	}
}
