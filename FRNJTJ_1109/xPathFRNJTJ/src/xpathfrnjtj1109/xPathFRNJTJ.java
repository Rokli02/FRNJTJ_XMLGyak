package xpathfrnjtj1109;

import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.xml.sax.SAXException;
import org.w3c.dom.*;

public class xPathFRNJTJ {
	private static final String FILE_NAME = "studentFRNJTJ.xml";
	
	public static void main(String[] args) {
		Document doc;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(FILE_NAME);
			doc.getDocumentElement().normalize();
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			String expression = getExpression(12);
			
			
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				
				System.out.println("Aktuális elem: "+node.getNodeName());
				
				if(node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
					Element element = (Element) node;
					
					System.out.println("Hallgató ID: "+element.getAttribute("id"));
					System.out.println("Keresztnév: "+element.getElementsByTagName("keresztnev").item(0).getTextContent());
					System.out.println("Vezetéknév: "+element.getElementsByTagName("vezeteknev").item(0).getTextContent());
					System.out.println("Becenév: "+element.getElementsByTagName("becenev").item(0).getTextContent());
					System.out.println("Kor: "+element.getElementsByTagName("kor").item(0).getTextContent()+"\n");
				}
			}
			
		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	private static String getExpression(int number) {
		switch (number) {
		case 0: return "class";					//	Kiindulópont
		case 1: return "class/student";				//	1) Válassza ki az összes student element, amely a class gyermekei.
		case 2: return "class/student[@id='01']";	//	2) Válassza ki azt a student elemet, amely rendelkezik "id" attribútummal és értéke "01".
		case 3: return "//student";					//	3) Kiválasztja az összes student elemet, függetlenül attól, hogy hol vannak a dokumentumban.
		case 4: return "class/student[2]";			//	4) Válassza ki a második student element, amely a class elem gyermeke.
		case 5: return "class/student[last()]";		//	5) Válassza ki a utolsó student elemet, amely a class elem gyermeke.
		case 6: return "class/student[last()-1]";	//	6) Válassza ki a utolsó elõtti student elemet, amely a class elem gyermeke.
		case 7: return "class/student[position()<3]";//	7) Válassza ki az elsõ két student elemet, amelyek a class elem gyermekei.
		case 8: return "class/*";					//	8) Válassza ki class elem összes gyermek elemét
		case 9: return "//student[@*]";				//	9) Válassza ki az összes student elemet, amely rendelkezik legalább egy bármilyen attribútummal.
		case 10: return "*";						//	10) Válassza ki a dokumentum összes elemét.
		case 11: return "class/student[kor>20]";	//	11) Válassza ki a class elem összes student elemét, amelynél a kor>20.
		case 12: return "//student/keresztnev | //student/vezeteknev";	//	12) Válassza ki az összes student elem összes keresztnev és vezeteknev csommópontot.

		default: return "";
		}
	}
	
}
