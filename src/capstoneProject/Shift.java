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
    private String shiftId;
    private double clockIn;
    private double clockOut; // Should probably add a total time in system variable to track the total
    private String volunteerId;
    
    // Default constructor
    public Shift() {
        this.shiftId = "Default Shift ID";
        this.clockIn = 00.00;
        this.clockOut = 00.00;
        this.volunteerId = "Default Volunteer ID";
    }

    // Overloaded constructor
    public Shift(String shiftId, double clockIn, double clockOut, String volunteerId) {
        setShiftId(shiftId);
        setClockIn(clockIn);
        setClockOut(clockOut);
        setVolunteerId(volunteerId);
    }

    // @return the shiftId
    public String getShiftId() {
        return shiftId;
    }

    // @param shiftId the shiftId to set
    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    // @return the clockIn
    public double getClockIn() {
        return clockIn;
    }

    // @param clockIn the clockIn to set
    public void setClockIn(double clockIn) {
        this.clockIn = clockIn;
    }

    // @return the clockOut
    public double getClockOut() {
        return clockOut;
    }

    // @param clockOut the clockOut to set
    public void setClockOut(double clockOut) {
        this.clockOut = clockOut;
    }

    // @return the volunteerId
    public String getVolunteerId() {
        return volunteerId;
    }

    // @param volunteerId the volunteerId to set
    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }
    
    public void writeShift() {
        String insertShift = "INSERT INTO Shift VALUES (";
        insertShift += "'" + this.getShiftId() + "',";
        insertShift += " " + this.getClockIn()+ ",";
        insertShift += " " + this.getClockOut() + ",";
        insertShift += "'" + this.getVolunteerId()+ "')";
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
