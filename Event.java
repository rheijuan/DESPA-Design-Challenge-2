package sample;

public class Event extends Occurrence {

    public Event(int year, int month, int dayOfMonth, String name, String color, Classification classification, int startHour, int startMin, int endHour, int endMin) {
        super(year, month, dayOfMonth, name, color, classification, startHour, startMin, endHour, endMin);
        setEndTime(endHour, endMin);
        this.expired = false;
    }

    @Override
    public void setEndTime(int endHour, int endMin) {
        this.timeEnd = endHour + ":" + endMin;
    }

    public void eventExpired() {
        this.expired = true;
    }

    private boolean expired;
}
