package hu.domparse.frnjtj;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMReadFrnjtj {
	private final static String FILE_NAME = "XMLFRNJTJ.xml";
	private static StringBuilder text;
	private static int indent = 0;

	public static void main(String[] args) {
		Document doc;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		text = new StringBuilder();			//	text StringBuilder adattag példányosítása, késõbb ebbe
											//	kerülnek bele az értékek soronként
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();	//Singleton példány lekérés
			doc = db.parse(FILE_NAME);				//XML fájl-ra rácsatlakozás
			
			Node er = doc.getFirstChild();	//ROOT elem lekérése
			NodeList erList = er.getChildNodes();						//ROOT elem tagjainak lekérése
			getAllElements(erList);									//	Átadás egy függvénynek, ami
																	//	feldarabolja a dokumentumot
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		writeOut();		//text változó kiíratása
	}
	
	private static void getAllElements(NodeList nl) {	//	Az elem sorokat veszi és eldönti, hogy van-e benne még több elem,
		Node node;										//	vagy már csak szöveg érték
		for (int i = 0; i < nl.getLength(); i++) {
			node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				
				text.append(indent(3)+node.getNodeName());
				
				if(node.hasAttributes()) {									//	Amennyiben van attribútum, 
					text.append(" "+getAttributes(node.getAttributes())+"\n");//	a megfelelõ taghoz hozzá adja
				} else {
					text.append("\n");
				}
				
				if(node.getChildNodes().getLength() != 1) {	//Ha a hossza nem egy, akkor nem csak szöveg tartalma van
					
					NodeList childList = node.getChildNodes();
					indent++;
					getAllElements(childList);
					
				} else {							//Ha van értéke adja hozzá a text-hez
					
					indent++;
					text.append(indent(3)+node.getTextContent()+"\n");
					indent--;
					
				}
			}
		}
		indent--;
	}
	
	private static String getAttributes(NamedNodeMap node) {	//	kiírja az attribútumokat {tag : érték; tag : érték}
		StringBuilder sb = new StringBuilder("{");				//	formában
		for (int i = 0; i < node.getLength(); i++) {
			if(i != node.getLength()-1) {
				sb.append(node.item(i).getNodeName()+" : "+node.item(i).getNodeValue()+"; ");
			} else {
				sb.append(node.item(i).getNodeName()+" : "+node.item(i).getNodeValue());
			}
		}
		
		sb.append("}");
		return sb.toString();
	}
	
	private static String indent(int multiplyBy) {		//Függvény ami számontartja a bekezdés bentebb törését
		StringBuilder whiteSpaces = new StringBuilder();
		for (int i = 0; i < (indent*multiplyBy); i++) {
			whiteSpaces.append(" ");
		}
		return whiteSpaces.toString();
	}
	
	private static void writeOut() {
		System.out.println(text.toString());
	}

}
