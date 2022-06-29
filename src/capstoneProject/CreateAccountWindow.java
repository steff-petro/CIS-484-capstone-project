package capstoneProject;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;

public class CreateAccountWindow {
    //     JavaFX Controls

    // Create GridPane
    GridPane overallPane = new GridPane();

    // Labels, text fields/areas, button, combo box
    Label lblVolunteerID = new Label("Volunteer ID:");
    Label lblFirstName = new Label("First Name:");
    Label lblLastName = new Label("Last Name:");
    Label lblDateOfBirth = new Label("Date of Birth:");
    Label lblEmail = new Label("Email:");
    Label lblPhone = new Label("Phone Number:");
    Label lblSpecialization = new Label("Specialization:");
    Label lblStreet = new Label("Street Address:");
    Label lblCity = new Label("City:");
    Label lblState = new Label("State:");
    Label lblZip = new Label("Zip:");
    Label lblInfo = new Label("Personal Information:");
    Label lblExperience = new Label("Please describe your Animal Experience Here: ");

    Text txtVolunteerID = new Text();
    TextField txtFirstName = new TextField();
    TextField txtLastName = new TextField();
    TextField txtDateOfBirth = new TextField();
    TextField txtEmail = new TextField();
    TextField txtPhone = new TextField();
    TextField txtStreet = new TextField();
    TextField txtCity = new TextField();
    TextField txtState = new TextField();
    TextField txtZip = new TextField();
    TextField txtInfo = new TextField();

    ComboBox<String> comboSpecialization = new ComboBox<>(MainWindow.specializations);

    TextArea txtExperience = new TextArea();

    Button btnSubmit = new Button("Submit Application");

    VBox leftVBox = new VBox();
    VBox rightVBox = new VBox();
    HBox idBox = new HBox(lblVolunteerID, txtVolunteerID);
    HBox firstNameBox = new HBox(lblFirstName, txtFirstName);
    HBox lastNameBox = new HBox(lblLastName, txtLastName);
    HBox dateOfBirthBox = new HBox(lblDateOfBirth, txtDateOfBirth);
    HBox emailBox = new HBox(lblEmail, txtEmail);
    HBox phoneBox = new HBox(lblPhone, txtPhone);
    HBox specialBox = new HBox(lblSpecialization, comboSpecialization);
    HBox streetBox = new HBox(lblStreet, txtStreet);
    HBox cityBox = new HBox(lblCity, txtCity);
    HBox stateBox = new HBox(lblState, txtState);
    HBox zipBox = new HBox(lblZip, txtZip);
    HBox infoBox = new HBox(lblInfo, txtInfo);
    HBox experienceBox = new HBox();

    public CreateAccountWindow() {

        overallPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.TOP_LEFT);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        overallPane.add(leftVBox, 0, 0);
        overallPane.add(rightVBox, 1, 0);
//        overallPane.add(experienceBox, 0, 7);
        overallPane.add(btnSubmit, 1, 20);

        // Set spacing on HBoxs/VBoxes and add content
        idBox.setSpacing(10);
        firstNameBox.setSpacing(10);
        lastNameBox.setSpacing(10);
        dateOfBirthBox.setSpacing(10);
        emailBox.setSpacing(10);
        phoneBox.setSpacing(10);
        specialBox.setSpacing(10);
        streetBox.setSpacing(10);
        cityBox.setSpacing(10);
        stateBox.setSpacing(10);
        zipBox.setSpacing(10);
        infoBox.setSpacing(10);
        experienceBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idBox, firstNameBox, lastNameBox, dateOfBirthBox, emailBox, phoneBox, specialBox, streetBox, cityBox, stateBox, zipBox, infoBox);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblExperience, txtExperience);

        Stage primaryStage = new Stage();
        Scene primaryScene = new Scene(overallPane, 900, 550);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Volunteer Application");
        primaryStage.show();

        // Submit button action to create new volunteer
        btnSubmit.setOnAction(e -> {

            // ERROR / EVENT HANDLING
            try {
                Integer zipInt = Integer.parseInt(txtZip.getText());

                Volunteer tempVolunteer = new Volunteer("volunteer" + Volunteer.volunteerCount, txtFirstName.getText(), txtLastName.getText(), txtDateOfBirth.getText(),
                        txtEmail.getText(), txtPhone.getText(), comboSpecialization.getSelectionModel().getSelectedItem(), txtStreet.getText(), txtCity.getText(),
                        txtState.getText(), zipInt, txtInfo.getText(), txtExperience.getText(), "conditional", "Password123", 0);
                Volunteer.volunteerArrayList.add(tempVolunteer);
                tempVolunteer.writeVolunteer();

                Alert confirmSubmit = new Alert(Alert.AlertType.CONFIRMATION,
                        "BARK has received your volunteer application. Thank you for applying!",
                        ButtonType.OK);
                confirmSubmit.show();
                primaryStage.close();

            } catch (NumberFormatException nfe) {
                Alert nfException = new Alert(Alert.AlertType.ERROR,
                        "Please make sure to enter a number for zip code.",
                        ButtonType.OK);
                nfException.show();
            } catch (Exception ex) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR,
                        "Your application could not be submitted. Please make sure you have filled out all fields.",
                        ButtonType.OK);
                noSelection.show();
            }
        });
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
