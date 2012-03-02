package GUI;

import general.*;
import processing.*;
import output.HTMLWriter;
import input.CalendarParser;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.jaxen.JaxenException;
import org.jdom.JDOMException;

import output.ListWriter;

public class Model {

	private List<Event> calendarEvents;
	private List<File> XMLThatNeedToBeParsed;
	private Map<String, Comparator<Event>> sortMap;

	public Model() {
		XMLThatNeedToBeParsed = new ArrayList<File>();
		calendarEvents = new ArrayList<Event>();
		sortMap = new HashMap<String, Comparator<Event>>();
		makeMap();
	}

	private void makeMap() {
		sortMap.put("title", new TitleComparator());
		sortMap.put("start time", new StartTimeComparator());
		sortMap.put("end time", new EndTimeComparator());

	}

	public void parse() throws JaxenException, JDOMException, IOException {
		for (File file : XMLThatNeedToBeParsed) {
			calendarEvents.addAll(new CalendarParser.Factory()
					.returnListOfEvents(file));
		}
	}

	public void addFile(File file) {
		XMLThatNeedToBeParsed.add(file);
	}

	public void sortstartTime() {
		Comparator<Event> comp = new StartTimeComparator();
		Collections.sort(calendarEvents, comp);
	}

	public void sortendTime() {
		Comparator<Event> comp = new EndTimeComparator();
		Collections.sort(calendarEvents, comp);
	}

	public void dateFilter(String[] dates) {
		DateTimeFilter filterType = new DateTimeFilter(calendarEvents);
		calendarEvents = filterType.filter(dates);
	}

	public void includeKeywordsFilter(String[] keywords) {
		IncludeKeywordsFilter filterType = new IncludeKeywordsFilter(
				calendarEvents);
		calendarEvents = filterType.filter(keywords);
	}

	public void excludeKeywordsFilter(String[] keywords) {
		ExcludeKeywordsFilter filterType = new ExcludeKeywordsFilter(
				calendarEvents);
		calendarEvents = filterType.filter(keywords);
	}

	public void titleFilter(String keyword) {
		KeywordTitleFilter filterType = new KeywordTitleFilter(calendarEvents);
		calendarEvents = filterType.filter(keyword);
	}

	public void sortTitle() {
		Comparator<Event> comp = new TitleComparator();
		Collections.sort(calendarEvents, comp);
	}

	public void characteristicFilter(String characteristic) {
		CharacteristicFilter filterType = new CharacteristicFilter(
				calendarEvents);
		calendarEvents = filterType.filter(characteristic);
	}

	public void sortReverseOrder(String sort) {
		Comparator<Event> oc = sortMap.get(sort);
		Comparator<Event> comparator = new ReverseOrderComparator(oc);
		Collections.sort(calendarEvents, comparator);
	}

	public void makeCalendar(String calendarName) {
		HTMLWriter writer = new ListWriter(calendarName, calendarEvents);
		try {
			writer.writeCal();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
