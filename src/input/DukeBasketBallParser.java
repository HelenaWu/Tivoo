package input;

import general.Person;

import java.io.IOException;
import java.util.HashMap;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.joda.time.DateTime;
import java.util.*;

public class DukeBasketBallParser extends  NoNameSpaceParser {
	public DukeBasketBallParser(Element root) throws IOException, JDOMException {
		super(root);

	}
	@Override
	public Map<String, String> createMap(){
		Map<String, String> tagsMap = new HashMap<String, String>();
		tagsMap.put("root","dataroot");
		tagsMap.put("event", "Calendar");
		tagsMap.put("title", "Subject");
		tagsMap.put("description", "Description");
		tagsMap.put("startDate", "StartDate");
		tagsMap.put("startTime", "StartTime");
		tagsMap.put("endDate", "EndDate");
		tagsMap.put("endTime", "EndTime");
		tagsMap.put("link", "n/a");
		tagsMap.put("cost", "free");
		
		return tagsMap;
		
	}
	
	public DateTime parseTime(Element cal, String DateType, String TimeType) {
		String dateData = cal.getChildText(DateType);
		String timeData = cal.getChildText(TimeType);
		String dateString = dateData + " " + timeData;
		
		return createTime(dateString,"MM/dd/yyyy h:mm:ss a");
	}

	@Override
	public Person parseContact(Element cal) {
		
		return new Person(null, null, null,null);
	}
	@Override
	public HashMap<String, String> createAdditionalInfo(Element cal) {
		HashMap<String,String> additionalInfo = new HashMap<String,String>();
		additionalInfo.put("Location", cal.getChildText("Location"));
		additionalInfo.put("Priority", cal.getChildText("Priority"));
		additionalInfo.put("Sensitivity", cal.getChildText("Sensitivity"));
		
		return additionalInfo;
	}



}