package sample;

public class Task extends Occurrence {

    public Task(int year, int month, int dayOfMonth, String name, Color color, Classification classification, int startHour, int startMin, int endHour, int endMin) {
        super(year, month, dayOfMonth, name, color, classification, startHour, startMin, endHour, endMin);
        setEndTime(endHour, endMin);
        this.marked = false;
    }

    @Override
    public void setEndTime(int endHour, int endMin) {
        if(endMin + 30 == 60)
            this.timeEnd = (endHour + 1) + ":" + 00;
        else
            this.timeEnd = endHour + ":" + (endMin + 30);
    }

    public void markTask() {
        this.marked = true;
    }

    private boolean marked;
}
