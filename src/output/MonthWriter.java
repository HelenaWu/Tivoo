package output;

import general.Event;

import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Link;
import com.hp.gagawa.java.elements.Text;
import com.hp.gagawa.java.elements.Title;


public class MonthWriter extends WeekWriter {
	private DateTime endDate;
	
	public MonthWriter(String title, List<Event> events, String startDate, String enddate) {
		super(title, events, startDate);
		DateTimeFormatter format = DateTimeFormat.forPattern("MM/dd/yyyy");
		endDate = format.parseDateTime(enddate);
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
		int numDays = howMany(currentDate,endDate);
		while(numDays>0) {
			Div week = new Div().setId("week");
			week = writeWeek(week);
			wrapper.appendChild(week);
			numDays -= 7;
			currentDate.plusDays(1);
		}
		container.appendChild(makeFooter());

		// now write to file
		String file = makeFileName("index");
		writeFile(file, document);

	}
	
}
