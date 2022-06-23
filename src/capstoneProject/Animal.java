/*
This is the Animal class
 */
package capstoneProject;

// Imports

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import oracle.jdbc.pool.OracleDataSource;

public class Animal {

    // Class variables and class array list
    private String animalID;
    private String animalName;
    private String animalSpecies;
    private String animalBreed;
    private int animalAge;
    static ArrayList<Animal> animalList = new ArrayList<>();
    
    static int animalCount = 0;

    // Default Constructor
    public Animal() {

        this.animalID = "Default Animal ID";
        this.animalName = "Default Animal Name";
        this.animalSpecies = "Default Animal Type";
        this.animalAge = 0;

    }

    // Overloaded constructor
    public Animal(String animalID, String animalName, String animalSpecies, String animalBreed, int animalAge) {
        this.animalID = "animal" + animalCount;
        this.animalName = animalName;
        this.animalSpecies = animalSpecies;
        this.animalBreed = animalBreed;
        this.animalAge = animalAge;
        
        animalCount++;

    }

    // @return the animalId
    public String getAnimalID() {
        return animalID;
    }

    // @param animalId the animalID to set
    public void setAnimalID(String animalID) {
        this.animalID = animalID;
    }

    // @return the animalName
    public String getAnimalName() {
        return animalName;
    }

    // @param animalName the animalName to set
    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    // @return the animalSpecies
    public String getAnimalSpecies() {
        return animalSpecies;
    }

    // @param animalSpecies the animalSpecies to set
    public void setAnimalSpecies(String animalSpecies) {
        this.animalSpecies = animalSpecies;
    }
    
    public String getAnimalBreed() {
        return this.animalBreed;
    }

    public void setAnimalBreed(String animalBreed) {
        this.animalBreed = animalBreed;
    }

    // @return the animalAge
    public int getAnimalAge() {
        return animalAge;
    }

    // @param animalAge the animalAge to set
    public void setAnimalAge(int animalAge) {
        this.animalAge = animalAge;
    }
    
    public void writeAnimal() {
        String insertAnimal = "INSERT INTO Animal VALUES (";
        insertAnimal += "'" + this.getAnimalID() + "',";
        insertAnimal += "'" + this.getAnimalName() + "',";
        insertAnimal += "'" + this.getAnimalSpecies() + "',";
        insertAnimal += "'" + this.getAnimalBreed() + "',";
        insertAnimal += " " + this.getAnimalAge() + ")";
        sendDBCommand(insertAnimal);
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
    
    public static Animal returnAnimalObject(String animalID) {
        Animal myObject;
        int index = 0;
        for (int i = 0; i < animalList.size(); i++) {
            myObject = animalList.get(i);
            if (myObject.animalID.equals(animalID)) {
                index = i;
            }
        }
        return myObject = animalList.get(index);
    }
    
    @Override
    public String toString() {
        return "Name: " + animalName + "\tSpecies: " + animalSpecies;
    }

}
