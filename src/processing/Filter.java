package processing;

import general.Event;

import java.util.List;

public abstract class Filter {
    
    private List<Event> calendarEvents;
    
    public Filter(List<Event> calendarEvents) {
        this.calendarEvents = calendarEvents;
    }
    
    public abstract List<Event> filter(String...filterCriteria);
    
    protected List<Event> getCalendarEvents() {
        return calendarEvents;
    }
    
}
