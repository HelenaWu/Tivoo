package processing;

import general.Event;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CharacteristicFilter extends Filter {

    public CharacteristicFilter(List<Event> calendarEvents) {
        super(calendarEvents);
    }
    
    public List<Event> filter(String...characteristic) {
        List<Event> filteredEvents = new ArrayList<Event>();
        String tag = characteristic[0];
        
        for (Event event: getCalendarEvents()) {
            HashMap<String, String> uniqueInfo = event.getAdditionalInfo();
            if (uniqueInfo.keySet().contains(tag)) {
                if (uniqueInfo.get(tag) != null) {
                    filteredEvents.add(event);
                }
            }
        }
        
        return filteredEvents;
    }
    
}
