package capstoneProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import oracle.jdbc.pool.OracleDataSource;

public class BarkApplication extends Application {

//     JavaFX Controls for Sign In page
//    // Create GridPanes for all tabs
    GridPane overallPane = new GridPane();

    // Labels, text fields, buttons, boxes
    Label welcomeLabel = new Label("Welcome! If you are a volunteer, check in below.");
    Label volunteerIDLabel = new Label("VolunteerID:");
    TextField textVolunteerID = new TextField();
    Label passwordLabel = new Label("Password:");
    PasswordField textPassword = new PasswordField();
    Button btnCheckIn = new Button("Check In");
    Label noAccountLabel = new Label("Don't have an account?");
    Button btnApplyHere = new Button("Apply Here!");
    VBox noAccountVBox = new VBox();
    HBox idHBox = new HBox(volunteerIDLabel, textVolunteerID);
    HBox passHBox = new HBox(passwordLabel, textPassword);
    VBox signInVBox = new VBox();

    @Override
    public void start(Stage primaryStage) {
//        readVolunteerData();
        overallPane.setAlignment(Pos.CENTER);
        signInVBox.setAlignment(Pos.CENTER_RIGHT);
        noAccountVBox.setAlignment(Pos.CENTER);

        // Adding labels, text fields, and buttons to sign-in page
        overallPane.add(signInVBox, 0, 0);
        overallPane.add(noAccountVBox, 0, 1);

        idHBox.setSpacing(10);
        passHBox.setSpacing(10);
        signInVBox.setSpacing(10);
        signInVBox.setPadding(new Insets(10, 20, 10, 20));
        signInVBox.getChildren().addAll(idHBox, passHBox);
        noAccountVBox.setSpacing(10);
        noAccountVBox.setPadding(new Insets(10, 20, 10, 20));
        noAccountVBox.getChildren().addAll(btnCheckIn, noAccountLabel, btnApplyHere);

        Scene primaryScene = new Scene(overallPane, 400, 450);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("BARK Volunteer Information System");
        primaryStage.show();

        readVolunteerData();

        // Actions to open main form when user logs in or open create account form
        btnCheckIn.setOnAction(e -> {

            String volunteerID = textVolunteerID.getText();
            String password = textPassword.getText();

//            MainWindow mainW = new MainWindow(this, volunteerID);
            // Login verification
//            boolean userExists = Volunteer.verifyLogin(volunteerID, password);
            boolean userExists = Volunteer.verifyLogin("volunteer2", "AdminPassword12!");

            if (userExists) {
                MainWindow mainW = new MainWindow(this, "volunteer2");
                primaryStage.close();
            } else {
                Alert invalidInput = new Alert(Alert.AlertType.ERROR,
                        "Incorrect user credentials.",
                        ButtonType.OK);
                invalidInput.show();
            }

        });
        btnApplyHere.setOnAction(e -> {
            CreateAccountWindow createAcct = new CreateAccountWindow();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    //Method runs when program is closed
    @Override
    public void stop() {
        //Write all Animal instances on close
        for (Animal a : Animal.animalList) {
            a.writeAnimal();
        }

        //Write all Drive instances on close
        for (Drives d : Drives.drivesList) {
            d.writeDrives();
        }

        //Write all Event instances on close
        for (Event e : Event.eventList) {
            e.writeEvent();
        }

        //Write all Job instances on close
        for (Job j : Job.jobList) {
            j.writeJob();
        }

        //Write all Location instances on close
        for (Location l : Location.locationList) {
            l.writeLocation();
        }

        //Write all Shift instances on close
        for (Shift s : Shift.shiftList) {
            s.writeShift();
        }

        //Write all Volunteer instances on close
        for (Volunteer v : Volunteer.volunteerArrayList) {
            v.writeVolunteer();
        }
        //Write all Work instances on close
        for (Work w : Work.workList) {
            w.writeWork();
        }
    }

    // Method to read volunteer data from database
    public void readVolunteerData() {
        Connection dbConn;
        Statement commStmt;
        // Set up connection strings
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        String userID = "javauser";
        String userPASS = "javapass";
        OracleDataSource ds;

        // try to connect
        try {
            ds = new OracleDataSource();
            ds.setURL(URL);
            dbConn = ds.getConnection(userID, userPASS);
            commStmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            // Reading Volunteer data into Volunteer objects
            String volunteerQuery = "SELECT VOLUNTEERID,FIRSTNAME,LASTNAME,DATEOFBIRTH,"
                    + "EMAIL,PHONENUMBER,SPECIALIZATION,VOLUNTEERSTREET,VOLUNTEERCITY,"
                    + "VOLUNTEERSTATE,VOLUNTEERZIP,PERSONALINFO,EXPERIENCE,STATUS,"
                    + "PASSWORD FROM VOLUNTEER";
            ResultSet dbVolunteers = commStmt.executeQuery(volunteerQuery);
            System.out.println(volunteerQuery);
            while (dbVolunteers.next()) {
                Volunteer dbVolunteer = new Volunteer(
                        dbVolunteers.getNString("VOLUNTEERID"),
                        dbVolunteers.getNString("FIRSTNAME"),
                        dbVolunteers.getNString("LASTNAME"),
                        dbVolunteers.getNString("DATEOFBIRTH"),
                        dbVolunteers.getNString("EMAIL"),
                        dbVolunteers.getNString("PHONENUMBER"),
                        dbVolunteers.getNString("SPECIALIZATION"),
                        dbVolunteers.getNString("VOLUNTEERSTREET"),
                        dbVolunteers.getNString("VOLUNTEERCITY"),
                        dbVolunteers.getNString("VOLUNTEERSTATE"),
                        dbVolunteers.getInt("VOLUNTEERZIP"),
                        dbVolunteers.getNString("PERSONALINFO"),
                        dbVolunteers.getNString("EXPERIENCE"),
                        dbVolunteers.getNString("STATUS"),
                        dbVolunteers.getNString("PASSWORD")
                );
                Volunteer.volunteerArrayList.add(dbVolunteer);
//                System.out.println(dbVolunteer.firstName + " " + dbVolunteer.lastName + " " + dbVolunteer.status);
            }
            for (Volunteer v : Volunteer.volunteerArrayList) {
                System.out.println(v.firstName + " " + v.lastName + " " + v.volunteerID + " " + v.password + " " + v.status);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

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
