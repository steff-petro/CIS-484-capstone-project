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
    private String driveID;
    private String volunteerID;
    private String locationID;
    private double miles;
    private String driveDate;
    private String driveNotes;
    
    static int driveCount;

    // Default constructor
    public Drives() {
        this.driveID = "Default Drive ID";
        this.volunteerID = "Default Volunteer ID";
        this.locationID = "Default Location ID";
        this.miles = 0;
        this.driveDate = "Default Drive Date";
        this.driveNotes = "Default Drive Notes";
    }

    // Overloaded constructor
    public Drives(String driveID, String volunteerID, String locationID, double miles, String driveDate, String driveNotes) {
        this.driveID = "drive" + driveCount;
        this.volunteerID = volunteerID;
        this.locationID = locationID;
        this.miles = miles;
        this.driveDate = driveDate;
        this.driveNotes = driveNotes;
        
        driveCount++;
    }

    // @return the driveID
    public String getDriveID() {
        return driveID;
    }

    // @param driveID the driveID to set
    public void setDriveId(String driveID) {
        this.driveID = driveID;
    }

    // @return the volunteerID
    public String getVolunteerID() {
        return volunteerID;
    }

    // @param volunteerID the volunteerID to set
    public void setVolunteerId(String volunteerID) {
        this.volunteerID = volunteerID;
    }

    // @return the locationID
    public String getLocationID() {
        return locationID;
    }

    // @param locationID the locationID to set
    public void setLocationId(String locationID) {
        this.locationID = locationID;
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
        insertDrives += "'" + this.getDriveID() + "',";
        insertDrives += "'" + this.getVolunteerID() + "',";
        insertDrives += "'" + this.getLocationID() + "',";
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
