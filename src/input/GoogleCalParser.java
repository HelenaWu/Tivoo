package input;
import general.Person;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jaxen.JaxenException;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.joda.time.DateTime;


public class GoogleCalParser extends NameSpaceParser {

	public GoogleCalParser(Element root) throws JDOMException,
			IOException {
		super(root);


	}

	@Override
	public DateTime parseTime(Element cal, String DateType, String TimeType)
			throws JaxenException, JDOMException, IOException {
		Element summary = findElement(cal, "summary");

		String info = summary.getText();
		String[] words = info.split(" ");
		if (!info.startsWith("Recurring Event")) {
			String month = words[2];
			String day = words[3].substring(0, words[3].length() - 1);
			String year = words[4];
			String time = "";
			String halfDay = "";
			
			
			if (!words[4].contains("<br")) {
				
			if (DateType.equals("startDate")) {
				
					time = words[5].substring(0, words[5].length() - 2);
					if (!time.contains(":")) {
						time += ":00";
					}
				

				halfDay = words[5].substring(words[5].length() - 2);

			} else if (DateType.equals("endDate")) {
				String[] timeData = words[7].split("&");

				time = timeData[0].substring(0, timeData[0].length() - 2);

				if (!time.contains(":")) {
					time += ":00";
				}

				halfDay = timeData[0].substring(timeData[0].length() - 2);
			}
			}
		else{
			year = year.substring(0,4);
			time = "12:00";
			halfDay = "am";
		}
			String dateString = month + "/" + day + "/" + year + " " + time
					+ " " + halfDay;

			return createTime(dateString, "MMM/dd/yyyy h:mm a");
		}

	else {
			return parseRecurring(words, DateType);
	}
	}

		
	

	public DateTime parseRecurring(String[] words, String DateType) {
		String day = words[3];
		String startTime = words[4];
		String duration = words[6].substring(0, 4);

		Integer durationInSecs = Integer.parseInt(duration);
		String dateString = day + " " + startTime;

		DateTime start = createTime(dateString, "yyyy-mm-dd kk:mm:ss");

		if (DateType.equals("startDate")) {

			return start;
		} else if (DateType.equals("endDate")) {
			DateTime end = start.plusSeconds(durationInSecs);
			return end;
		}

		return null;

	}

	@Override
	public Person parseContact(Element cal) throws JDOMException, IOException,
			JaxenException {
		Namespace a = this.getRoot().getNamespace();
		String name = cal.getChild("author", a).getChildText("name", a);
		String email = cal.getChild("author", a).getChildText("email", a);
		Person contact = new Person(null, name, email, null);
		return contact;
	}

	@Override
	public Map<String, String> createMap() {
		Map<String, String> tagsMap = new HashMap<String, String>();
		tagsMap.put("root","feed");
		tagsMap.put("event", "entry");
		tagsMap.put("title", "title");
		tagsMap.put("description", "content");
		tagsMap.put("startDate", "startDate");
		tagsMap.put("endDate", "endDate");
		tagsMap.put("link", "id");
		tagsMap.put("cost", "n/a");

		return tagsMap;
	}

	@Override
	public HashMap<String, String> createAdditionalInfo(Element cal) {
		HashMap<String,String> additionalInfo = new HashMap<String,String>();
		return additionalInfo;
	}

}
