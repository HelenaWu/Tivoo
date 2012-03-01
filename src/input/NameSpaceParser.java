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

public abstract class NameSpaceParser extends CalendarParser {

	private Map<String,String> myNamespacemap;

	public NameSpaceParser(Element root) throws JDOMException, IOException {
		super(root);
		myNamespacemap = new HashMap<String, String>();
		myNamespacemap.put("namespace",root.getNamespaceURI());
	}

	public Element findElement(Element cal, String tag) throws JDOMException,
			JaxenException, IOException {
		return cal.getChild(tag, this.getRoot().getNamespace());
	}

	public List<Element> findAllInstancesOfElement(String info)
			throws JDOMException, JaxenException, IOException {
		List<Element> list;
		XPath xpath;
		xpath = XPath.newInstance("//namespace:" + info);
		xpath.addNamespace("namespace", getRoot().getNamespaceURI());
		list = xpath.selectNodes(getRoot());
		return list;
	}

	@Override
	public String getField(Element cal, String fieldName)throws JaxenException,
			JDOMException, IOException {


		try{
			return findElement(cal, this.getTagMap().get(fieldName)).getText();
		}catch (NullPointerException e){
			return this.getTagMap().get(fieldName);
		}
	}

}
