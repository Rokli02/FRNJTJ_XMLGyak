package SaxFRNJTJ1019;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

//tartalomkezelõ keret létrehozása, valamint az esemény- és hibakezelõ metódusok definiálása
public class SaxValidationHandler extends DefaultHandler {
	
	public void warning(SAXParseException spe) throws SAXException {
	    System.out.println("Figyelmeztetés:\n" + spe.getMessage());
	}
	        
	public void error(SAXParseException spe) throws SAXException {
	    String message = "Hiba:\n" + spe.getMessage();
	    throw new SAXException(message);
	}

	public void fatalError(SAXParseException spe) throws SAXException {
	    String message = "Fatális hiba:\n" + spe.getMessage();
	    throw new SAXException(message);
	}
}
