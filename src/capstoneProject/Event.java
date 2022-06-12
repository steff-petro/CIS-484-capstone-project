/*
This is the Event class
 */
package capstoneProject;

// Imports
public class Event {

    // Class variables
    String eventID;
    String eventName;
    String eventDate;
    String eventTime;
    int maxVolunteers;
    int spotsLeft;
    String eventDescription;
    String locationID;
    int registeredVolunteers;
    
    static int eventCount = 0;
    
    public Event(String eventID, String eventName, String eventDate, String eventTime, int maxVolunteers, String eventDescription, String locationID, int registeredVolunteers) {
        this.eventID = "event" + eventCount;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.maxVolunteers = maxVolunteers;
        this.eventDescription = eventDescription;
        this.locationID = locationID;
        
        this.spotsLeft = maxVolunteers - registeredVolunteers;
        
        eventCount++;
    }
    
    public String getEventID() {
        return this.eventID;
    }
    
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
    
    public String getEventName() {
        return this.eventName;
    }
    
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
    public String getEventDate() {
        return this.eventDate;
    }
    
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    
    public String getEventTime() {
        return this.eventTime;
    }
    
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
    
    public int getMaxVolunteers() {
        return this.maxVolunteers;
    }
    
    public void setMaxVolunteers(int maxVolunteers) {
        this.maxVolunteers = maxVolunteers;
    }
    
    public String getEventDescription() {
        return this.eventDescription;
    }
    
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    
    public String getLocationID() {
        return this.locationID;
    }
    
    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }
    
    public int getSpotsLeft() {
        return this.spotsLeft;
    }
    
    public void setSpotsLeft(int spotsLeft) {
        this.spotsLeft = spotsLeft;
    }
}
