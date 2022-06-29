package capstoneProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import oracle.jdbc.pool.OracleDataSource;

public class BarkApplication extends Application {

    Instant checkIn;
    Instant checkOut;

//     JavaFX Controls for Sign In page
//    // Create GridPanes for all tabs
    GridPane overallPane = new GridPane();

    // Labels, text fields, buttons, boxes
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
            if (btnCheckIn.getText().equalsIgnoreCase("Check In")) {
                String volunteerID = textVolunteerID.getText();
                String password = textPassword.getText();

                // Login verification
                boolean userExists = Volunteer.verifyLogin(volunteerID, password);

                if (userExists) {
                    checkIn = Instant.now();
                    MainWindow mainW = new MainWindow(this, volunteerID);
                    btnCheckIn.setText("Check Out");
                    noAccountVBox.getChildren().remove(noAccountLabel);
                    noAccountVBox.getChildren().remove(btnApplyHere);

                } else {
                    Alert invalidInput = new Alert(Alert.AlertType.ERROR,
                            "Incorrect user credentials.",
                            ButtonType.OK);
                    invalidInput.show();
                }

                textVolunteerID.clear();
                textPassword.clear();
                textVolunteerID.setText(volunteerID);

                // Displays window to log out
            } else {
                Volunteer currentUser = Volunteer.returnVolunteerObject(textVolunteerID.getText());

                String volunteerID = textVolunteerID.getText();
                String password = textPassword.getText();

                // Login verification
                boolean userExists = Volunteer.verifyLogin(volunteerID, password);

                if (userExists) {
                    checkOut = Instant.now();
                    double timeElapsed = Duration.between(checkIn, checkOut).toMinutes();
                    int quarterHours = (int) timeElapsed / 15;
                    System.out.println("Quarter Hours Elapsed: " + quarterHours);

                    Shift tempShift = new Shift(
                            "shift" + Shift.shiftCount,
                            checkIn,
                            checkOut,
                            currentUser.getVolunteerID()
                    );

                    // Calculating total quarter hours
                    currentUser.setTotalQHours(currentUser.getTotalQHours() + quarterHours);
                    if (currentUser.getTotalQHours() >= 80 && (!currentUser.getStatus().equalsIgnoreCase("admin"))) {
                        currentUser.setStatus("active");
                    }

                    Alert confirmCheckOut = new Alert(Alert.AlertType.CONFIRMATION,
                            "You have checked out for today.",
                            ButtonType.OK);
                    confirmCheckOut.show();
                    primaryStage.close();

                    //Update Qhours and Status in DB for this volunteer
                    sendDBCommand("UPDATE VOLUNTEER SET TOTALQUARTERHOURS = "
                            + currentUser.getTotalQHours() + ", STATUS = '"
                            + currentUser.getStatus() + "' "
                            + "WHERE VOLUNTEERID = '" + currentUser.getVolunteerID() + "'"
                    );
                } else {
                    Alert invalidInput = new Alert(Alert.AlertType.ERROR,
                            "Incorrect user credentials.",
                            ButtonType.OK);
                    invalidInput.show();
                }
            }
        });
        btnApplyHere.setOnAction(e -> {
            CreateAccountWindow createAcct = new CreateAccountWindow();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    /*Method runs when program is closed
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

        //Fixes problem where if application is closed without logging in, all work is deleted
        if (!Work.workList.isEmpty()) {
            //Fixes duplication of work
            sendDBCommand("DELETE FROM Work");
        }

        //Write all Work instances on close
        for (Work w : Work.workList) {
            w.writeWork();
        }
    }*/
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
                    + "PASSWORD, TOTALQUARTERHOURS FROM VOLUNTEER";
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
                        dbVolunteers.getNString("PASSWORD"),
                        dbVolunteers.getInt("TOTALQUARTERHOURS")
                );
                Volunteer.volunteerArrayList.add(dbVolunteer);
            }
            MainWindow.allVolunteers.clear();
            for (Volunteer v : Volunteer.volunteerArrayList) {
                if (!v.getStatus().equalsIgnoreCase("conditional")) {
                    MainWindow.allVolunteers.add(v);
                }
            }

            //Read Specialization data into Specialization objects
            String specializationQuery = "SELECT SPECIALIZATIONNAME FROM SPECIALIZATION";
            ResultSet dbSpecializations = commStmt.executeQuery(specializationQuery);
            while (dbSpecializations.next()) {
                Specialization dbSpecialization = new Specialization(
                        dbSpecializations.getNString("SPECIALIZATIONNAME"));

                Specialization.specializationList.add(dbSpecialization);
            }
            for (Specialization s : Specialization.specializationList) {
                MainWindow.specializations.add(s.getSpecializationName());
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
