package output;


import general.Event;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;


import com.hp.gagawa.java.elements.*;

import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;

public abstract class HTMLWriter {
	private String myTitle;
	private List<Event> myEvents;
	public static final String datePattern = "MM/dd/yy";

	public HTMLWriter(String title, List<Event> events) {
		myTitle = title;
		myEvents = events;
	}

	protected Div makeHeader() {
		Div header = new Div().setId("header");
		H1 title = new H1();
		title.appendChild(new Text(myTitle));
		header.appendChild(title);
		return header;
	}

	protected Div makeFooter() {
		Div footer = new Div().setId("footer");
		P words = new P();
		words.appendChild(new Text("Copyright 2012 Group Millman!"));
		footer.appendChild(words);
		return footer;
	}

	protected Div writeEvent(Event event, String pageLoc) {
		Div ev = new Div().setId("event");
		A link = new A().setHref(pageLoc);
		link.appendChild(new Text(event.getTitle()));
		ev.appendChild(new P().appendChild(link));

		String time = makeTime(event.getDateTime());
		ev.appendChild(new P().appendChild(new Text("Start Time: " + time)));

		time = makeTime(event.getEndTime());

		ev.appendChild(new P().appendChild(new Text("End Time: " + time)));
		return ev;
	}

	protected Div writeEventSmall(Event event, String pageLoc) {
		Div ev = new Div().setId("eventsm");
		A link = new A().setHref(pageLoc);
		link.appendChild(new Text(event.getTitle()));

		ev.appendChild(link);

		String time = makeTime(event.getDateTime());
		ev.appendChild(new Text(" (" + time + " - "));
		time = makeTime(event.getEndTime());
		ev.appendChild(new Text(time + ")"));

		return ev;
	}

	public abstract void writeCal() throws IOException;

	protected void writeFile(String filename, Document document) {
		try {
			FileWriter f = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(f);
			out.write(document.write());
			out.close();
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	/*
	 * Write an HTML page with all of the information for the event, then return
	 * its location as a String.
	 */

	public String writeEventPage(Event event) throws IOException {
		Document document = new Document(DocumentType.XHTMLTransitional);
		Link link = new Link();
		link.setRel("stylesheet");
		link.setType("text/css");
		link.setHref("styles.css");
		document.head.appendChild(new Title().appendChild(new Text(myTitle
				+ " -- " + event.getTitle())));
		document.head.appendChild(link);
		Body body = document.body;
		Div container = new Div().setId("container");
		body.appendChild(container);
		container.appendChild(makeHeader());
		Div wrapper = new Div().setId("wrapper");
		container.appendChild(wrapper);
		Div content = new Div().setId("content");
		wrapper.appendChild(content);
		content.appendChild(new H1().appendChild(new Text(event.getTitle()))); // Write
																				// Title

		String time = makeTime(event.getDateTime());

		content.appendChild(new P()
				.appendChild(new Text("Start Time: " + time)));
		time = makeTime(event.getEndTime());

		content.appendChild(new P().appendChild(new Text("End Time: " + time)));
		content.appendChild(new P().appendChild(new Text(event.getDescription())));

		// Add any/all additionalInfo
	
		for (String tag : event.getAdditionalInfo().keySet()) {
			content.appendChild(new P().appendChild(new Text(tag + ": "
					+ event.getAdditionalInfo().get(tag))));
		}
		
		String contactInfo = "Contact: " + event.getContactInfo().getName()
				+ " -- " + event.getContactInfo().getEmail() + " -- "
				+ event.getContactInfo().getPhoneNumber();
		content.appendChild(new P().appendChild(new Text(contactInfo)));

		container.appendChild(makeFooter());

		// now write to file
		String file = makeFileName(event.getTitle(), "events");
		writeFile(file, document);
		return file;
	}

	/*
	 * Creates a valid filename by taking out any invalid characters, and adds
	 * directory information
	 */
	protected String makeFileName(String title, String directory) {
		String file = "";
		if (directory != null)
			file += directory + "/";
		for (int i = 0; i < title.length(); i++) {
			if (Character.isLetter(title.charAt(i))
					|| Character.isDigit(title.charAt(i)))
				file += Character.toString(title.charAt(i));
			else
				file += "_";
		}
		file += ".html";
		return file;
	}

	protected String makeFileName(String title) {
		return makeFileName(title, null);
	}

	protected String makeTime(DateTime time) {
		if (time == null) {
			return "N/A";
		}
		String minutes = Integer.toString(time.getMinuteOfHour());
		if (minutes.length() == 1)
			minutes = "0" + minutes;
		return Integer.toString(time.getHourOfDay()) + ":" + minutes;
	}
	
	protected String getTitle() {
		return myTitle;
	}
	
	protected List<Event> getEvents() {
		return myEvents;
	}
	
	protected void deleteEvents(List<Event> events) {
		myEvents.removeAll(events);
	}
	
}
