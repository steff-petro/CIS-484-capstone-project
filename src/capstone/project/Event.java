/*
This is the Event class
 */
package capstone.project;

// Imports
public class Event {

    // Class variables
    private String eventId;
    private String eventName;
    private int maxVolunteers;
    private int spotsLeft;
    private double time;
    private String eventDate;
    private String eventDescription;
    private String locationId;

    // Default constructor
    public Event() {
        this.eventId = "Default Event ID";
        this.eventName = "Default Event Name";
        this.maxVolunteers = 00;
        this.spotsLeft = 00;
        this.time = 00.00;
        this.eventDate = "Event Date";
        this.eventDescription = "Event Description";
        this.locationId = "Default Location ID";
    }

    // Overloaded constructor
    public Event(String eventId, String eventName, int maxVolunteers, int spotsLeft, double time, String eventDate, String eventDescription, String locationId) {
        setEventId(eventId);
        setEventName(eventName);
        setMaxVolunteers(maxVolunteers);
        setSpotsLeft(spotsLeft);
        setTime(time);
        setEventDate(eventDate);
        setEventDescription(eventDescription);
        setLocationId(locationId);
    }

    // @return the eventId
    public String getEventId() {
        return eventId;
    }

    // @param eventId the eventId to set
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    // @return the eventName
    public String getEventName() {
        return eventName;
    }

    // @param eventName the eventName to set
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    // @return the maxVolunteers
    public int getMaxVolunteers() {
        return maxVolunteers;
    }

    // @param maxVolunteers the maxVolunteers to set
    public void setMaxVolunteers(int maxVolunteers) {
        this.maxVolunteers = maxVolunteers;
    }

    // @return the spotsLeft
    public int getSpotsLeft() {
        return spotsLeft;
    }

    // @param spotsLeft the spotsLeft to set
    public void setSpotsLeft(int spotsLeft) {
        this.spotsLeft = spotsLeft;
    }

    // @return the time
    public double getTime() {
        return time;
    }

    // @param time the time to set
    public void setTime(double time) {
        this.time = time;
    }

    // @return the eventDate
    public String getEventDate() {
        return eventDate;
    }

    // @param eventDate the eventDate to set
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    // @return the eventDescription
    public String getEventDescription() {
        return eventDescription;
    }

    // @param eventDescription the eventDescription to set
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    // @return the locationId
    public String getLocationId() {
        return locationId;
    }

    // @param locationId the locationId to set
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
