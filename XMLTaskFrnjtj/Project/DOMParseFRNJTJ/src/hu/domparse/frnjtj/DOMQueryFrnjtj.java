package hu.domparse.frnjtj;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMQueryFrnjtj {
	private final static String FILE_NAME = "XMLFRNJTJ.xml";
	private final static String QUERY_BY_NODE = "Kolcsonzok";
	private final static String QUERY_BY_ATTRIBUTE = "iranyitoszam";
	private final static String QUERY_BY_ATTRIBUTEVALUE = "1001";
	private static StringBuilder text;

	public static void main(String[] args) {
		Document doc;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		text = new StringBuilder("Query from: "+QUERY_BY_NODE+"\n____________________________________________\n\n");
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(FILE_NAME);
			
			NodeList erList = doc.getFirstChild().getChildNodes();	//ROOT-ba levõ elemek
			
			for (int i = 0; i < erList.getLength(); i++) {	//Kiszedi az 5 fõ tagot
				Node node = erList.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(QUERY_BY_NODE)) {
					
					NodeList subList = node.getChildNodes();		//Többször elõforduló elemek
					QueryNodes(subList, QUERY_BY_ATTRIBUTE, QUERY_BY_ATTRIBUTEVALUE);
				}
			}
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		writeOut();
	}

	
	public static void QueryNodes(NodeList nl,  String attributeName, String attributeValue) {	
		//megkeresi azokat a tagokat, amiknek az attribútumai megfelelnek a paraméterben átadott értékekkel
		for (int i = 0; i < nl.getLength(); i++) {	//Többször elõforduló elemek
			Node node = nl.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				String[] attributes = getAttributes(node.getAttributes()).split(";");
				String[] firstAttribute = attributes[0].split(":");
				
				if(attributes.length != 1) {
					String[] secondAttribute = attributes[1].split(":");
					if(secondAttribute[0].equals(attributeName) && secondAttribute[1].equals(attributeValue)) {
						addNodeToText(node, attributes);
						continue;
					}
				}
				if(firstAttribute[0].equals(attributeName) && firstAttribute[1].equals(attributeValue)) {
					addNodeToText(node, attributes);
				}
			}
		}
	}
	
	private static void addNodeToText(Node node, String[] attributes) {	//a text adattaghoz újabb szöveg táblát ad hozzá
		text.append("Current Element: "+node.getNodeName()+"\n");
		text.append("Attributes: \n\t{"+attributes[0]);
		
		if(attributes.length != 1) {
			text.append(" ; "+attributes[1]+"}\n");
		} else {
			text.append("}\n");
		}
		
		text.append("Elements: \n"+getElements(node)+"\n");
		text.append("-----------------------------------------------\n");
	}
	
	private static String getAttributes(NamedNodeMap node) {	//visszaadja az attribútumokat tag:érték;tag:érték alakba
		StringBuilder sb = new StringBuilder();		
		for (int i = 0; i < node.getLength(); i++) {
			if(i != node.getLength()-1) {
				sb.append(node.item(i).getNodeName()+":"+node.item(i).getNodeValue()+";");
			} else {
				sb.append(node.item(i).getNodeName()+":"+node.item(i).getNodeValue());
			}
		}
		
		return sb.toString();
	}
	
	private static String getElements(Node node) {	//az elemeket egy olvasható szöveg alakba hozza
		StringBuilder sb = new StringBuilder();
		NodeList list = node.getChildNodes();
		
		for (int i = 0; i < list.getLength(); i++) {
			Node subNode = list.item(i);
			
			if(subNode.getNodeType() == Node.ELEMENT_NODE) {
				sb.append("\t"+subNode.getNodeName()+": "+subNode.getTextContent()+"\n");
			}
		}
		
		return sb.toString();
	}
	
	private static void writeOut() {
		System.out.println(text.toString());
	}
}
