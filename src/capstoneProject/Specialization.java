package capstoneProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import oracle.jdbc.pool.OracleDataSource;

public class Specialization {
    private String specializationName;
    static ArrayList<Specialization> specializationList = new ArrayList<>();
    
    public Specialization(String specializationName){
        this.specializationName = specializationName;
    }
    
    public String getSpecializationName(){
        return this.specializationName;
    }
    
    public void writeSpecialization(){
        String insertSpecialization = "INSERT INTO Specialization VALUES ( ";
        insertSpecialization += "'" + this.getSpecializationName()+ "')";
        sendDBCommand(insertSpecialization);
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
}
