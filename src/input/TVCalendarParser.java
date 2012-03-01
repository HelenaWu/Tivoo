package input;

import general.Person;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.joda.time.DateTime;


public class TVCalendarParser extends NoNameSpaceParser {

	public TVCalendarParser(Element root)
			throws JDOMException, IOException {
		super(root);
	}

	@Override
	public DateTime parseTime(Element cal, String DateType, String TimeType) {
		if (DateType.equals("StartDate")) {
			String start = cal.getAttributeValue("start");
			start = start.substring(0, start.length() - 6);
			return createTime(start, "yyyymmddkkmmss");
		} else {
			String stop = cal.getAttributeValue("stop");
			stop = stop.substring(0, stop.length() - 6);
			return createTime(stop, "yyyymmddkkmmss");
		}

	}

	@Override
	public Person parseContact(Element cal) {
		return new Person(null, null, null, null);
	}

	@Override
	public Map<String, String> createMap() {
		Map<String, String> tagsMap = new HashMap<String, String>();
		tagsMap.put("root","tv");
		tagsMap.put("event", "programme");
		tagsMap.put("title", "title");
		tagsMap.put("description", "desc");
		tagsMap.put("startDate", "StartDate");
		tagsMap.put("endDate", "EndDate");
		tagsMap.put("link", "n/a");
		tagsMap.put("cost", "free");

		return tagsMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, String> createAdditionalInfo(Element cal) {
		HashMap<String,String> additionalInfo = new HashMap<String,String>();
		List<Element> credits = cal.getChild("credits").getChildren();
		String all = "";
		for( Element element : credits)
		{
			all +=element.getName()+": "+element.getText()+"\n";
		}
		additionalInfo.put("credits",all);
		all = "";
		additionalInfo.put("date", cal.getChildText("date"));
		
		List<Element> category = cal.getChildren("category");
		for( Element element : category)
		{
			all +=element.getText()+"\n";
		}
		additionalInfo.put("category","Category: "+all);
		all = "";
		
		List<Element> rating = cal.getChildren("rating");
		
		for( Element element : rating)
		{
			all +=element.getChildText("value")+"\n";
		}
		additionalInfo.put("rating","Rating: "+all);
		return  additionalInfo;
	}

}
