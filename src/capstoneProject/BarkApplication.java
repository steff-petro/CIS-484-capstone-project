package capstoneProject;

import java.util.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class BarkApplication extends Application {
    
//     JavaFX Controls for Sign In page
//    // Create GridPanes for all tabs
    GridPane overallPane = new GridPane();

    // Labels, text fields, buttons, boxes
    Label welcomeLabel = new Label("Welcome! If you are a volunteer, check in below.");
    Label volunteerIDLabel = new Label("VolunteerID:");
    TextField textVolunteerID = new TextField();
    Label passwordLabel = new Label("Password:");
    TextField textPassword = new TextField();
    Button btnCheckIn = new Button("Check In");
    Label noAccountLabel = new Label("Don't have an account?");
    Button btnApplyHere = new Button("Apply Here!");
    VBox noAccountVBox = new VBox();
    HBox idHBox = new HBox(volunteerIDLabel, textVolunteerID);
    HBox passHBox = new HBox(passwordLabel, textPassword);
    VBox signInVBox = new VBox();

    @Override
    public void start(Stage primaryStage) {
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

        // Actions to open main form when user logs in or open create account form
        btnCheckIn.setOnAction(e -> {
            
            String volunteerID = textVolunteerID.getText();
            String password = textPassword.getText();
            
            // METHOD FOR LOGIN VERIFICATION GOES HERE -- CHECK USER ID/PASSWORD, CHECK IF ADMIN
//            verifyLogin();
            MainWindow mainW = new MainWindow(this, volunteerID);
            primaryStage.close();
            
        });
        btnApplyHere.setOnAction(e -> {
            CreateAccountWindow createAcct = new CreateAccountWindow();
        });

    }

    

    public static void main(String[] args) {
        launch(args);
    }

}
