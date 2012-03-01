package processing;

import general.Event;
import java.util.ArrayList;
import java.util.List;


public class ExcludeKeywordsFilter extends Filter {

    public ExcludeKeywordsFilter(List<Event> calendarEvents) {
        super(calendarEvents);
    }
    
    public List<Event> filter(String...keywords) {
        List<Event> filteredEvents = new ArrayList<Event>();
        boolean noKeywords;
        
        for (Event event: getCalendarEvents()) {
            noKeywords = true;
            
            for (String word: keywords) {
                if (event.toString().contains(word.toLowerCase())) {
                    noKeywords = false;
                    break;
                } 
            }
            
            if (noKeywords == true) {
                filteredEvents.add(event);
            }
            
        }
        return filteredEvents;
    }
    
}
