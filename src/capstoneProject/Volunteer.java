/*
this is the Volunteer class
 */
package capstoneProject;

// Imports
import java.io.*;
import java.util.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.pool.OracleDataSource;

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
    String password;
    static ArrayList<Volunteer> volunteerArrayList = new ArrayList<>();

    static int volunteerCount = 0;

    // Default constructor
    public Volunteer() {
        this.volunteerID = "Default Volunteer ID" + volunteerCount;
        this.firstName = "Default Volunteer First Name";
        this.lastName = "Default Volunteer Last Name";
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
        this.password = "TestPassword123!";
        
        volunteerCount++;
    }

    // Overloaded constructor
    public Volunteer(String volunteerID, String firstName, String lastName, String dateOfBirth,
            String email, String phone, String specialization, String street, String city,
            String state, int zip, String personalInfo, String experience, String status, String password) {
        
        this.volunteerID = volunteerID;
        //this.volunteerID = "volunteer" + volunteerCount;
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
        this.password = password;

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
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void writeVolunteer() {
        String insertVolunteer = "INSERT INTO Volunteer VALUES (";
        insertVolunteer += "'" + this.getVolunteerID() + "',";
        insertVolunteer += "'" + this.getFirstName() + "',";
        insertVolunteer += "'" + this.getLastName() + "',";
        insertVolunteer += "'" + this.getDateOfBirth() + "',";
        insertVolunteer += "'" + this.getStreet() + "',";
        insertVolunteer += "'" + this.getCity() + "',";
        insertVolunteer += "'" + this.getState() + "',";
        insertVolunteer += " " + this.getZip() + " ,";
        insertVolunteer += "'" + this.getEmail() + "',";
        insertVolunteer += "'" + this.getPhone() + "',";
        insertVolunteer += "'" + this.getExperience() + "',";
        insertVolunteer += "'" + this.getStatus() + "',";
        insertVolunteer += "'" + this.getPassword() + "',";
        insertVolunteer += "'" + this.getSpecialization() + "',";
        insertVolunteer += "'" + this.getPersonalInfo() + "')";
        sendDBCommand(insertVolunteer);
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


    // Method that verifies if the user exists
    public static boolean verifyLogin(String volunteerID, String password) {

        boolean userExists = false;
        for (int i = 0; i < volunteerArrayList.size(); i++) {
            Volunteer myObject = volunteerArrayList.get(i);

            if (volunteerID.equals(myObject.volunteerID) && password.equals(myObject.password)) {
                userExists = true;
            }
        }
        return userExists;
    }
    
    
    // Only use this method once the user has logged in and it has been verified that the user exists or it might return wrong volunteer
    public static Volunteer returnVolunteerObject(String volunteerID) {
        Volunteer myObject;
        int index = 0;
        for (int i = 0; i < volunteerArrayList.size(); i++) {
            myObject = volunteerArrayList.get(i);
            if (myObject.volunteerID.equals(volunteerID)) {
                index = i;
            }
        }
        return myObject = volunteerArrayList.get(index);
    }
    
    // method to return full name of volunteer
    public String toString() {
        return firstName + " " + lastName;
    } 
}
