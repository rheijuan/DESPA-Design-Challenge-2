package sample;

import java.util.ArrayList;

public class EventList {

    public EventList() {
        events = new ArrayList<>();
    }

    public void addOccurrence(Occurrence r) {
        events.add(r);
    }

    public ArrayList<Occurrence> getEvents() {
        return this.events;
    }

    private ArrayList<Occurrence> events;
}
