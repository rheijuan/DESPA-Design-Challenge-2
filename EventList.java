package sample;

import java.util.ArrayList;

public class EventList {

    public EventList() {
        events = new ArrayList<>();
    }

    public void addOccurrence(Occurrence r) {
        events.add(r);
    }

    public void removeOccurrence(String name) {
        for(Occurrence o : events) {
            if (o instanceof Task) {
                if (o.getName().equals(name)) {
                    events.remove(o);
                    break;
                }
            }
        }
    }

    public void markOccurrence(String name) {
        for(Occurrence o : events) {
            if(o instanceof Task) {
                if(o.getName().equals(name)) {
                    ((Task) o).markTask();
                    break;
                }
            }
        }
    }

    public ArrayList<Occurrence> getEvents() {
        return this.events;
    }

    private ArrayList<Occurrence> events;
}
