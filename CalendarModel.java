package sample;

public class CalendarModel {

    public CalendarModel() {
        eventlist = new EventList();
    }

    public EventList getEventlist() {
        return eventlist;
    }

    public boolean isValid(Occurrence o) {
        return validTime(o) && noOverlaps(o);
    }

    private boolean validTime(Occurrence o) {
        if(o.getStartHour() >= 0 && o.getStartHour() <= 23 && o.getEndHour() >= 0 && o.getEndHour() <= 23) {
            if(o.getStartHour() < o.getEndHour())
                return true;
            else if(o.getStartHour() == o.getEndHour())
                return o.getStartMin() < o.getEndMin();
        }
        return false;
    }

    private boolean noOverlaps(Occurrence o) {
        for(int i = 0; i < eventlist.getEvents().size(); i++) {
            if(eventlist.getEvents().get(i).getDay().equals(o.getDay())) {
                // If start hour of both events are equal
                if(eventlist.getEvents().get(i).getStartHour() == o.getStartHour())
                    return false;
                    // If the event starts at the end time of an event
                else if(eventlist.getEvents().get(i).getEndHour() == o.getStartHour() &&
                        eventlist.getEvents().get(i).getEndMin() == o.getStartMin())
                    return false;
                    // If the event ends at the start of an event
                else if(eventlist.getEvents().get(i).getStartHour() == o.getEndHour() &&
                        eventlist.getEvents().get(i).getStartMin() == o.getEndMin())
                    return false;
                    // If the start of the event is in between an event
                else if(o.getStartHour() > eventlist.getEvents().get(i).getStartHour() &&
                        o.getStartHour() < eventlist.getEvents().get(i).getEndHour())
                    return false;
                    // If the end time of the event is in between an event
                else if(o.getEndHour() > eventlist.getEvents().get(i).getStartHour() &&
                        o.getEndHour() < eventlist.getEvents().get(i).getEndHour())
                    return false;
            }
        }
        return true;
    }

    private EventList eventlist;
}
