package sample;

import javafx.beans.property.SimpleStringProperty;

public class TimeSlot {

    public TimeSlot(String time, String event) {
        this.time = new SimpleStringProperty(time);
        this.event = new SimpleStringProperty(event);
    }

    public String getTime() {
        return time.get();
    }

    public String getEvent() {
        return event.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public SimpleStringProperty eventProperty() {
        return event;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public void setEvent(String event) {
        this.event.set(event);
    }

    private SimpleStringProperty time;
    private SimpleStringProperty event;
}
