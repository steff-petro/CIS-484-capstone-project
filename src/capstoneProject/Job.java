package capstoneProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.pool.OracleDataSource;

public class Job {

    String jobID;
    String jobName;
    String jobType;
    String location;
    String jobNotes;

    static int jobCount = 0;

    // Default constructor
    public Job() {
        this.jobID = "job" + jobCount;
        this.jobName = "Clean Holding Area";
        this.jobType = "Enclosure Care";
        this.location = "BARK";
        this.jobNotes = null;

        jobCount++;
    }

    // Overloaded constructor
    public Job(String jobID, String jobName, String jobType, String location, String jobNotes) {
        this.jobID = "job" + jobCount;
        this.jobName = jobName;
        this.jobType = jobType;
        this.location = location;
        this.jobNotes = jobNotes;

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

    public String getLocation() {
        return this.location;
    }

    public void setLocationID(String location) {
        this.location = location;
    }

    public String getJobNotes() {
        return this.jobNotes;
    }

    public void setJobNotes(String jobNotes) {
        this.jobNotes = jobNotes;
    }

    //Writes animal to DB
    public void writeJob() {
            String insertJob = "INSERT INTO Job VALUES (";
        insertJob += "'" + this.getJobID() + "',";
        insertJob += "'" + this.getJobName() + "',";
        insertJob += "'" + this.getJobNotes() + "',";
        insertJob += "'" + this.getJobType() + "',";
        insertJob += "'" + this.getLocation() + "')";
        sendDBCommand(insertJob);
    }

    public void sendDBCommand(String sqlQuery) {
        Connection dbConn;
        Statement commStmt;
        ResultSet dbResults;
        // Set up your connection strings
        // IF YOU ARE IN CIS330 NOW: use YOUR Oracle Username/Password
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        String userID = "javauser"; // Change to YOUR Oracle username
        String userPASS = "javapass"; // Change to YOUR Oracle password
        OracleDataSource ds;

        // Clear Box Testing - Print each query to check SQL syntax
        //  sent to this method.
        // You can comment this line out when your program is finished
        System.out.println(sqlQuery);

        // Lets try to connect
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
