package output;

import general.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.H3;
import com.hp.gagawa.java.elements.Link;
import com.hp.gagawa.java.elements.Text;
import com.hp.gagawa.java.elements.Title;


public class WeekWriter extends HTMLWriter {
	protected DateTime currentDate;

	public WeekWriter(String title, List<Event> events, String startDate) {
		super(title, events);
		DateTimeFormatter format = DateTimeFormat.forPattern("MM/dd/yyyy");
		currentDate = format.parseDateTime(startDate);
	}
	
	private Div makeWeekday(int day, DateTime date) {
		Div weekday = new Div().setId("weekday"+day);
		weekday.appendChild(new H3().appendChild(new Text(date.toString(datePattern))));
		return weekday;
	}
	
	protected int howMany(DateTime current, DateTime second) {
		if (current.toString("MM/dd").equals(second.toString("MM/dd"))) {
			return 0;
		}
		current = current.plusDays(1);
		return 1+howMany(current,second);
	}

	public void writeCal() throws IOException {
		Document document = new Document(DocumentType.XHTMLTransitional);
		Link link = new Link();
		link.setRel("stylesheet");
		link.setType("text/css");
		link.setHref("styles.css");
		document.head.appendChild(new Title().appendChild(new Text(getTitle())));
		document.head.appendChild(link);
		Body body = document.body;
		Div container = new Div().setId("container");
		body.appendChild(container);
		container.appendChild(makeHeader());
		Div wrapper = new Div().setId("wrapper");
		container.appendChild(wrapper); // from here on, all of the body will be
										// within the wrapper;

		// Start to write events
		
		wrapper = writeWeek(wrapper);

		container.appendChild(makeFooter());

		// now write to file
		String file = makeFileName("index");
		writeFile(file, document);

	}

	protected Div writeWeek(Div wrapper) throws IOException {
		int currentDay = 1;
		Div weekday = makeWeekday(currentDay,currentDate);
		wrapper.appendChild(weekday);
		ArrayList<Event> usedEvents = new ArrayList<Event>();
		writeEvents: for (Event event : getEvents()) {
			while (howMany(event.getDateTime(),currentDate) != 0) {
				currentDate = currentDate.plusDays(1);
				currentDay++;
				if(currentDay > 7) 
					break writeEvents;
				weekday = makeWeekday(currentDay,currentDate);
				wrapper.appendChild(weekday);
			}
			
			String eventPage = writeEventPage(event);
			weekday.appendChild(writeEventSmall(event, eventPage));
			usedEvents.add(event);

			weekday.appendChild(new Br());
		}
		deleteEvents(usedEvents);
		while(currentDay < 7) { //finish week, if necessary	
			currentDate = currentDate.plusDays(1);
			currentDay++;
			weekday = makeWeekday(currentDay,currentDate);
			wrapper.appendChild(weekday);
		}
		return wrapper;
	}
	
}
