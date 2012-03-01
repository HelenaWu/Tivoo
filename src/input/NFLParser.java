package input;

import general.Person;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.joda.time.DateTime;


public class NFLParser extends NoNameSpaceParser{
	
	public NFLParser(Element root) throws JDOMException, IOException {
		super(root);

	}

	@Override
	public DateTime parseTime(Element cal, String DateType, String TimeType) {
		String all = cal.getChildText(DateType);	
		return createTime(all,"yyyy-MM-dd HH:mm:ss");
	}

	@Override
	public Person parseContact(Element cal) {
		return new Person(null,null,null,null);
	}

	@Override
	public Map<String, String> createMap() {
		Map<String, String> tagsMap = new HashMap<String, String>();
		tagsMap.put("root", "document");
		tagsMap.put("event", "row");
		tagsMap.put("title", "Col1");
		tagsMap.put("description", "Col15");
		tagsMap.put("startDate", "Col8");
		tagsMap.put("startTime", "Col8");
		tagsMap.put("endDate", "Col9");
		tagsMap.put("endTime", "Col9");

		tagsMap.put("link", "Col2");
		tagsMap.put("cost", "N/A");
		
		return tagsMap;
		
	}

	@Override
	public HashMap<String, String> createAdditionalInfo(Element cal) {
		HashMap<String,String> additionalInfo = new HashMap<String,String>();
		return additionalInfo;
	}

}
