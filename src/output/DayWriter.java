package output;

import general.Event;

import java.io.IOException;
import java.util.List;

import org.joda.time.*;


import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.H1;
import com.hp.gagawa.java.elements.H2;
import com.hp.gagawa.java.elements.Link;
import com.hp.gagawa.java.elements.P;
import com.hp.gagawa.java.elements.Text;
import com.hp.gagawa.java.elements.Title;

public class DayWriter extends HTMLWriter {

	public DayWriter(String title, List<Event> events) {
		super(title, events);
	}

	protected Div makeHeader() {
		Div header = new Div().setId("header");
		H1 title = new H1();
		title.appendChild(new Text(getTitle()));
		H2 date = new H2();
		if (getEvents().size() > 0) {
			String currentDay = getEvents().get(0).getDateTime().toString(datePattern);
			date.appendChild(new Text(currentDay));
		}
		header.appendChild(title);
		header.appendChild(date);
		return header;
	}
	
//	protected Div writeEvent(Event event, String pageLoc) {
//		Div ev = new Div().setId("eventsm");
//		A link = new A().setHref(pageLoc);
//		link.appendChild(new Text(event.getTitle()));
//
//		ev.appendChild(link);
//		
//		String time = makeTime(event.getDateTime());
//		ev.appendChild(new Text(" ("+time+" - "));
//		time= makeTime(event.getEndTime());
//		ev.appendChild(new Text(time+")"));
//		
//		return ev;
//	}
	
	private boolean inHour(int hour, DateTime time) {
		String hourOfEvent = time.toString("HH");
		String givenHour = Integer.toString(hour);
		if (givenHour.length() == 1)
			givenHour = "0"+givenHour;
		
		return hourOfEvent.equals(givenHour);
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
		container.appendChild(wrapper);
		Div day = new Div().setId("day");
		wrapper.appendChild(day); // from here on, all of the body will be
								  // within the day;

		// Start to write events
		for (int time = 0; time <= 24; time++) { //Loop through the hours
			Div hour = new Div().setId("date");
			hour.appendChild(new P().appendChild(new Text(Integer.toString(time)+":00")));
			if (getEvents().size() > 0) {
				for(Event event:getEvents()) {
					if (inHour(time, event.getDateTime())) {
						String eventPage = writeEventPage(event);
						hour.appendChild(writeEventSmall(event, eventPage));
					}
				}
			}
			day.appendChild(hour);
		}

		container.appendChild(makeFooter());

		// now write to file
		String file = makeFileName("index");
		writeFile(file, document);

	}
	
}
