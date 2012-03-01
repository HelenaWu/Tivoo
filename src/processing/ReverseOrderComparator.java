package processing;

import general.Event;

import java.util.Comparator;



public class ReverseOrderComparator implements Comparator<Event> {
    
    private Comparator<Event> comparator;
    
    public ReverseOrderComparator(Comparator<Event> comp)
    {
        comparator = comp;
    }

    public int compare(Event left, Event right)
    {
        return -1 * comparator.compare(left, right);
    }
}
