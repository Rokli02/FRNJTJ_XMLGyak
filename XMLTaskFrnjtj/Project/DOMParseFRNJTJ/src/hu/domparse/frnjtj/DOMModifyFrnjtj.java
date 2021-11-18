package hu.domparse.frnjtj;

import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMModifyFrnjtj {
	private final static String FILE_NAME = "XMLFRNJTJ.xml";
	
	private final static String ROOT_OF_ELEMENT = "Szemelyek",				//A módosítandó elem ROOT-ja
								ELEMENT_TO_MODIFY = "nev",					//A módosítandó mezõ neve
								MODIFIABLE_VALUE_OF_ELEMENT = "Markó Roland",//Mezõ beli érték, amit módosítani szeretnénk, ha üres, minden értéket változtat
								MODIFY_VALUE_OF_ELEMENT = "Molnar Mark";	//Érték amire változtatja a módosítandó mezõt
	
	private final static String ROOT_OF_ATTRIBUTE = "Kolcsonzok",			//A módosítandó attribútum ROOT-ja
								ATTRIBUTE_TO_MODIFY = "iranyitoszam",		//A módosítandó attribútum neve
								MODIFIABLE_VALUE_OF_ATTRIBUTE = "1001",		//attribútum beli érték, amit módosítani szeretnénk, ha üres, minden értéket változtat
								MODIFY_VALUE_OF_ATTRIBUTE = "1003";			//Érték amire változtatja a módosítandó attribútumot
	
	public static void main(String[] args) {
		Document doc;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(FILE_NAME);
			
			Node rootNode = doc.getFirstChild();
			NodeList rootNodeList = rootNode.getChildNodes();
			
			for (int i = 0; i < rootNodeList.getLength(); i++) {
				Node supNode = rootNodeList.item(i);
				
				if(supNode.getNodeType() == Node.ELEMENT_NODE) {
					
					//ELEMENT
					if(supNode.getNodeName().equals(ROOT_OF_ELEMENT)) {
						if(!ELEMENT_TO_MODIFY.isBlank()) {
							setElementValue(supNode.getChildNodes());
						}
					}
					
					//ATTRIBUTE
					if(supNode.getNodeName().equals(ROOT_OF_ATTRIBUTE)) {
						if(!ATTRIBUTE_TO_MODIFY.isBlank()) {
							setAttributeValue(supNode.getChildNodes());
						}
					}
				}
			}
			
			
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			
			tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			DOMSource source = new DOMSource(rootNode);
			StreamResult console = new StreamResult(System.out);
			
			System.out.println("----------------- Modified File -----------------");
			
			tf.transform(source, console);
			
		} catch (ParserConfigurationException | SAXException | IOException 
				| TransformerException | TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
	}
	
	public static void setElementValue(NodeList nodeList) {		//Megkeresi a feltételeknek megfelelõ elemeket és a megadott értékre írja át azokat
		boolean found = false;
		
		for (int i = 0; i < nodeList.getLength(); i++) {		
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {		
				NodeList list = node.getChildNodes();
				
				for (int j = 0; j < list.getLength(); j++) {
					Node subNode = list.item(j);
					
					if(subNode.getNodeType() == Node.ELEMENT_NODE) {	
						if(subNode.getNodeName().equals(ELEMENT_TO_MODIFY)) {	//vizsgálja, hogy a megfelelõ elemnél van-e a ciklus
							
							if(subNode.getTextContent().equals(MODIFIABLE_VALUE_OF_ELEMENT) ||	//megnézi, hogy a módosítandó érték megtalálható-e itt
								MODIFIABLE_VALUE_OF_ELEMENT.isBlank()) {
								
								subNode.setTextContent(MODIFY_VALUE_OF_ELEMENT);
								found = true;
							}
						}
					}
				}
			}
		}
		if(!found)
			System.out.println("Nem történt elem változtatás");
	}

	public static void setAttributeValue(NodeList nodeList) {	//Megkeresi a feltételeknek megfelelõ attribútumokat és a megadott értékre írja át azokat
		boolean found = false;
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {	//Kiválatszja a listából az elemeket
				Element element = (Element) node;
				
				if(!element.getAttribute(ATTRIBUTE_TO_MODIFY).isBlank()) {	//Megnézi, hogy létezik-e az a tulajdonság,
																			//amit meg akarunk változtatni
					if(element.getAttribute(ATTRIBUTE_TO_MODIFY).equals(MODIFIABLE_VALUE_OF_ATTRIBUTE) ||
						MODIFIABLE_VALUE_OF_ATTRIBUTE.isBlank()) {
						
						element.setAttribute(ATTRIBUTE_TO_MODIFY, MODIFY_VALUE_OF_ATTRIBUTE);	//A megadott attribútum értékét a megadott értékre írja át
						found = true;
						
					}
				}
				
			}
		}
		if(!found)
			System.out.println("Nem történt attribútum változtatás");
		
	}
}
