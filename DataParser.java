package sample;

import java.util.ArrayList;

public interface DataParser {

    void readData();

    void appendEvent(Event event);

    void reWriteData(ArrayList<Event> events);

    ArrayList<Event> getEvents();

}
