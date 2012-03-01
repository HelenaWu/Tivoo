package processing;

import general.Event;
import java.util.ArrayList;
import java.util.List;


public class IncludeKeywordsFilter extends Filter {

    public IncludeKeywordsFilter(List<Event> calendarEvents) {
        super(calendarEvents);
    }
    
    public List<Event> filter(String...keywords) {
        List<Event> filteredEvents = new ArrayList<Event>();

        for (Event event: getCalendarEvents()) {
            
            for (String word: keywords) {
                if (event.toString().contains(word.toLowerCase())) {
                    filteredEvents.add(event);
                    break; //if find a keyword, stop searching
                } 
            }
            
        }
        return filteredEvents;
    }
    
}
