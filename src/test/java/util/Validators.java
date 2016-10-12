package util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.InputSource;

public class Validators {

	public static boolean isXMLValid(String xmlString) {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        builder.parse( new InputSource( new StringReader( xmlString ) ) );  
	    } catch (Exception e) {  
	        e.printStackTrace(); 
	        return false;
	    }
	    return true;
	}
	
}
