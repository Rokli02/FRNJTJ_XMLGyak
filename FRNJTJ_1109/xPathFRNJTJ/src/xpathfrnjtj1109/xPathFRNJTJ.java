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
				
				System.out.println("Aktu�lis elem: "+node.getNodeName());
				
				if(node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
					Element element = (Element) node;
					
					System.out.println("Hallgat� ID: "+element.getAttribute("id"));
					System.out.println("Keresztn�v: "+element.getElementsByTagName("keresztnev").item(0).getTextContent());
					System.out.println("Vezet�kn�v: "+element.getElementsByTagName("vezeteknev").item(0).getTextContent());
					System.out.println("Becen�v: "+element.getElementsByTagName("becenev").item(0).getTextContent());
					System.out.println("Kor: "+element.getElementsByTagName("kor").item(0).getTextContent()+"\n");
				}
			}
			
		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	private static String getExpression(int number) {
		switch (number) {
		case 0: return "class";					//	Kiindul�pont
		case 1: return "class/student";				//	1) V�lassza ki az �sszes student element, amely a class gyermekei.
		case 2: return "class/student[@id='01']";	//	2) V�lassza ki azt a student elemet, amely rendelkezik "id" attrib�tummal �s �rt�ke "01".
		case 3: return "//student";					//	3) Kiv�lasztja az �sszes student elemet, f�ggetlen�l att�l, hogy hol vannak a dokumentumban.
		case 4: return "class/student[2]";			//	4) V�lassza ki a m�sodik student element, amely a class elem gyermeke.
		case 5: return "class/student[last()]";		//	5) V�lassza ki a utols� student elemet, amely a class elem gyermeke.
		case 6: return "class/student[last()-1]";	//	6) V�lassza ki a utols� el�tti student elemet, amely a class elem gyermeke.
		case 7: return "class/student[position()<3]";//	7) V�lassza ki az els� k�t student elemet, amelyek a class elem gyermekei.
		case 8: return "class/*";					//	8) V�lassza ki class elem �sszes gyermek elem�t
		case 9: return "//student[@*]";				//	9) V�lassza ki az �sszes student elemet, amely rendelkezik legal�bb egy b�rmilyen attrib�tummal.
		case 10: return "*";						//	10) V�lassza ki a dokumentum �sszes elem�t.
		case 11: return "class/student[kor>20]";	//	11) V�lassza ki a class elem �sszes student elem�t, amelyn�l a kor>20.
		case 12: return "//student/keresztnev | //student/vezeteknev";	//	12) V�lassza ki az �sszes student elem �sszes keresztnev �s vezeteknev csomm�pontot.

		default: return "";
		}
	}
	
}
