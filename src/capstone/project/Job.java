/*
This is the Job class
 */
package capstone.project;

// Imports
public class Job {

    // Class variables
    private String jobId;
    private String jobName;
    private String jobNotes;
    private String jobType;
    private String locationId;

    // Default constructor
    public Job() {
        this.jobId = "Default Job ID";
        this.jobName = "Default Job Name";
        this.jobNotes = "Default Job Notes";
        this.jobType = "Default Job type";
        this.locationId = "Default Location ID";
    }

    // Overloaded constructor
    public Job(String jobId, String jobName, String jobNotes, String jobType, String locationId) {
        setJobId(jobId);
        setJobName(jobName);
        setJobNotes(jobNotes);
        setJobType(jobType);
        setLocationId(locationId);
    }

    // @return the jobId
    public String getJobId() {
        return jobId;
    }

    // @param jobId the jobId to set
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    // @return the jobName
    public String getJobName() {
        return jobName;
    }

    // @param jobName the jobName to set
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    // @return the jobNotes
    public String getJobNotes() {
        return jobNotes;
    }

    // @param jobNotes the jobNotes to set
    public void setJobNotes(String jobNotes) {
        this.jobNotes = jobNotes;
    }

    // @return the jobType
    public String getJobType() {
        return jobType;
    }

    // @param jobType the jobType to set
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    // @return the locationId
    public String getLocationId() {
        return locationId;
    }

    // @param locationId the locationId to set
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
