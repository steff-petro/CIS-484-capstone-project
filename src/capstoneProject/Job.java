package capstoneProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import oracle.jdbc.pool.OracleDataSource;

public class Job {

    String jobID;
    String jobName;
    String jobType;
    String locationID;
    String jobNotes;
    String locationName;
    static ArrayList<Job> jobList = new ArrayList<>();

    static int jobCount = 0;

    // Default constructor
    public Job() {
        this.jobID = "job" + jobCount;
        this.jobName = "Clean Holding Area";
        this.jobType = "Enclosure Care";
        this.locationID = "location1";
        this.jobNotes = null;

        jobCount++;
    }

    // Overloaded constructor
    public Job(String jobID, String jobName, String jobType, String locationID, String jobNotes) {
        this.jobID = "job" + jobCount;
        this.jobName = jobName;
        this.jobType = jobType;
        this.locationID = locationID;
        this.jobNotes = jobNotes;
//        this.locationName = Location.reutrnLocationName(locationID);

        jobCount++;
    }

    public String getJobID() {
        return this.jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getJobName() {
        return this.jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobType() {
        return this.jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getLocationID() {
        return this.locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getJobNotes() {
        return this.jobNotes;
    }

    public void setJobNotes(String jobNotes) {
        this.jobNotes = jobNotes;
    }
    
    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    //Writes animal to DB
    public void writeJob() {
            String insertJob = "INSERT INTO Job VALUES (";
        insertJob += "'" + this.getJobID() + "',";
        insertJob += "'" + this.getJobName() + "',";
        insertJob += "'" + this.getJobType() + "',";
        insertJob += "'" + this.getLocationID() + "',";
        insertJob += "'" + this.getJobNotes() + "')";
        sendDBCommand(insertJob);
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
    
    @Override
    public String toString() {
        return jobName;
    }

}
