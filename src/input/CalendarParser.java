package input;
import general.Event;
import general.Person;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.jaxen.JaxenException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public abstract class CalendarParser {
	private Element myRoot;
	private Map<String,String> myTagMap;

	
	
	public CalendarParser(Element root) throws JDOMException, IOException
	{
		myRoot = root;
		myTagMap = createMap();

	}
	
	protected Element getRoot(){
		return myRoot;
	}
	protected String getRootName(){
		return myTagMap.get("root");
	}
	protected Map<String,String> getTagMap(){
		return myTagMap;
	}


	
	public boolean isKindOfEvent(){
		if (this.getRoot().getName().equals(this.getRootName())) {
			return true;
		}
		return false;
	}
	
	public abstract DateTime parseTime(Element cal, String DateType, String TimeType) throws JaxenException, JDOMException, IOException;
	public abstract Person parseContact(Element cal) throws JDOMException, IOException, JaxenException;
	public abstract Map<String, String> createMap();
	public abstract HashMap<String, String> createAdditionalInfo(Element cal);	
	public abstract String getField(Element cal, String fieldName) throws JaxenException, JDOMException, IOException;
	
	public abstract List<Element> findAllInstancesOfElement(String info) throws JDOMException, JaxenException, IOException; 
	
	
	public List<Event> parseEvent() throws JaxenException, IOException {
		
		try {
			ArrayList<Event> AllEvents = new ArrayList<Event>();

			
			Iterator i = makeIterator(myTagMap.get("event"));
			while (i.hasNext()) {
				Element cal = (Element) i.next();
				HashMap<String,String> additionalInfo = createAdditionalInfo(cal);
				Person contact = this.parseContact(cal);
				DateTime dateTime1 = this.parseTime(cal, myTagMap.get("startDate"), myTagMap.get("startTime"));
				DateTime dateTime2 = this.parseTime(cal, myTagMap.get("endDate"), myTagMap.get("endTime"));
				String summary = "n/a";

				Event calEvent = new Event(
						getField(cal,"title"),
						summary,
						getField(cal,"description"),
						contact,
						dateTime1,
						dateTime2,
						getField(cal,"link"),
						getField(cal,"cost"),
						additionalInfo
						);		

				AllEvents.add(calEvent);

			}
			return AllEvents;

		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return null;
	}	
	

	

	public DateTime createTime(String dateString, String pattern){
		
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		DateTime dateTime = format.parseDateTime(dateString);
		return dateTime;
	}
	
	public Iterator<Element> makeIterator(String tag) throws JDOMException, JaxenException, IOException {
		
		List<Element> calendars = findAllInstancesOfElement(tag);
		return calendars.iterator();
		
	}

	public static class Factory {
		private ArrayList<CalendarParser> eventTypes;

		private void constructCalendarTypes(Element root) throws JDOMException, IOException

		{

			eventTypes = new ArrayList<CalendarParser>();
			eventTypes.add(new DukeCalendarParser(root));
			eventTypes.add(new DukeBasketBallParser(root));
			eventTypes.add(new NFLParser(root));
			eventTypes.add(new GoogleCalParser(root));
			eventTypes.add(new TVCalendarParser(root));

		}

		public Element getRoot(File xmlFile) throws IOException, JaxenException, JDOMException{
			
			
			SAXBuilder builder = new SAXBuilder();
			Document doc;
			try {
				builder.build(xmlFile);
				doc = (Document) builder.build(xmlFile);
				return doc.getRootElement();
	
			} catch (JDOMException e) {
				throw e;
			}
			
		}

		
		public List<Event> returnListOfEvents(File xmlFile) throws JDOMException, IOException, JaxenException{
			CalendarParser.Factory parserChooser = new CalendarParser.Factory();
			CalendarParser parser = parserChooser.createCalendarParser(getRoot(xmlFile));
			List<Event> CalendarEvents = parser.parseEvent();
			return CalendarEvents;
		}
		
		public CalendarParser createCalendarParser(Element root)
				throws JDOMException, IOException

		{
			constructCalendarTypes(root);
			for (CalendarParser type : eventTypes) {

				if (type.isKindOfEvent()) {
					return type;
					
				}

			}
			return null;

		}
	}



}