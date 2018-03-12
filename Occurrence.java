package sample;

import java.util.Calendar;

public class Occurrence {

    public Occurrence(int year, int month, int dayOfMonth, String name, String color, Classification classification, int startHour, int startMin, int endHour, int endMin) {
        date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month-1);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        this.name = name;
        this.color = color;
        this.classification = classification;
        this.timeStart = startHour + ":" + startMin;
        setEndTime(endHour, endMin);
    }

    public Calendar getDate() {
        return this.date;
    }

    public String getColor() {
        return this.color.toString().toLowerCase();
    }

    public String getName() {
        return name;
    }

    public String getClassification() {
        return this.classification.toString();
    }

    public String toString() {
        return (date.get(Calendar.MONTH)+1) + "/" + date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.YEAR)
                + "-" + name + "-" + color + "-" + classification.toString() + "-" + timeStart + "-" + timeEnd;
    }

    public String getDay() {
        String[] o = toString().split("-");
        return o[0];
    }

    public int getStartHour() {
        String[] time = timeStart.split(":");
        return Integer.parseInt(time[0]);
    }

    public int getStartMin() {
        String[] time = timeStart.split(":");
        return Integer.parseInt(time[1]);
    }

    public int getEndHour() {
        String[] time = timeEnd.split(":");
        return Integer.parseInt(time[0]);
    }

    public int getEndMin() {
        String[] time = timeEnd.split(":");
        return Integer.parseInt(time[1]);
    }

    public void setEndTime(int endHour, int endMin) {
    }

    protected Calendar date;
    protected String name;
    protected String color;
    protected Classification classification;
    protected String timeStart;
    protected String timeEnd;
}
