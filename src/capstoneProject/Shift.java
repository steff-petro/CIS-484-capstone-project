/*
This is the Shift class
 */
package capstoneProject;

// Imports
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
}
