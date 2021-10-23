package SaxFRNJTJ1019;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SaxFRNJTJ {

	public static void main(String[] args) {
		try {
			
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();
			SaxHandler handler = new SaxHandler();
			saxParser.parse(new File("src/SaxFRNJTJ1019/szemelyekFRNJTJ.xml"), handler);
			
		} catch(ParserConfigurationException | SAXException | IOException pce) {
			System.out.println("Hibaüzenet: "+pce.getMessage());
		}
	}
	
}
