package sample;

public class CalendarModel {

    CalendarModel() {
        eventlist = new EventList();
        csvParser = new CSVParser();
        psvParser = new PSVParser();

        readData();
    }

    public boolean isValid(Occurrence o) {
        return validTime(o) && noOverlaps(o);
    }

    private boolean validTime(Occurrence o) {
        if(o.getStartHour() >= 0 && o.getStartHour() <= 23 && o.getEndHour() >= 0 && o.getEndHour() <= 23) {
            if(o.getStartHour() < o.getEndHour())
                return true;
            else if(o.getStartHour() == o.getEndHour())
                return o.getStartMin() <= o.getEndMin();
        }
        return true;
    }

    private boolean noOverlaps(Occurrence o) {
        for(Occurrence occ : eventlist.getEvents()) {
            if(occ.getDay().equals(o.getDay())) {
                // If start hour of both events are equal
                if(occ.getStartHour() == o.getStartHour())
                    return false;

                // If the event starts at the end time of an event
                else if(occ.getEndHour() == o.getStartHour() &&
                        occ.getEndMin() == o.getStartMin())
                    return false;

                // If the event ends at the start of an event
                else if(occ.getStartHour() == o.getEndHour() &&
                        occ.getStartMin() == o.getEndMin())
                    return false;

                // If the start of the event is in between an event
                else if(o.getStartHour() < occ.getStartHour() &&
                        o.getEndHour() > occ.getEndHour())
                    return false;

            }
        }
        return true;
    }

    private void readData() {
        csvParser.readData();
        psvParser.readData();

        for(int i = 0; i < csvParser.getEvents().size(); i++)
            eventlist.getEvents().add(csvParser.getEvents().get(i));

        for(int i = 0; i < psvParser.getEvents().size(); i++)
            eventlist.getEvents().add(psvParser.getEvents().get(i));
    }

    public EventList getEventlist() {
        return eventlist;
    }

    public CSVParser getCsvParser() {
        return csvParser;
    }

    public PSVParser getPsvParser() {
        return psvParser;
    }

    private EventList eventlist;
    private CSVParser csvParser;
    private PSVParser psvParser;
}
