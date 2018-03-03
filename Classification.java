package model;

public enum Classification {

    Holiday, Event;

    public String toString() {
        switch(this) {
            case Holiday: return "Holiday";
            case Event: return "Event";
            default: return "Event";
        }
    }

    public static Classification toClassification(String classification) {
        switch(classification.toLowerCase()) {
            case "holiday": return Holiday;
            case "event": return Event;
            default: return Event;
        }
    }
}
