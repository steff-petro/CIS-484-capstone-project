/*
This is the Work class
 */
package capstoneProject;

// Imports

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import oracle.jdbc.pool.OracleDataSource;

public class Work {

    // Class variables
    private String workID;
    private String workStatus;
    private String volunteerID;
    private String jobID;
    private String eventID;
    private String animalID;
    static ArrayList<Work> workList = new ArrayList<>();
    
    static int workCount;

    // Default constructor
    public Work() {
        this.workID = "Default Work ID";
        this.workStatus = "Default Work Status";
        this.volunteerID = "Default Volunteer ID";
        this.jobID = "Default Job ID";
        this.eventID = "Default Event ID";
        this.animalID = "Default Animal ID";
    }

    // Overloaded constructor
    public Work(String workID, String workStatus, String volunteerID, String jobID, String eventID, String animalID) {
        this.workID = workID;
        this.workStatus = workStatus;
        this.volunteerID = volunteerID;
        this.jobID = jobID;
        this.eventID = eventID;
        this.animalID = animalID;
    }

    // @return the workID
    public String getWorkID() {
        return workID;
    }

    // @param workID the workID to set
    public void setWorkID(String workID) {
        this.workID = workID;
    }

    // @return the workStatus
    public String getWorkStatus() {
        return workStatus;
    }

    // @param workStatus the workStatus to set
    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    // @return the volunteerID
    public String getVolunteerID() {
        return volunteerID;
    }

    // @param volunteerID the volunteerID to set
    public void setVolunteerId(String volunteerID) {
        this.volunteerID = volunteerID;
    }

    // @return the jobID
    public String getJobID() {
        return jobID;
    }

    // @param jobID the jobID to set
    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    // @return the eventID
    public String getEventID() {
        return eventID;
    }

    // @param eventID the eventID to set
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    // @return the animalID
    public String getAnimalID() {
        return animalID;
    }

    // @param animalID the animalID to set
    public void setAnimalID(String animalIdD) {
        this.animalID = animalID;
    }
    
    public void writeWork() {
        String insertAnimal = "INSERT INTO Work VALUES (";
        insertAnimal += "'" + this.getWorkID()+ "',";
        insertAnimal += "'" + this.getWorkStatus() + "',";
        insertAnimal += "'" + this.getVolunteerID()+ "',";
        insertAnimal += "'" + this.getJobID()+ "',";
        insertAnimal += "'" + this.getEventID()+ "',";
        insertAnimal += "'" + this.getAnimalID()+ "')";
        sendDBCommand(insertAnimal);
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
