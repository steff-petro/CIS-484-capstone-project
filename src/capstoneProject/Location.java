/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapstoneProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author emmagriffith
 */
public class Location {

    //Data fields
    String locationID;
    String name;
    String street;
    String city;
    String state;
    int zip;
    String type;

    static int locationCount = 0;

    public Location() {
        this.locationID = "Location" + locationCount;
        this.name = "Default";
        this.street = "Default";
        this.city = "Default";
        this.state = "NA";
        this.zip = 00000;
        this.type = "Default";

        locationCount++;
    }

    public Location(String location, String name, String street, String city, String state, int zip, String type) {
        this.locationID = "Location" + locationCount;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.type = type;

        locationCount++;
    }

    public String getLocationID() {
        return this.locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getType() {
        return this.type;
    }

    public void setLtype(String type) {
        this.type = type;
    }

    //Writes Location to DB
    public void writeLocation() {
        String insertLocation = "INSERT INTO Location VALUES (";
        insertLocation += "'" + this.getLocationID() + "',";
        insertLocation += "'" + this.getName() + "',";
        insertLocation += "'" + this.getStreet() + "',";
        insertLocation += "'" + this.getCity() + "',";
        insertLocation += "'" + this.getState() + "',";
        insertLocation += " " + this.getZip() + ",";
        insertLocation += "'" + this.getType() + "')";
        sendDBCommand(insertLocation);
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
