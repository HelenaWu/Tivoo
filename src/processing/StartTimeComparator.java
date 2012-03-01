package processing;



import general.Event;

import java.util.Comparator;



public class StartTimeComparator implements Comparator<Event> {
    
    public int compare(Event left, Event right) {
        return left.getDateTime().compareTo(right.getDateTime());
    }
}
