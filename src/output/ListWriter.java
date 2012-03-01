package output;



import general.Event;

import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;



import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Link;
import com.hp.gagawa.java.elements.P;
import com.hp.gagawa.java.elements.Text;
import com.hp.gagawa.java.elements.Title;


public class ListWriter extends HTMLWriter {
	
	public ListWriter(String title, List<Event> events) {
		super(title,events);
	}
	
	protected Div makeDay(String datestr) {
		Div day = new Div().setId("day");
		Div date = new Div().setId("date");
		P text = new P().appendChild(new Text(datestr));
		date.appendChild(text);
		day.appendChild(date);
		return day;
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
		if(getEvents().size() == 0 )
			wrapper.appendChild(new Text("No events found"));
		
		DateTime currentDate = new DateTime();
		Div day = new Div();
		for (Event event : getEvents()) {
//			if (!event.getDateTime().toString(datePattern)
//					.equals(currentDate.toString(datePattern))) {
				currentDate = event.getDateTime();
				day = makeDay(currentDate.toString(datePattern));
				wrapper.appendChild(day);
//			}
			String eventPage = writeEventPage(event);
			day.appendChild(writeEvent(event, eventPage));

			day.appendChild(new Br());
		}

		container.appendChild(makeFooter());

		// now write to file
		String file = makeFileName("index");
		writeFile(file, document);

	}
}
