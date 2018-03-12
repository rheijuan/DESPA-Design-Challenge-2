package sample;

import java.util.ArrayList;

public interface DataParser {

    void readData();

    void appendEvent(Occurrence event);

    void reWriteData(ArrayList<Occurrence> events);

    ArrayList<Occurrence> getEvents();

}
