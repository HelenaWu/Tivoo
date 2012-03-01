package input;
import general.Person;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.joda.time.DateTime;


public class DukeCalendarParser extends NoNameSpaceParser {

	public DukeCalendarParser(Element root) throws JDOMException, IOException {
		super(root);
	}


	
	public Map<String, String> createMap(){
		Map<String, String> tagsMap = new HashMap<String, String>();
		tagsMap.put("root", "events");
		tagsMap.put("event", "event");
		tagsMap.put("title", "summary");
		tagsMap.put("description", "description");
		tagsMap.put("startDate", "start");
		tagsMap.put("endDate", "end");
		tagsMap.put("link", "link");
		tagsMap.put("cost", "cost");
		
		return tagsMap;
		
	}

	@Override
	public DateTime parseTime(Element cal, String DateType, String TimeType) {
		String month = cal.getChild(DateType).getChildText("month");
		String day = cal.getChild(DateType).getChildText("day");
		String year = cal.getChild(DateType).getChildText("year");
		String time = cal.getChild(DateType).getChildText("time");
		String[] timeParts = time.split(" ");
		String timeData = timeParts[0] + ":00 "+ timeParts[1];
		String dateString = month + "/" + day + "/" + year + " " + timeData;
		return createTime(dateString, "MM/dd/yyyy h:mm:ss a");
	}
	
	@Override
	public Person parseContact(Element cal) {
		
		Person contact = new Person(
				cal.getChild("contact").getChildText("id"),
				cal.getChild("contact").getChildText("name"),
				cal.getChild("contact").getChildText("email"),
				cal.getChild("contact").getChildText("phone"));
		return contact;
	}



	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, String> createAdditionalInfo(Element cal) {
		HashMap<String,String> additionalInfo = new HashMap<String,String>();
		additionalInfo.put("allDay", cal.getChild("end").getChildText("allday"));
		additionalInfo.put("status", cal.getChildText("status"));
		additionalInfo.put("reccuring", cal.getChildText("recurring"));
		additionalInfo.put("public", cal.getChildText("public"));
		List<Element> categories = cal.getChild("categories").getChildren("category");
		String all = "";
		for(Element element : categories){
			all += element.getChildText("value")+"\n";
		}
		additionalInfo.put("category", "Categories: "+ all);		
		return additionalInfo;
	}



}