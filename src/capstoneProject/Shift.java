/*
This is the Shift class
 */
package capstoneProject;

// Imports

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.pool.OracleDataSource;

public class Shift {

    // Class variables
    private String shiftID;
    private int clockIn;
    private int clockOut; // Should probably add a total time in system variable to track the total
    private String volunteerID;
    
    static int shiftCount;
    
    // Default constructor
    public Shift() {
        this.shiftID = "Default Shift ID";
        this.clockIn = 00;
        this.clockOut = 00;
        this.volunteerID = "Default Volunteer ID";
    }

    // Overloaded constructor
    public Shift(String shiftID, int clockIn, int clockOut, String volunteerID) {
        this.shiftID = "shift" + shiftCount;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.volunteerID = volunteerID;
        
        shiftCount++;
    }

    // @return the shiftID
    public String getShiftID() {
        return shiftID;
    }

    // @param shiftID the shiftID to set
    public void setShiftID(String shiftID) {
        this.shiftID = shiftID;
    }

    // @return the clockIn
    public double getClockIn() {
        return clockIn;
    }

    // @param clockIn the clockIn to set
    public void setClockIn(int clockIn) {
        this.clockIn = clockIn;
    }

    // @return the clockOut
    public double getClockOut() {
        return clockOut;
    }

    // @param clockOut the clockOut to set
    public void setClockOut(int clockOut) {
        this.clockOut = clockOut;
    }

    // @return the volunteerId
    public String getVolunteerID() {
        return volunteerID;
    }

    // @param volunteerId the volunteerId to set
    public void setVolunteerID(String volunteerID) {
        this.volunteerID = volunteerID;
    }
    
    public void writeShift() {
        String insertShift = "INSERT INTO Shift VALUES (";
        insertShift += "'" + this.getShiftID() + "',";
        insertShift += " " + this.getClockIn()+ ",";
        insertShift += " " + this.getClockOut() + ",";
        insertShift += "'" + this.getVolunteerID()+ "')";
        sendDBCommand(insertShift);
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
