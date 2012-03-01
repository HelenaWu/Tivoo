package input;

import general.Event;
import general.Person;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jaxen.JaxenException;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.joda.time.DateTime;

public abstract class NoNameSpaceParser extends CalendarParser {

	public NoNameSpaceParser(Element root) throws JDOMException, IOException{
		super(root);
	}
	
	public List<Element> findAllInstancesOfElement(String info) throws JDOMException, JaxenException, IOException{
		List<Element> list;
		XPath xpath;
		xpath = XPath.newInstance("//" + info);
		list = xpath.selectNodes(this.getRoot());
		return list;
	}
	

	public String getField(Element cal, String fieldName) throws JaxenException, JDOMException, IOException{
		return cal.getChildText(this.getTagMap().get(fieldName));
		
	}
	
	
}
