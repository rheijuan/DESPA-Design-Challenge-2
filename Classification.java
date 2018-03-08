package sample;

public enum Classification {

    Task, Event;

    public String toString() {
        switch(this) {
            case Task: return "Task";
            case Event: return "Event";
            default: return "Event";
        }
    }

    public static Classification toClassification(String classification) {
        switch(classification.toLowerCase()) {
            case "task": return Task;
            case "event": return Event;
            default: return Event;
        }
    }
}
