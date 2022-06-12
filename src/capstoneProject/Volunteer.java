/*
this is the Volunteer class
 */
package capstoneProject;

// Imports
public class Volunteer {

    // Data fields
    String volunteerID;
    String firstName;
    String lastName;
    String dateOfBirth;
    String email;
    String phone;
    String specialization;
    String street;
    String city;
    String state;
    int zip;
    String personalInfo;
    String experience;
    String status;
    
    static int volunteerCount = 0;
    
    // Default constructor
    public Volunteer() {
        this.volunteerID = "Default Volunteer ID" + volunteerCount;
        this.firstName = "Default Volunteer First Name";
        this.lastName ="Default Volunteer Last Name";
        this.dateOfBirth = "00/00/0000";
        this.email = "Default Email";
        this.phone = "1234567890";
        this.specialization = "Default Specialization";
        this.street = "Default Volunteer Street";
        this.city = "Default Volunteer City";
        this.state = "XX";
        this.zip = 00000;
        this.personalInfo = "none";
        this.experience = "Default Experience";
        this.status = "admin";
        
        volunteerCount++;
    }
    
    // Overloaded constructor
    public Volunteer(String volunteerID, String firstName, String lastName, String dateOfBirth, 
            String email, String phone, String specialization, String street, String city, 
            String state, int zip, String personalInfo, String experience, String status) {
        
        this.volunteerID = "volunteer" + volunteerCount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.personalInfo = personalInfo;
        this.experience = experience;
        this.status = status;
        
        volunteerCount++;
    }
    
    public String getVolunteerID() {
        return this.volunteerID;
    }
    
    public void setVolunteerID(String volunteerID) {
        this.volunteerID = volunteerID;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getDateOfBirth() {
        return this.dateOfBirth;
    }
    
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getSpecialization() {
        return this.specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getStreet() {
        return this.street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public int getZip() {
        return this.zip;
    }
    
    public void setZip(int zip) {
        this.zip = zip;
    }
    
    public String getPersonalInfo() {
        return this.personalInfo;
    }
    
    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }
    
    public String getExperience() {
        return this.experience;
    }
    
    public void setExperience(String experience) {
        this.experience = experience;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

}
