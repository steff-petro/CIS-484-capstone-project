/*
This is the Event class
 */
package capstoneProject;

// Imports

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.pool.OracleDataSource;

public class Event {

    // Class variables
    String eventID;
    String eventName;
    String eventDate;
    int eventTime;
    int maxVolunteers;
    int spotsLeft;
    String eventDescription;
    String locationID;
    
    int registeredVolunteers = 0;
    
    static int eventCount = 0;
    
    public Event(String eventID, String eventName, String eventDate, int eventTime, int maxVolunteers, String eventDescription, String locationID) {
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
    
    public int getEventTime() {
        return this.eventTime;
    }
    
    public void setEventTime(int eventTime) {
        this.eventTime = eventTime;
    }
    
    public int getMaxVolunteers() {
        return this.maxVolunteers;
    }
    
    public void setMaxVolunteers(int maxVolunteers) {
        this.maxVolunteers = maxVolunteers;
    }
    
    public int getRegisteredVolunteers() {
        return this.registeredVolunteers;
    }
    
    public void setRegisteredVolunteers(int registeredVolunteers) {
        this.registeredVolunteers = registeredVolunteers;
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
    
    public void writeEvent() {
        String insertEvent = "INSERT INTO Event VALUES (";
        insertEvent += "'" + this.getEventID() + "',";
        insertEvent += "'" + this.getEventName() + "',";
        insertEvent += " " + this.getMaxVolunteers() + ",";
        insertEvent += " " + this.getRegisteredVolunteers() + ",";
        insertEvent += " " + this.getSpotsLeft() + ",";
        insertEvent += "'" + this.getEventTime() + "',";
        insertEvent += "'" + this.getEventDate() + "',";
        insertEvent += "'" + this.getEventDescription() + "',";
        insertEvent += "'" + this.getLocationID() + "')";
        sendDBCommand(insertEvent);
    }

    public void sendDBCommand(String sqlQuery) {
        Connection dbConn;
        Statement commStmt;
        ResultSet dbResults;
        // Set up connection strings
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        String userID = "javauser"; // Change to YOUR Oracle username
        String userPASS = "javapass"; // Change to YOUR Oracle password
        OracleDataSource ds;

        // Print each query to check SQL syntax sent to this method.
        System.out.println(sqlQuery);

        // Try to connect
        try {
            ds = new OracleDataSource();
            ds.setURL(URL);
            dbConn = ds.getConnection(userID, userPASS);
            commStmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbResults = commStmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
}
