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
	
	private final static String ROOT_OF_ELEMENT = "Szemelyek",				//A m�dos�tand� elem ROOT-ja
								ELEMENT_TO_MODIFY = "nev",					//A m�dos�tand� mez� neve
								MODIFIABLE_VALUE_OF_ELEMENT = "Mark� Roland",//Mez� beli �rt�k, amit m�dos�tani szeretn�nk, ha �res, minden �rt�ket v�ltoztat
								MODIFY_VALUE_OF_ELEMENT = "Molnar Mark";	//�rt�k amire v�ltoztatja a m�dos�tand� mez�t
	
	private final static String ROOT_OF_ATTRIBUTE = "Kolcsonzok",			//A m�dos�tand� attrib�tum ROOT-ja
								ATTRIBUTE_TO_MODIFY = "iranyitoszam",		//A m�dos�tand� attrib�tum neve
								MODIFIABLE_VALUE_OF_ATTRIBUTE = "1001",		//attrib�tum beli �rt�k, amit m�dos�tani szeretn�nk, ha �res, minden �rt�ket v�ltoztat
								MODIFY_VALUE_OF_ATTRIBUTE = "1003";			//�rt�k amire v�ltoztatja a m�dos�tand� attrib�tumot
	
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
	
	public static void setElementValue(NodeList nodeList) {		//Megkeresi a felt�teleknek megfelel� elemeket �s a megadott �rt�kre �rja �t azokat
		boolean found = false;
		
		for (int i = 0; i < nodeList.getLength(); i++) {		
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {		
				NodeList list = node.getChildNodes();
				
				for (int j = 0; j < list.getLength(); j++) {
					Node subNode = list.item(j);
					
					if(subNode.getNodeType() == Node.ELEMENT_NODE) {	
						if(subNode.getNodeName().equals(ELEMENT_TO_MODIFY)) {	//vizsg�lja, hogy a megfelel� elemn�l van-e a ciklus
							
							if(subNode.getTextContent().equals(MODIFIABLE_VALUE_OF_ELEMENT) ||	//megn�zi, hogy a m�dos�tand� �rt�k megtal�lhat�-e itt
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
			System.out.println("Nem t�rt�nt elem v�ltoztat�s");
	}

	public static void setAttributeValue(NodeList nodeList) {	//Megkeresi a felt�teleknek megfelel� attrib�tumokat �s a megadott �rt�kre �rja �t azokat
		boolean found = false;
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {	//Kiv�latszja a list�b�l az elemeket
				Element element = (Element) node;
				
				if(!element.getAttribute(ATTRIBUTE_TO_MODIFY).isBlank()) {	//Megn�zi, hogy l�tezik-e az a tulajdons�g,
																			//amit meg akarunk v�ltoztatni
					if(element.getAttribute(ATTRIBUTE_TO_MODIFY).equals(MODIFIABLE_VALUE_OF_ATTRIBUTE) ||
						MODIFIABLE_VALUE_OF_ATTRIBUTE.isBlank()) {
						
						element.setAttribute(ATTRIBUTE_TO_MODIFY, MODIFY_VALUE_OF_ATTRIBUTE);	//A megadott attrib�tum �rt�k�t a megadott �rt�kre �rja �t
						found = true;
						
					}
				}
				
			}
		}
		if(!found)
			System.out.println("Nem t�rt�nt attrib�tum v�ltoztat�s");
		
	}
}
