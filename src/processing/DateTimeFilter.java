package processing;

import general.Event;
import java.util.List;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeFilter extends Filter {
            
    public DateTimeFilter(List<Event> calendarEvents) {
        super(calendarEvents);
    }
    
    /**
     * 
     * @param startDate and endDate are formatted as "mm/dd/yyyy"
     */
    public List<Event> filter(String...dates) {
        DateTimeFormatter format = DateTimeFormat.forPattern("MM/dd/yyyy");
        DateTime startDateTime = format.parseDateTime(dates[0]);
        DateTime endDateTime = format.parseDateTime(dates[1]);
        
        List<Event> filteredEvents = new ArrayList<Event>();
        
        for (Event event: getCalendarEvents()) {
            DateTime eventTime = event.getDateTime();
            if (eventTime.isAfter(startDateTime) && eventTime.isBefore(endDateTime) || eventTime.toLocalDate().equals(endDateTime.toLocalDate())) {
                filteredEvents.add(event);
            }
        }
        
        return filteredEvents;
    }
    
}
