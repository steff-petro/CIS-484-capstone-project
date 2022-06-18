/*
This is the Drives class
 */
package capstoneProject;

// Imports

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.pool.OracleDataSource;

public class Drives {

    // Class variables
    private String driveId;
    private String volunteerId;
    private String locationId;
    private double miles;
    private String driveDate;
    private String driveNotes;

    // Default constructor
    public Drives() {
        this.driveId = "Default Drive ID";
        this.volunteerId = "Default Volunteer ID";
        this.locationId = "Default Location ID";
        this.miles = 000;
        this.driveDate = "Default Drive Date";
        this.driveNotes = "Default Drive Notes";
    }

    // Overloaded constructor
    public Drives(String driveId, String volunteerId, String locationId, double miles, String driveDate, String driveNotes) {
        setDriveId(driveId);
        setVolunteerId(volunteerId);
        setLocationId(locationId);
        setMiles(miles);
        setDriveDate(driveDate);
        setDriveNotes(driveNotes);
    }

    // @return the driveId
    public String getDriveId() {
        return driveId;
    }

    // @param driveId the driveId to set
    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }

    // @return the volunteerId
    public String getVolunteerId() {
        return volunteerId;
    }

    // @param volunteerId the volunteerId to set
    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    // @return the locationId
    public String getLocationId() {
        return locationId;
    }

    // @param locationId the locationId to set
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    // @return the miles
    public double getMiles() {
        return miles;
    }

    // @param miles the miles to set
    public void setMiles(double miles) {
        this.miles = miles;
    }

    // @return the driveDate
    public String getDriveDate() {
        return driveDate;
    }

    // @param driveDate the driveDate to set
    public void setDriveDate(String driveDate) {
        this.driveDate = driveDate;
    }

    // @return the driveNotes
    public String getDriveNotes() {
        return driveNotes;
    }

    // @param driveNotes the driveNotes to set
    public void setDriveNotes(String driveNotes) {
        this.driveNotes = driveNotes;
    }
    
    public void writeDrives() {
        String insertDrives = "INSERT INTO Drives VALUES (";
        insertDrives += "'" + this.getDriveId() + "',";
        insertDrives += "'" + this.getVolunteerId() + "',";
        insertDrives += "'" + this.getLocationId() + "',";
        insertDrives += " " + this.getMiles()+ ",";
        insertDrives += "'" + this.getDriveDate()+ "',";
        insertDrives += "'" + this.getDriveNotes() + "')";
        sendDBCommand(insertDrives);
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
