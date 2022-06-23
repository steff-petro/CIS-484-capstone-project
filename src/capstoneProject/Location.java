package capstoneProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import oracle.jdbc.pool.OracleDataSource;

public class Location {

    //Data fields
    private String locationID;
    private String name;
    private String street;
    private String city;
    private String state;
    private int zip;
    private String type;
    static ArrayList<Location> locationList = new ArrayList<>();

    static int locationCount = 0;

    public Location() {
        this.locationID = "location" + locationCount;
        this.name = "Default";
        this.street = "Default";
        this.city = "Default";
        this.state = "NA";
        this.zip = 00000;
        this.type = "Default";

        locationCount++;
    }

    public Location(String locationID, String name, String street, String city, String state, int zip, String type) {
        this.locationID = "location" + locationCount;
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

    // Method to return name of location based on locationID
    public static String reutrnLocationName(String locationID) {
        String name = "Shelter";
        for (Location l : Location.locationList) {
            if (l.getLocationID().equalsIgnoreCase(locationID)) {
                name = l.getName();
            }
        }
        return name;
    }

    public static Location returnLocationObject(String locationID) {
        Location currentLocation;
        int index = 0;
        for (int i = 0; i < locationList.size(); i++) {
            currentLocation = locationList.get(i);
            if (currentLocation.locationID.equals(locationID)) {
                index = i;
            }
        }
        return currentLocation = locationList.get(index);
    }

    public static String returnLocationID(String locationName) {
        String locationID = "";
        for (Location l : Location.locationList) {
            if (l.getName().equalsIgnoreCase(locationName)) {
                locationID = l.getLocationID();
            }
        }
        return locationID;
    }

    @Override
    public String toString() {
        return name;
    }
}
