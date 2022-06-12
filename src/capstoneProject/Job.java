/*
This is the Job class
 */
package capstoneProject;

// Imports
public class Job {

    String jobID;
    String jobName;
    String jobType;
    String location;
    String jobNotes;
    
    static int jobCount = 0;
    
    // Default constructor
    public Job() {
        this.jobID = "Default Job ID" + jobCount;
        this.jobName = "Default Job Name";
        this.jobType = "Default Job type";
        this.location = "Default Location ID";
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
}
