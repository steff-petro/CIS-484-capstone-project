/*
This is the Location class
 */
package capstoneProject;

// Imports
public class Location {

    // Class variables
    private String locationId;
    private String locationName;
    private String locationStreet;
    private String locationCity;
    private String locationState;
    private int locationZip;
    private String locationType;

    // Default constructor
    public Location() {
        this.locationId = "Default Location ID";
        this.locationName = "Default Location Name";
        this.locationStreet = "Default Location Street";
        this.locationCity = "Default Location City";
        this.locationState = "Default Location State";
        this.locationZip = 00000;
        this.locationType = "Default Location type";
    }

    // Overloaded constructor
    public Location(String locationId, String locationName, String locationStreet, String locationCity, String locationState, int locationZip, String locationType) {
        setLocationId(locationId);
        setLocationName(locationName);
        setLocationStreet(locationStreet);
        setLocationCity(locationCity);
        setLocationState(locationState);
        setLocationZip(locationZip);
        setLocationType(locationType);
    }

    // @return the locationId
    public String getLocationId() {
        return locationId;
    }

    // @param locationId the locationId to set
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    // @return the locationName
    public String getLocationName() {
        return locationName;
    }

    // @param locationName the locationName to set
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    // @return the locationStreet
    public String getLocationStreet() {
        return locationStreet;
    }

    // @param locationStreet the locationStreet to set
    public void setLocationStreet(String locationStreet) {
        this.locationStreet = locationStreet;
    }

    // @return the locationCity
    public String getLocationCity() {
        return locationCity;
    }

    // @param locationCity the locationCity to set
    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    // @return the locationState
    public String getLocationState() {
        return locationState;
    }

    // @param locationState the locationState to set
    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }

    // @return the locationZip
    public int getLocationZip() {
        return locationZip;
    }

    // @param locationZip the locationZip to set
    public void setLocationZip(int locationZip) {
        this.locationZip = locationZip;
    }

    // @return the locationType
    public String getLocationType() {
        return locationType;
    }

    // @param locationType the locationType to set
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

}
