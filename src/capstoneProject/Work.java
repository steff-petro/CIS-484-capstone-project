/*
This is the Work class
 */
package capstoneProject;

// Imports
public class Work {

    // Class variables
    private String workId;
    private String workStatus;
    private String volunteerId;
    private String jobId;
    private String eventId;
    private String animalId;

    // Default constructor
    public Work() {
        this.workId = "Default Work ID";
        this.workStatus = "Default Work Status";
        this.volunteerId = "Default Volunteer ID";
        this.jobId = "Default Job ID";
        this.eventId = "Default Event ID";
        this.animalId = "Default Animal ID";
    }

    // Overloaded constructor
    public Work(String workId, String workStatus, String volunteerId, String jobId, String eventId, String animalId) {
        setWorkId(workId);
        setWorkStatus(workStatus);
        setVolunteerId(volunteerId);
        setJobId(jobId);
        setEventId(eventId);
        setAnimalId(animalId);
    }

    // @return the workId
    public String getWorkId() {
        return workId;
    }

    // @param workId the workId to set
    public void setWorkId(String workId) {
        this.workId = workId;
    }

    // @return the workStatus
    public String getWorkStatus() {
        return workStatus;
    }

    // @param workStatus the workStatus to set
    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    // @return the volunteerId
    public String getVolunteerId() {
        return volunteerId;
    }

    // @param volunteerId the volunteerId to set
    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    // @return the jobId
    public String getJobId() {
        return jobId;
    }

    // @param jobId the jobId to set
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    // @return the eventId
    public String getEventId() {
        return eventId;
    }

    // @param eventId the eventId to set
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    // @return the animalId
    public String getAnimalId() {
        return animalId;
    }

    // @param animalId the animalId to set
    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    
    public void testMethod()  {
        
    }
    
}
