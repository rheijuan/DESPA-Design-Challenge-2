package sample;

public class Event extends Occurrence {

    public Event(int year, int month, int dayOfMonth, String name, Color color, Classification classification, int startHour, int startMin, int endHour, int endMin) {
        super(year, month, dayOfMonth, name, color, classification, startHour, startMin, endHour, endMin);
        setEndTime(endHour, endMin);
    }

    @Override
    public void setEndTime(int endHour, int endMin) {
        this.timeEnd = endHour + ":" + endMin;
    }
}
