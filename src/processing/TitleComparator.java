package processing;



import general.Event;

import java.util.Comparator;



public class TitleComparator implements Comparator<Event> {
    
    public int compare(Event left, Event right) {
        return left.getTitle().compareTo(right.getTitle());
    }
    
}
