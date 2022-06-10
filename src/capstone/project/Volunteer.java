/*
this is the Volunteer class
 */
package capstone.project;

// Imports
public class Volunteer {

    // Class variables
    private String volunteerId;
    private String firstName;
    private String lastName;
    private String volunteerStreet;
    private String volunteerCity;
    private String volunteerState;
    private int volunteerZip;
    private String email;
    private String phoneNumber;
    private String experience;
    private String status;
    private String password;
    private String specialization;

    // Default constructor
    public Volunteer() {
        
        this.volunteerId = "Default Volunteer ID";
        this.firstName = "Default Volunteer First Name";
        this.lastName = "Defualt Volunteer Last Name";

        
        
        
        

    }


// Overriden constructor
    public Volunteer(String volunteerId, String firstName, String lastName, String volunteerStreet, String volunteerCity, String volunteerState, int volunteerZip, String email, String phoneNumber, String experience, String status, String password, String specialization) {
        setVolunteerId(volunteerId);
        setFirstName(firstName);
        setLastName(lastName);
        setVolunteerStreet(volunteerStreet);
        setVolunteerCity(volunteerCity);
        setVolunteerState(volunteerState);
        setVolunteerZip(volunteerZip);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setExperience(experience);
        setStatus(status);
        setPassword(password);
        setSpecialization(specialization);
    }

    // @return the volunteerId
    public String getVolunteerId() {
        return volunteerId;
    }

    // @param volunteerId the volunteerId to set
    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    // @return the firstName
    public String getFirstName() {
        return firstName;
    }

    // @param firstName the firstName to set
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // @return the lastName
    public String getLastName() {
        return lastName;
    }

    // @param lastName the lastName to set
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // @return the volunteerStreet
    public String getVolunteerStreet() {
        return volunteerStreet;
    }

    // @param volunteerStreet the volunteerStreet to set
    public void setVolunteerStreet(String volunteerStreet) {
        this.volunteerStreet = volunteerStreet;
    }

    // @return the volunteerCity
    public String getVolunteerCity() {
        return volunteerCity;
    }

    // @param volunteerCity the volunteerCity to set
    public void setVolunteerCity(String volunteerCity) {
        this.volunteerCity = volunteerCity;
    }

    // @return the volunteerState
    public String getVolunteerState() {
        return volunteerState;
    }

    // @param volunteerState the volunteerState to set
    public void setVolunteerState(String volunteerState) {
        this.volunteerState = volunteerState;
    }

    // @return the volunteerZip
    public int getVolunteerZip() {
        return volunteerZip;
    }

    // @param volunteerZip the volunteerZip to set
    public void setVolunteerZip(int volunteerZip) {
        this.volunteerZip = volunteerZip;
    }

    // @return the email
    public String getEmail() {
        return email;
    }

    // @param email the email to set
    public void setEmail(String email) {
        this.email = email;
    }

    // @return the phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // @param phoneNumber the phoneNumber to set
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // @return the experience
    public String getExperience() {
        return experience;
    }

    // @param experience the experience to set
    public void setExperience(String experience) {
        this.experience = experience;
    }

    // @return the status
    public String getStatus() {
        return status;
    }

    // @param status the status to set
    public void setStatus(String status) {
        this.status = status;
    }

    // @return the password
    public String getPassword() {
        return password;
    }

    // @param password the password to set
    public void setPassword(String password) {
        this.password = password;
    }

    // @return the specialization
    public String getSpecialization() {
        return specialization;
    }

    // @param specialization the specialization to set
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    
    
    
}
