package sample;

import javafx.beans.property.SimpleStringProperty;

public class TimeSlot {

    public TimeSlot(String time, String name, String classification) {
        this.time = new SimpleStringProperty(time);
        this.name = new SimpleStringProperty(name);
        this.classification = classification;
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    private SimpleStringProperty time;
    private SimpleStringProperty name;
    private String classification;
}
