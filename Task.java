package sample;

public class Task extends Occurrence {

    public Task(int year, int month, int dayOfMonth, String name, String color, Classification classification, int startHour, int startMin, int endHour, int endMin) {
        super(year, month, dayOfMonth, name, color, classification, startHour, startMin, endHour, endMin);
        setEndTime(endHour, endMin);
        this.marked = false;
    }

    @Override
    public void setEndTime(int endHour, int endMin) {
        this.timeEnd = endHour + ":" + endMin;
    }

    public void markTask() {
        this.marked = true;
    }

    public boolean isMarked() {
        return marked;
    }

    private boolean marked;
}
