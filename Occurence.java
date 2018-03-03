package model;

import java.util.Calendar;

public class Occurence {

    public Occurence(int year, int month, int dayOfMonth, String name, Color color,  Classification classification) {
        date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month-1);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        this.name = name;
        this.color = color;
        this.classification = classification;
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
                + " - " + name + " - " + color.toString() + "- " + classification.toString();
    }

    private Calendar date;
    private String name;
    private Color color;
    private Classification classification;
}
