package processing;


import general.Event;

import java.util.Comparator;



public class EndTimeComparator implements Comparator<Event> {

    public int compare(Event left, Event right) {
        return left.getEndTime().compareTo(right.getEndTime());
    }
}
