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
		text = new StringBuilder();			//	text StringBuilder adattag p�ld�nyos�t�sa, k�s�bb ebbe
											//	ker�lnek bele az �rt�kek soronk�nt
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();	//Singleton p�ld�ny lek�r�s
			doc = db.parse(FILE_NAME);				//XML f�jl-ra r�csatlakoz�s
			
			Node er = doc.getFirstChild();	//ROOT elem lek�r�se
			NodeList erList = er.getChildNodes();						//ROOT elem tagjainak lek�r�se
			getAllElements(erList);									//	�tad�s egy f�ggv�nynek, ami
																	//	feldarabolja a dokumentumot
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		writeOut();		//text v�ltoz� ki�rat�sa
	}
	
	private static void getAllElements(NodeList nl) {	//	Az elem sorokat veszi �s eld�nti, hogy van-e benne m�g t�bb elem,
		Node node;										//	vagy m�r csak sz�veg �rt�k
		for (int i = 0; i < nl.getLength(); i++) {
			node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				
				text.append(indent(3)+node.getNodeName());
				
				if(node.hasAttributes()) {									//	Amennyiben van attrib�tum, 
					text.append(" "+getAttributes(node.getAttributes())+"\n");//	a megfelel� taghoz hozz� adja
				} else {
					text.append("\n");
				}
				
				if(node.getChildNodes().getLength() != 1) {	//Ha a hossza nem egy, akkor nem csak sz�veg tartalma van
					
					NodeList childList = node.getChildNodes();
					indent++;
					getAllElements(childList);
					
				} else {							//Ha van �rt�ke adja hozz� a text-hez
					
					indent++;
					text.append(indent(3)+node.getTextContent()+"\n");
					indent--;
					
				}
			}
		}
		indent--;
	}
	
	private static String getAttributes(NamedNodeMap node) {	//	ki�rja az attrib�tumokat {tag : �rt�k; tag : �rt�k}
		StringBuilder sb = new StringBuilder("{");				//	form�ban
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
	
	private static String indent(int multiplyBy) {		//F�ggv�ny ami sz�montartja a bekezd�s bentebb t�r�s�t
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
