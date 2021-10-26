package domfrnjtj1026;

import org.xml.sax.*;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;

public class DomReadFRNJTJ {
	public static void main(String[] args) {
		Document doc;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		User user = new User();
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse("userFRNJTJ.xml");
			
			NodeList list = doc.getElementsByTagName("user");
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					
					user.setId(Integer.parseInt(element.getAttribute("id")));
					user.setLastname(element.getElementsByTagName("lastname").item(0).getTextContent());
					user.setFirstname(element.getElementsByTagName("firstname").item(0).getTextContent());
					user.setProfession(element.getElementsByTagName("profession").item(0).getTextContent());
					System.out.println("Curren element: user\n"+user.toString()+"\n");
				}
			}
		} catch(SAXException | IOException | ParserConfigurationException e) {
			System.err.println(e.getMessage());
			user = new User();
		} catch(NumberFormatException nfe) {
			System.err.println(nfe.getMessage());
			user = new User();
		}
		
		
	}

}
