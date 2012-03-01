package processing;

import general.Event;
import java.util.List;
import java.util.ArrayList;


public class KeywordTitleFilter extends Filter {
        
    public KeywordTitleFilter(List<Event> calendarEvents) {
        super(calendarEvents);
    }

    public List<Event> filter(String...keyword) {
        List<Event> filteredEvents = new ArrayList<Event>();
        
        for (Event event: getCalendarEvents()) {
            if (event.getTitle().contains(keyword[0]))
                filteredEvents.add(event);
        }
        
        return filteredEvents;
    }
    
}
