package general;
import input.CalendarParser;


import java.io.File;
import java.io.IOException;
import java.util.*;
import org.jaxen.JaxenException;
import org.jdom.JDOMException;

import output.HTMLWriter;
import output.ListWriter;
import processing.*;



public class TivooSystem {
	public static void run() throws JaxenException {
		try {       
//			File file1 = new File("DukeBasketBall.xml");
//			File file2 = new File("dukecal.xml");
//			File file3 = new File("googlecal.xml");
//			File file4 = new File("NFL.xml");
			File file5 = new File("tvshort.xml");
			ArrayList<File> XMLs = new ArrayList<File>();
//			XMLs.add(file1);
//			XMLs.add(file2);
//			XMLs.add(file3);
//			XMLs.add(file4);
			XMLs.add(file5);
			List<Event> calendarEvents = new ArrayList<Event>();
			
			for(File xmlFile: XMLs){
				calendarEvents.addAll(new CalendarParser.Factory().returnListOfEvents(xmlFile));
			}

//			DateTimeFilter filter1 = new DateTimeFilter(calendarEvents);
//			calendarEvents = filter1.filter("11/11/2011", "1/19/2012");
			
//			IncludeKeywordsFilter filter2 = new IncludeKeywordsFilter(calendarEvents);
//			calendarEvents = filter2.filter("Belmont", "Michigan", "ESPNU");
			
			//CharacteristicFilter filter3 = new CharacteristicFilter(calendarEvents);
			//calendarEvents = filter3.filter("title");
			
//			ExcludeKeywordsFilter filter4 = new ExcludeKeywordsFilter(calendarEvents);
//			calendarEvents = filter4.filter("Michigan", "ACC Network");
			
//			KeywordTitleFilter filter5 = new KeywordTitleFilter(calendarEvents);
//			calendarEvents = filter5.filter("Duke");
	         
            //Sort by title
//            Comparator<Event> comp = new TitleComparator();
//            Collections.sort(calendarEvents, comp);
            
            //Sort by start time
            //Comparator<Event> comp2 = new StartTimeComparator();
            //Collections.sort(calendarEvents, comp2);
			
			//Sort by end time
			//Comparator<Event> comp3 = new EndTimeComparator();
	        //Collections.sort(calendarEvents, comp3);
            
            //Reverse Order
            //Comparator<Event> comp4 = new ReverseOrderComparator(new TitleComparator());
            //Collections.sort(calendarEvents, comp4);
			
//			HTMLWriter writer = new MonthWriter("DukeCal", calendarEvents, "11/11/2011", "12/11/2012");
			HTMLWriter writer = new ListWriter("DukeCal", calendarEvents);
//			HTMLWriter writer = new WeekWriter("DukeCal", calendarEvents, "11/11/2011");
//			HTMLWriter writer = new DayWriter("DukeCal", calendarEvents);
			writer.writeCal();

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}