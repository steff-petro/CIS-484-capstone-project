package capstoneProject;

import java.io.*;
import java.sql.*;
import java.time.Instant;
import java.time.Duration;
import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.util.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.*;
import javafx.scene.control.TabPane.*;
import javafx.scene.image.*;
import oracle.jdbc.pool.OracleDataSource;

public class MainWindow {

    // Data Fields
    BarkApplication signInForm;
    String volunteerID;
    Instant checkIn;

    // Obervable list to hold names of locations - used to populate comboboxes
    // so that when user enters a location, they select from existing locations
    ObservableList<String> locationNames = FXCollections.observableArrayList();

    // Observable list and ListView for home page
    ObservableList<Work> homePageOL = FXCollections.observableArrayList();
    ListView homePageList = new ListView<>(homePageOL);

//     JavaFX Controls
    // Labels, TableView, and Button for Jobs Pane
    Label lblJobs = new Label("Jobs for Today");
    Button btnSelectJob = new Button("Sign Up for Selected Job");
    ObservableList<Job> jobData = FXCollections.observableArrayList();
    TableView<Job> jobTable = new TableView<>(jobData);
    VBox jobVBox = new VBox();

    // Labels, TableView, and Button for Events Pane
    Label lblEvents = new Label("Upcoming Events");
    Button btnSelectEvent = new Button("Register for Selected Event");
    ObservableList<Event> eventData = FXCollections.observableArrayList();
    TableView<Event> eventTable = new TableView<>(eventData);
    VBox eventVBox = new VBox();

    // Controls for drives pane
    Label lblYourDrives = new Label("Your drive history:");
    ObservableList<Drives> drivesData = FXCollections.observableArrayList();
    ListView<Drives> driveHistoryList = new ListView<>(drivesData);
    Label lblDriveID = new Label("Drive ID:");
    Label lblLocation = new Label("Location:");
    Label lblMiles = new Label("Miles Driven:");
    Label lblDriveDate = new Label("Date:");
    Label lblDriveNotes = new Label("Notes:");
    Text txtDriveID = new Text();
    ComboBox comboLocation = new ComboBox<>(locationNames);
    TextField txtMiles = new TextField();
    TextField txtDriveDate = new TextField();
    TextField txtDriveNotes = new TextField();
    Button btnLogDrive = new Button("Log Drive");
    HBox driveIDHBox = new HBox(lblDriveID, txtDriveID);
    HBox locationHBox = new HBox(lblLocation, comboLocation);
    HBox milesHBox = new HBox(lblMiles, txtMiles);
    HBox dateHBox = new HBox(lblDriveDate, txtDriveDate);
    HBox notesHBox = new HBox(lblDriveNotes, txtDriveNotes);
    VBox driveVBox = new VBox();

    // Tabs and controls for admin pane
    TabPane tbPaneAdmin = new TabPane();
    Tab tab6 = new Tab("Reports");
    Tab tab7 = new Tab("Add/Edit Jobs");
    Tab tab8 = new Tab("Add/Edit Events");
    Tab tab9 = new Tab("Add/Edit Locations");
    Tab tab10 = new Tab("Animals");
    Tab tab11 = new Tab("Manage Volunteers");
    Tab tab13 = new Tab("Edit Specializations");

    VBox adminVBox = new VBox();
    ObservableList<Volunteer> conditionalVolunteers = FXCollections.observableArrayList();
    ObservableList<Volunteer> currentVolunteers = FXCollections.observableArrayList();
    ObservableList<Volunteer> inactiveVolunteers = FXCollections.observableArrayList();
    ListView<Volunteer> conditionalVolList = new ListView<>(conditionalVolunteers);
    ListView<Volunteer> currentVolList = new ListView<>(currentVolunteers);
    ListView<Volunteer> inactiveVolList = new ListView<>(inactiveVolunteers);
    static ObservableList<String> specializations = FXCollections.observableArrayList(); //"Animal Health Care", "Feeding", "Enclosure Care", "Adopter Relations", "Event Volunteer"
    ListView<String> specializationList = new ListView<>(specializations);
    ListView<Job> currentJobsList = new ListView<>(jobData);
    ListView<Event> currentEventsList = new ListView<>(eventData);
    ObservableList<Location> currentLocations = FXCollections.observableArrayList();
    ListView<Location> currentLocationsList = new ListView<>(currentLocations);
    ObservableList<Animal> currentAnimals = FXCollections.observableArrayList();
    ListView<Animal> currentAnimalsList = new ListView<>(currentAnimals);

    // Create GridPanes for all tabs
    GridPane overallPane = new GridPane(); // holds menuBar and tab pane
    GridPane homePane = new GridPane();
    GridPane jobPane = new GridPane();
    GridPane eventPane = new GridPane();
    GridPane volunteerPane = new GridPane();
    GridPane drivesPane = new GridPane();
    GridPane adminPane = new GridPane();
    GridPane adminReportsPane = new GridPane();
    GridPane adminJobsPane = new GridPane();
    GridPane adminEventsPane = new GridPane();
    GridPane adminVolunteerPane = new GridPane();
    GridPane adminAnimalPane = new GridPane();
    GridPane adminLocationsPane = new GridPane();
    GridPane adminSpecializationsPane = new GridPane();

    // Create Menu Bar
    MenuBar menuBar = new MenuBar();
    Menu menuMyAccount = new Menu("My Account");
    MenuItem miEditAccount = new MenuItem("Edit Account");
    MenuItem miCheckOut = new MenuItem("Check Out");
    Menu menuMyWork = new Menu("My Jobs and Events");
    MenuItem miMyJobs = new MenuItem("My Jobs");
    MenuItem miMyEvents = new MenuItem("My Events");

    // Create tab pane object and create 5 tabs
    TabPane tbPane = new TabPane();
    Tab tab1 = new Tab("Home");
    Tab tab2 = new Tab("Jobs");
    Tab tab3 = new Tab("BARK Events");
    Tab tab4 = new Tab("Volunteer Summary");
    Tab tab5 = new Tab("Log a Drive");
    Tab tab12 = new Tab("Admin");

    // Class wide variable that can be used to display content related to the logged in user
    String currentLoggedInUser;

    public MainWindow(BarkApplication signInForm, Instant checkIn, String volunteerID) {
        this.checkIn = checkIn;

        // Class wide variable that can be used to display content related to the logged in user
        currentLoggedInUser = volunteerID;

        overallPane.setAlignment(Pos.CENTER);
        homePane.setAlignment(Pos.CENTER);
        jobPane.setAlignment(Pos.CENTER);
        eventPane.setAlignment(Pos.CENTER);
        volunteerPane.setAlignment(Pos.CENTER);
        drivesPane.setAlignment(Pos.CENTER);
        adminPane.setAlignment(Pos.CENTER);
        jobVBox.setAlignment(Pos.CENTER);
        eventVBox.setAlignment(Pos.CENTER);
        adminVBox.setAlignment(Pos.CENTER);

        // Set up MenuBar and add to overallPane
        menuMyAccount.getItems().addAll(miEditAccount, miCheckOut);
        menuMyWork.getItems().addAll(miMyJobs, miMyEvents);
        menuBar.getMenus().addAll(menuMyAccount, menuMyWork);
        overallPane.add(menuBar, 0, 0);

        // Home Pane
        // Jobs Pane
        jobPane.add(jobVBox, 0, 0);
        jobVBox.setSpacing(10);
        jobVBox.setPadding(new Insets(10, 20, 10, 20));
        jobVBox.getChildren().addAll(lblJobs, jobTable, btnSelectJob);

        // Events Pane
        eventPane.add(eventVBox, 0, 0);
        eventVBox.setSpacing(10);
        eventVBox.setPadding(new Insets(10, 20, 10, 20));
        eventVBox.getChildren().addAll(lblEvents, eventTable, btnSelectEvent);

        // Volunteer Summary Pane
        // Log Drive Pane
        drivesPane.add(driveVBox, 0, 0);
        drivesPane.add(driveHistoryList, 1, 0);
        driveHistoryList.setPrefWidth(600);
        locationHBox.setSpacing(10);
        milesHBox.setSpacing(10);
        dateHBox.setSpacing(10);
        notesHBox.setSpacing(10);
        driveVBox.setSpacing(10);
        driveVBox.setPadding(new Insets(10, 20, 10, 20));
        driveVBox.getChildren().addAll(locationHBox, milesHBox, dateHBox, notesHBox, btnLogDrive);

        // Admin Pane
        //So tabs cannot close
        tbPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tbPaneAdmin.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        adminPane.add(tbPaneAdmin, 0, 0);
        // tabs on admin pane
        tab6.setContent(adminReportsPane);
        tab7.setContent(adminJobsPane);
        tab8.setContent(adminEventsPane);
        tab9.setContent(adminLocationsPane);
        tab10.setContent(adminAnimalPane);
        tab11.setContent(adminVolunteerPane);
        tab13.setContent(adminSpecializationsPane);
        tbPaneAdmin.getTabs().addAll(tab6, tab7, tab8, tab9, tab10, tab13, tab11);

        // Placing tabs in overallPane and setting content of tabs to correspoding panes
        overallPane.add(tbPane, 0, 1);
        tab1.setContent(homePane);
        tab2.setContent(jobPane);
        tab3.setContent(eventPane);
        tab4.setContent(volunteerPane);
        tab5.setContent(drivesPane);
        tab12.setContent(adminPane);

        // Display tabs based on user status
        Volunteer currentUser = Volunteer.returnVolunteerObject(currentLoggedInUser);
        if (currentUser.getStatus().equalsIgnoreCase("admin")) {
            tbPane.getTabs().addAll(tab1, tab2, tab3, tab4, tab5, tab12);
        } else {
            tbPane.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);
        }

        readDatabaseData(currentUser);
        addJobTab();
        addEventsTab();
        addLocationsTab();
        animalsTab();
        specializationsTab();
        manageVolunteersTab();
        reportsTab();

        Stage primaryStage = new Stage();
        Scene primaryScene = new Scene(overallPane, 900, 650);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("BARK Volunteer Information System");
        primaryStage.show();

        // Set height and width of FX controls
        tbPane.setMinHeight(primaryScene.getHeight());
        tbPane.setMinWidth(primaryScene.getWidth());
        tbPaneAdmin.setMinHeight(primaryScene.getHeight());
        tbPaneAdmin.setMinWidth(primaryScene.getWidth());
        jobTable.setMinWidth(700);
        eventTable.setMinWidth(820);

        populateEventsTable(currentUser);
        populateJobsTable(currentUser);
        volunteerSummary();
        try {
            homePage();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // Select event button action
        btnSelectEvent.setOnAction(e -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();

            try {
                if (selectedEvent.getSpotsLeft() != 0) {
                    selectedEvent.setRegisteredVolunteers(selectedEvent.getRegisteredVolunteers() + 1);
                    // CODE TO ASSOCIATE EVENT WITH CURRENT VOLUNTEER GOES HERE
                    Job currentObject = jobTable.getSelectionModel().getSelectedItem();
                    String currentJobID = currentObject.getJobID();
                    String workStatus = "In Progress";
                    // AnimalID is can be null
                    Work myObject = new Work("ID", workStatus, volunteerID, currentJobID, null, null);

                } else {
                    // Alerts user if the event is full
                    Alert eventFull = new Alert(Alert.AlertType.ERROR,
                            "Sorry, this event does not have any available spots left.",
                            ButtonType.OK);
                    eventFull.show();
                }

            } catch (NullPointerException npe) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR,
                        "You must select an event.",
                        ButtonType.OK);
                noSelection.show();
            }

        });

        // Menu Bar item actions
        miEditAccount.setOnAction(e -> {
            editAccountWindow(currentUser);
        });

        miMyJobs.setOnAction(e -> {
            myJobsMenuItem(currentUser);
        });

        miMyEvents.setOnAction(e -> {
            myEventsMenuItem(currentUser);
        });

        miCheckOut.setOnAction(e -> {
            Instant checkOut = Instant.now();
            double timeElapsed = Duration.between(checkIn, checkOut).toMinutes();
            int quarterHours = (int)timeElapsed / 15;
            System.out.println("Time Elapsed: " + timeElapsed);

            Shift tempShift = new Shift(
                    "shift" + Shift.shiftCount,
                    checkIn,
                    checkOut,
                    currentUser.getVolunteerID()
            );

            // CALCULATE TOTAL QUARTER HOURS HERE - **you actually wont add timeElapsed...you'll end up adding the quarter hours for the shift
            currentUser.setTotalHours(currentUser.getTotalHours() + timeElapsed);

            Alert confirmCheckOut = new Alert(Alert.AlertType.CONFIRMATION,
                    "You have checked out for today.",
                    ButtonType.OK);
            confirmCheckOut.show();
            primaryStage.close();
        });

        // Log Drive button action
        btnLogDrive.setOnAction(e -> {
            String driveLocationID = Location.returnLocationID((String) comboLocation.getValue());
            Drives tempDrive = new Drives(
                    "drive" + Drives.driveCount,
                    currentUser.getVolunteerID(),
                    driveLocationID,
                    Double.valueOf(txtMiles.getText()),
                    txtDriveDate.getText(),
                    txtDriveNotes.getText()
            );
            Drives.drivesList.add(tempDrive);
            driveHistoryList.getItems().clear();
            for (Drives d : Drives.drivesList) {
                if (d.getVolunteerID().equals(currentUser.getVolunteerID())) {
                    drivesData.add(d);
                }
            }
            txtDriveID.setText("drive" + Drives.driveCount);
        });

        // Select job button action
        btnSelectJob.setOnAction(e -> {
            Job selectedJob = jobTable.getSelectionModel().getSelectedItem();
            try {
                // CODE TO ASSOCIATE JOB WITH CURRENT VOLUNTEER GOES HERE
                Work tempWork = new Work(
                        "work" + Work.workCount,
                        "in progress",
                        currentUser.getVolunteerID(),
                        selectedJob.getJobID(),
                        null,
                        null
                );
                Work.workList.add(tempWork);
                homePageList.getItems().clear();
                for (Work w : Work.workList) {
                    homePageOL.add(w);
                }
                jobData.remove(selectedJob);
                homePageList.setItems(homePageOL);

                Alert comfirmSelectJob = new Alert(Alert.AlertType.CONFIRMATION,
                        "Job has been associated to your account.",
                        ButtonType.OK);
                comfirmSelectJob.show();

            } catch (NullPointerException npe) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR,
                        "You must select a job.",
                        ButtonType.OK);
                noSelection.show();
            }
        });

        // Select event button action
        btnSelectEvent.setOnAction(e -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            try {
                if (selectedEvent.getSpotsLeft() != 0) {
                    selectedEvent.setRegisteredVolunteers(selectedEvent.getRegisteredVolunteers() + 1);
                    selectedEvent.setSpotsLeft(selectedEvent.getSpotsLeft() - 1);
                    // CODE TO ASSOCIATE EVENT WITH CURRENT VOLUNTEER GOES HERE
                    Work tempWork = new Work(
                            "work" + Work.workCount,
                            "in progress",
                            currentUser.getVolunteerID(),
                            null,
                            selectedEvent.getEventID(),
                            null
                    );
                    Work.workList.add(tempWork);
                    homePageList.getItems().clear();
                    for (Work w : Work.workList) {
                        homePageOL.add(w);
                    }
                    homePageList.setItems(homePageOL);
                    eventTable.refresh();

                    Alert comfirmSelectEvent = new Alert(Alert.AlertType.CONFIRMATION,
                            "Event has been associated to your account.",
                            ButtonType.OK);
                    comfirmSelectEvent.show();

                } else {
                    // Alerts user if the event is full
                    Alert eventFull = new Alert(Alert.AlertType.ERROR,
                            "Sorry, this event does not have any available spots left.",
                            ButtonType.OK);
                    eventFull.show();
                }

            } catch (NullPointerException npe) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR,
                        "You must select an event.",
                        ButtonType.OK);
                noSelection.show();
            }

        });

    }

    public void myJobsMenuItem(Volunteer currentUser) {
        GridPane myJobsPane = new GridPane();
        Label myIPJobs = new Label("Jobs in Progress");
        Label myCompletedJobs = new Label("Completed Jobs");
        Button btnMarkComplete = new Button("Mark Selected Job As Complete");
        ObservableList<Work> ipJobs = FXCollections.observableArrayList();
        ObservableList<Work> completedJobs = FXCollections.observableArrayList();
        ListView ipJobsList = new ListView<>(ipJobs);
        ListView completedJobsList = new ListView<>(completedJobs);
        VBox ipVBox = new VBox();
        VBox completedVBox = new VBox();

        ipJobsList.getItems().clear();
        completedJobsList.getItems().clear();
        for (Work w : Work.workList) {
            if (w.getVolunteerID().equalsIgnoreCase(currentUser.getVolunteerID())) {
                if (w.getJobID() != null) {
                    if (w.getWorkStatus().equalsIgnoreCase("completed")) {
                        completedJobs.add(w);
                    } else if (w.getWorkStatus().equalsIgnoreCase("in progress")) {
                        ipJobs.add(w);
                    }
                }
            }
        }
        ipJobsList.setItems(ipJobs);
        completedJobsList.setItems(completedJobs);

        myJobsPane.setAlignment(Pos.CENTER);
        ipVBox.setAlignment(Pos.CENTER);
        completedVBox.setAlignment(Pos.CENTER);

        myJobsPane.add(ipVBox, 0, 0);
        myJobsPane.add(completedVBox, 1, 0);

        ipVBox.setSpacing(10);
        ipVBox.setPadding(new Insets(10, 20, 10, 20));
        ipVBox.getChildren().addAll(myIPJobs, ipJobsList, btnMarkComplete);

        completedVBox.setSpacing(10);
        completedVBox.setPadding(new Insets(10, 20, 10, 20));
        completedVBox.getChildren().addAll(myCompletedJobs, completedJobsList);

        Stage myJobsStage = new Stage();
        Scene myJobsScene = new Scene(myJobsPane, 900, 550);
        myJobsStage.setScene(myJobsScene);
        myJobsStage.setTitle("My Jobs");
        myJobsStage.show();

        btnMarkComplete.setOnAction(e2 -> {
            try {
                Work selectedJob = (Work) ipJobsList.getSelectionModel().getSelectedItem();
                selectedJob.setWorkStatus("completed");
                ipJobsList.getItems().clear();
                completedJobsList.getItems().clear();
                for (Work w : Work.workList) {
                    if (w.getVolunteerID().equalsIgnoreCase(currentUser.getVolunteerID())) {
                        if (w.getJobID() != null) {
                            if (w.getWorkStatus().equalsIgnoreCase("completed")) {
                                completedJobs.add(w);
                            } else if (w.getWorkStatus().equalsIgnoreCase("in progress")) {
                                ipJobs.add(w);
                            }
                        }
                    }
                }
                ipJobsList.setItems(ipJobs);
                completedJobsList.setItems(completedJobs);

                Alert confirmMarkComplete = new Alert(Alert.AlertType.CONFIRMATION,
                        "You have marked this job as complete.",
                        ButtonType.OK);
                confirmMarkComplete.show();
            } catch (NullPointerException npe) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR,
                        "You must select a job to be marked as completed.",
                        ButtonType.OK);
                noSelection.show();
            }
        });
    }

    public void myEventsMenuItem(Volunteer currentUser) {
        GridPane myEventsPane = new GridPane();
        Label myIPEvents = new Label("Events in Progress");
        Label myCompletedEvents = new Label("Completed Events");
        Button btnMarkComplete = new Button("Mark Selected Event As Complete");
        ObservableList<Work> ipEvents = FXCollections.observableArrayList();
        ObservableList<Work> completedEvents = FXCollections.observableArrayList();
        ListView ipEventsList = new ListView<>(ipEvents);
        ListView completedEventsList = new ListView<>(completedEvents);
        VBox ipVBox = new VBox();
        VBox completedVBox = new VBox();

        ipEventsList.getItems().clear();
        completedEventsList.getItems().clear();
        for (Work w : Work.workList) {
            if (w.getVolunteerID().equalsIgnoreCase(currentUser.getVolunteerID())) {
                if (w.getEventID() != null) {
                    if (w.getWorkStatus().equalsIgnoreCase("completed")) {
                        completedEvents.add(w);
                    } else if (w.getWorkStatus().equalsIgnoreCase("in progress")) {
                        ipEvents.add(w);
                    }
                }
            }
        }
        ipEventsList.setItems(ipEvents);
        completedEventsList.setItems(completedEvents);

        myEventsPane.setAlignment(Pos.CENTER);
        ipVBox.setAlignment(Pos.CENTER);
        completedVBox.setAlignment(Pos.CENTER);

        myEventsPane.add(ipVBox, 0, 0);
        myEventsPane.add(completedVBox, 1, 0);

        ipVBox.setSpacing(10);
        ipVBox.setPadding(new Insets(10, 20, 10, 20));
        ipVBox.getChildren().addAll(myIPEvents, ipEventsList, btnMarkComplete);

        completedVBox.setSpacing(10);
        completedVBox.setPadding(new Insets(10, 20, 10, 20));
        completedVBox.getChildren().addAll(myCompletedEvents, completedEventsList);

        Stage myEventsStage = new Stage();
        Scene myEventsScene = new Scene(myEventsPane, 900, 550);
        myEventsStage.setScene(myEventsScene);
        myEventsStage.setTitle("My Events");
        myEventsStage.show();

        btnMarkComplete.setOnAction(e2 -> {
            try {
                Work selectedEvent = (Work) ipEventsList.getSelectionModel().getSelectedItem();
                selectedEvent.setWorkStatus("completed");
                ipEventsList.getItems().clear();
                completedEventsList.getItems().clear();
                for (Work w : Work.workList) {
                    if (w.getVolunteerID().equalsIgnoreCase(currentUser.getVolunteerID())) {
                        if (w.getEventID() != null) {
                            if (w.getWorkStatus().equalsIgnoreCase("completed")) {
                                completedEvents.add(w);
                            } else if (w.getWorkStatus().equalsIgnoreCase("in progress")) {
                                ipEvents.add(w);
                            }
                        }
                    }
                }
                ipEventsList.setItems(ipEvents);
                completedEventsList.setItems(completedEvents);

                Alert confirmMarkComplete = new Alert(Alert.AlertType.CONFIRMATION,
                        "You have marked this event as complete.",
                        ButtonType.OK);
                confirmMarkComplete.show();
            } catch (NullPointerException npe) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR,
                        "You must select an event to be marked as completed.",
                        ButtonType.OK);
                noSelection.show();
            }
        });
    }

    public void homePage() throws IOException {
        // FX Controls

        VBox root = new VBox(5);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        InputStream imgInput = new FileInputStream("profilepic.jpeg");

        homePageList.getItems().clear();
        for (Work w : Work.workList) {
            homePageOL.add(w);
        }
        homePageList.setItems(homePageOL);

        homePageList.setCellFactory(new Callback<ListView<Work>, ListCell<Work>>() {
            @Override
            public ListCell<Work> call(ListView<Work> param) {
                return new ListCell<Work>() {
                    @Override
                    protected void updateItem(Work work, boolean empty) {
                        super.updateItem(work, empty);

                        if (work == null || empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            HBox cellRoot = new HBox(5);
                            cellRoot.setAlignment(Pos.CENTER_LEFT);
                            cellRoot.setPadding(new Insets(5));

                            // Add our profile picture
                            Image image = new Image(imgInput);
                            ImageView imgProfilePic = new ImageView();
                            imgProfilePic.setImage(image);
                            imgProfilePic.setFitHeight(24);
                            imgProfilePic.setFitWidth(24);
                            cellRoot.getChildren().add(imgProfilePic);

                            // A simple Separator between the photo and the details
                            cellRoot.getChildren().add(new Separator(Orientation.VERTICAL));

                            // Now, create a VBox to hold the name and age
                            VBox vBox = new VBox(5);
                            vBox.setAlignment(Pos.CENTER_LEFT);
                            vBox.setPadding(new Insets(5));

                            String volunteerName = "";
                            for (Volunteer v : Volunteer.volunteerArrayList) {
                                if (v.getVolunteerID().equalsIgnoreCase(work.getVolunteerID())) {
                                    volunteerName = v.toString();
                                }
                            }

                            Label lblAction = new Label("");
                            if (work.getWorkStatus().equalsIgnoreCase("in progress")) {
                                lblAction = new Label("Signed up for " + work.toString());
                            } else if (work.getWorkStatus().equalsIgnoreCase("completed")) {
                                lblAction = new Label("Completed " + work.toString());
                            }
                            // Add our Work details
                            vBox.getChildren().addAll(
                                    new Label(volunteerName),
                                    lblAction
                            );

                            // Add our VBox to the cellRoot
                            cellRoot.getChildren().add(vBox);

                            // Finally, set this cell to display our custom layout
                            setGraphic(cellRoot);
                        }
                    }
                };
            }
        });
        // Now, add our ListView to the root layout
        homePageList.setPrefSize(400, 400);
        root.getChildren().add(homePageList);
        homePane.add(root, 0, 0);
    }

    public void volunteerSummary() {
        // FX Controls
        Label lblVolList = new Label("BARK Volunteers");
        Button btnViewVol = new Button("View Summary for Selected Volunteer");
        ListView volunteerSumList = new ListView<>(currentVolunteers);
        VBox volSumBox = new VBox();

        volSumBox.setAlignment(Pos.CENTER);

        volunteerPane.add(volSumBox, 0, 0);
        volSumBox.setSpacing(10);
        volSumBox.setPadding(new Insets(10, 20, 10, 20));
        volSumBox.getChildren().addAll(lblVolList, volunteerSumList, btnViewVol);

        // View summary button action
        btnViewVol.setOnAction(e -> {
            try {
                Volunteer volunteer = (Volunteer) volunteerSumList.getSelectionModel().getSelectedItem();
                viewSummary(volunteer);
            } catch (NullPointerException npe) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR,
                        "You must select a volunteer.",
                        ButtonType.OK);
                noSelection.show();
            }
        });
    }

    public void viewSummary(Volunteer volunteer) {
        GridPane viewSummaryPane = new GridPane();
        Label lblName = new Label("Volunteer:");
        Label lblSpecialization = new Label("Specialization:");
        Label lblWorkHistory = new Label("Recent Work History:");
        Label lblCompleteEvents = new Label("Recent BARK Events:");
        Label lblMileage = new Label("Mileage:");
        Label lblHours = new Label("Total Hours Worked To-Date:");
        Text txtName = new Text();
        Text txtSpecialization = new Text();
        Text txtMileage = new Text();
        Text txtHours = new Text();
        ObservableList<Work> workHistory = FXCollections.observableArrayList();
        ObservableList<Work> eventHistory = FXCollections.observableArrayList();
        ListView workHistoryList = new ListView<>(workHistory);
        ListView eventHistoryList = new ListView<>(eventHistory);
        HBox nameBox = new HBox(lblName, txtName);
        HBox specialBox = new HBox(lblSpecialization, txtSpecialization);
        HBox mileageBox = new HBox(lblMileage, txtMileage);
        HBox hoursBox = new HBox(lblHours, txtHours);
        VBox workHistoryBox = new VBox();
        VBox eventHistoryBox = new VBox();
        VBox volStatsVbox = new VBox();

        viewSummaryPane.setAlignment(Pos.CENTER);
        viewSummaryPane.add(volStatsVbox, 0, 0);
        viewSummaryPane.add(workHistoryBox, 0, 1);
        viewSummaryPane.add(eventHistoryBox, 1, 1);

        workHistoryList.setPrefHeight(200);
        eventHistoryBox.setPrefHeight(200);
        nameBox.setSpacing(10);
        specialBox.setSpacing(10);
        mileageBox.setSpacing(10);
        hoursBox.setSpacing(10);

        // new Insets(top, left, bottom, right)
        volStatsVbox.setSpacing(10);
        volStatsVbox.setPadding(new Insets(10, 20, 30, 20));
        volStatsVbox.getChildren().addAll(nameBox, specialBox, mileageBox, hoursBox);

        workHistoryBox.setSpacing(10);
        workHistoryBox.setPadding(new Insets(10, 20, 10, 20));
        workHistoryBox.getChildren().addAll(lblWorkHistory, workHistoryList);

        eventHistoryBox.setSpacing(10);
        eventHistoryBox.setPadding(new Insets(10, 20, 10, 20));
        eventHistoryBox.getChildren().addAll(lblCompleteEvents, eventHistoryList);

        txtName.setText(volunteer.toString());
        txtSpecialization.setText(volunteer.getSpecialization());
        txtMileage.setText(String.valueOf(Drives.returnTotalMiles(volunteer.getVolunteerID())));
        txtHours.setText(String.valueOf(volunteer.getTotalHours()));

        workHistoryList.getItems().clear();
        eventHistoryList.getItems().clear();
        for (Work w : Work.workList) {
            if (w.getWorkStatus().equalsIgnoreCase("completed")) {
                if (volunteer.getVolunteerID().equalsIgnoreCase(w.getVolunteerID())) {
                    if (w.getJobID() != null && w.getEventID() == null) {
                        workHistory.add(w);
                    } else if (w.getEventID() != null && w.getJobID() == null) {
                        eventHistory.add(w);
                    }
                }
            }
        }
        workHistoryList.setItems(workHistory);
        eventHistoryList.setItems(eventHistory);

        Stage primaryStage = new Stage();
        Scene primaryScene = new Scene(viewSummaryPane, 900, 550);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Volunteer Summary for " + volunteer.toString());
        primaryStage.show();
    }

    public void populateJobsTable(Volunteer currentUser) {
        for (Job j : Job.jobList) {
            j.setLocationName(Location.reutrnLocationName(j.getLocationID()));
        }

        // Populate job table data
        jobTable.setItems(jobData);
        TableColumn tblcJobName = new TableColumn("Name");
        TableColumn tblcJobType = new TableColumn("Type");
        TableColumn tblcJobLocation = new TableColumn("Location");
        TableColumn tblcJobNotes = new TableColumn("Notes");

        tblcJobName.setCellValueFactory(new PropertyValueFactory<Job, String>("jobName"));
        tblcJobType.setCellValueFactory(new PropertyValueFactory<Job, Double>("jobType"));
        tblcJobLocation.setCellValueFactory(new PropertyValueFactory<Job, String>("locationName"));
        tblcJobNotes.setCellValueFactory(new PropertyValueFactory<Job, String>("jobNotes"));

        tblcJobName.setMinWidth(150);
        tblcJobType.setMinWidth(150);
        tblcJobLocation.setMinWidth(150);
        tblcJobNotes.setMinWidth(250);

        jobTable.getColumns().addAll(tblcJobName, tblcJobType, tblcJobLocation, tblcJobNotes);

    }

    public void populateEventsTable(Volunteer currentUser) {
        for (Event e : Event.eventList) {
            e.setLocationName(Location.reutrnLocationName(e.getLocationID()));
        }

        // Populate Event table data
        eventTable.setItems(eventData);
        TableColumn tblcEventName = new TableColumn("Name");
        TableColumn tblcEventDate = new TableColumn("Date");
        TableColumn tblcEventTime = new TableColumn("Time");
        TableColumn tblcEventLocation = new TableColumn("Location");
        TableColumn tblcMaxVolunteers = new TableColumn("Max Volunteers");
        TableColumn tblcSpotsLeft = new TableColumn("Spots Left");
        TableColumn tblcEventDescription = new TableColumn("Description");

        tblcEventName.setCellValueFactory(new PropertyValueFactory<Event, String>("eventName"));
        tblcEventDate.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDate"));
        tblcEventTime.setCellValueFactory(new PropertyValueFactory<Event, String>("eventTime"));
        tblcEventLocation.setCellValueFactory(new PropertyValueFactory<Event, String>("locationName"));
        tblcMaxVolunteers.setCellValueFactory(new PropertyValueFactory<Event, Integer>("maxVolunteers"));
        tblcSpotsLeft.setCellValueFactory(new PropertyValueFactory<Event, Integer>("spotsLeft"));
        tblcEventDescription.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDescription"));

        eventTable.getColumns().addAll(tblcEventName, tblcEventDate, tblcEventTime, tblcEventLocation, tblcMaxVolunteers, tblcSpotsLeft, tblcEventDescription);

        tblcEventName.setMinWidth(150);
        tblcEventDescription.setMinWidth(250);
        tblcMaxVolunteers.setMinWidth(120);

    }

    public void addJobTab() {
        Label lblJobID = new Label("Job ID:");
        Label lblJobName = new Label("Name:");
        Label lblJobType = new Label("Type:");
        Label lblJoblocation = new Label("Location:");
        Label lblJobNotes = new Label("Notes:");
        Label lblCurrentJobs = new Label("Current Jobs:");
        Text txtJobID = new Text();
        TextField txtJobName = new TextField();
        TextField txtJobType = new TextField();
        ComboBox comboJobLocation = new ComboBox<>(locationNames);
        TextField txtJobNotes = new TextField();
        Button btnAddNewJob = new Button("Add Job");
        Button btnEditJob = new Button("Edit Selected Job");
        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        HBox idHBox = new HBox(lblJobID, txtJobID);
        HBox nameHBox = new HBox(lblJobName, txtJobName);
        HBox typeHBox = new HBox(lblJobType, txtJobType);
        HBox jobLocationHBox = new HBox(lblJoblocation, comboJobLocation);
        HBox jobNotesHBox = new HBox(lblJobNotes, txtJobNotes);

        adminJobsPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.CENTER);
        rightVBox.setAlignment(Pos.CENTER);

        adminJobsPane.add(leftVBox, 0, 0);
        adminJobsPane.add(rightVBox, 1, 0);

        idHBox.setSpacing(10);
        nameHBox.setSpacing(10);
        typeHBox.setSpacing(10);
        jobLocationHBox.setSpacing(10);
        jobNotesHBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idHBox, nameHBox, typeHBox, jobLocationHBox, jobNotesHBox, btnAddNewJob);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblCurrentJobs, currentJobsList, btnEditJob);

        txtJobID.setText("job" + Job.jobCount);

        // Add Job button action
        btnAddNewJob.setOnAction(e -> {
            Job tempJob;
            if (btnAddNewJob.getText().equalsIgnoreCase("Save Changes")) {
                for (Job j : Job.jobList) {
                    if (j.getJobID().equalsIgnoreCase(txtJobID.getText())) {
                        tempJob = Job.returnJobObject(txtJobID.getText());
                        tempJob.setJobName(txtJobName.getText());
                        tempJob.setJobType(txtJobType.getText());
                        tempJob.setLocationID(Location.returnLocationID((String) comboJobLocation.getValue()));
                        tempJob.setJobNotes(txtJobNotes.getText());
                    }
                }
            } else {
                tempJob = new Job(
                        txtJobID.getText(),
                        txtJobName.getText(),
                        txtJobType.getText(),
                        Location.returnLocationID((String) comboJobLocation.getValue()),
                        txtJobNotes.getText()
                );
                Job.jobList.add(tempJob);
            }
            jobData.clear();
            currentJobsList.getItems().clear();
            for (Job j : Job.jobList) {
                jobData.add(j);
            }
            txtJobID.setText("job" + Job.jobCount);
            txtJobName.clear();
            txtJobType.clear();
            comboJobLocation.setValue("");
            txtJobNotes.clear();
            if (btnAddNewJob.getText().equalsIgnoreCase("Save Changes")) {
                Alert confirmEditJob = new Alert(Alert.AlertType.CONFIRMATION,
                        "Changes to job have been saved.",
                        ButtonType.OK);
                confirmEditJob.show();
            } else {
                Alert confirmAddJob = new Alert(Alert.AlertType.CONFIRMATION,
                        "Jobs have been updated.",
                        ButtonType.OK);
                confirmAddJob.show();
            }
            btnAddNewJob.setText("Add Job");
        });

        // Edit Button actions
        btnEditJob.setOnAction(e -> {
            Job selectedJob = currentJobsList.getSelectionModel().getSelectedItem();
            txtJobID.setText(selectedJob.getJobID());
            txtJobName.setText(selectedJob.getJobName());
            txtJobType.setText(selectedJob.getJobType());
            comboJobLocation.setValue(Location.reutrnLocationName(selectedJob.getLocationID()));
            txtJobNotes.setText(selectedJob.getJobNotes());
            btnAddNewJob.setText("Save Changes");
        });

    }

    public void addEventsTab() {
        // FX Controls
        Label lblCurrentEvents = new Label("Current Events:");
        Label lblEventID = new Label("Event ID:");
        Label lblEventName = new Label("Name:");
        Label lblEventDate = new Label("Date:");
        Label lblEventTime = new Label("Time:");
        Label lblMaxVolunteers = new Label("Max Volunteers:");
        Label lblEventDescription = new Label("Description:");
        Label lblEventLocation = new Label("Location:");
        Text txtEventID = new Text();
        TextField txtEventName = new TextField();
        TextField txtEventDate = new TextField();
        TextField txtEventTime = new TextField();
        TextField txtMaxVolunteers = new TextField();
        TextField txtEventDescription = new TextField();
        ComboBox comboLocation = new ComboBox<>(locationNames);
        Button btnAddNewEvent = new Button("Add Event");
        Button btnEdit = new Button("Edit Selected Event");
        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        HBox idHBox = new HBox(lblEventID, txtEventID);
        HBox nameHBox = new HBox(lblEventName, txtEventName);
        HBox eventDateHBox = new HBox(lblEventDate, txtEventDate);
        HBox timeHBox = new HBox(lblEventTime, txtEventTime);
        HBox maxVolHBox = new HBox(lblMaxVolunteers, txtMaxVolunteers);
        HBox descHBox = new HBox(lblEventDescription, txtEventDescription);
        HBox eventLocationHBox = new HBox(lblEventLocation, comboLocation);

        adminEventsPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        adminEventsPane.add(leftVBox, 0, 0);
        adminEventsPane.add(rightVBox, 1, 0);

        idHBox.setSpacing(10);
        nameHBox.setSpacing(10);
        eventDateHBox.setSpacing(10);
        timeHBox.setSpacing(10);
        maxVolHBox.setSpacing(10);
        descHBox.setSpacing(10);
        eventLocationHBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idHBox, nameHBox, eventDateHBox, timeHBox, maxVolHBox, descHBox, eventLocationHBox, btnAddNewEvent);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblCurrentEvents, currentEventsList, btnEdit);

        txtEventID.setText("event" + Event.eventCount);

        // Add Event button action
        btnAddNewEvent.setOnAction(e -> {
            Event tempEvent;
            if (btnAddNewEvent.getText().equalsIgnoreCase("Save Changes")) {
                for (Event ev : Event.eventList) {
                    if (ev.getEventID().equalsIgnoreCase(txtEventID.getText())) {
                        tempEvent = Event.returnEventObject(txtEventID.getText());
                        tempEvent.setEventName(txtEventName.getText());
                        tempEvent.setEventDate(txtEventDate.getText());
                        tempEvent.setEventTime(txtEventTime.getText());
                        tempEvent.setMaxVolunteers(Integer.valueOf(txtMaxVolunteers.getText()));
                        tempEvent.setEventDescription(txtEventDescription.getText());
                        tempEvent.setLocationID(Location.returnLocationID((String) comboLocation.getValue()));
                    }
                }
            } else {
                tempEvent = new Event(
                        txtEventID.getText(),
                        txtEventName.getText(),
                        txtEventDate.getText(),
                        txtEventTime.getText(),
                        Integer.valueOf(txtMaxVolunteers.getText()),
                        0,
                        txtEventDescription.getText(),
                        Location.returnLocationID((String) comboLocation.getValue())
                );
                Event.eventList.add(tempEvent);
            }
            eventData.clear();
            currentEventsList.getItems().clear();
            for (Event ev : Event.eventList) {
                eventData.add(ev);
            }
            txtEventID.setText("event" + Event.eventCount);
            txtEventName.clear();
            txtEventDate.clear();
            txtEventTime.clear();
            txtMaxVolunteers.clear();
            txtEventDescription.clear();
            comboLocation.setValue("");
            if (btnAddNewEvent.getText().equalsIgnoreCase("Save Changes")) {
                Alert confirmEditEvent = new Alert(Alert.AlertType.CONFIRMATION,
                        "Changes to event have been saved.",
                        ButtonType.OK);
                confirmEditEvent.show();
            } else {
                Alert confirmAddEvent = new Alert(Alert.AlertType.CONFIRMATION,
                        "New event has been added to the list.",
                        ButtonType.OK);
                confirmAddEvent.show();
            }
            btnAddNewEvent.setText("Add Event");
        });

        // Edit Button actions
        btnEdit.setOnAction(e -> {
            Event selectedEvent = currentEventsList.getSelectionModel().getSelectedItem();
            txtEventID.setText(selectedEvent.getEventID());
            txtEventName.setText(selectedEvent.getEventName());
            txtEventDate.setText(selectedEvent.getEventDate());
            txtEventTime.setText(selectedEvent.getEventTime());
            txtMaxVolunteers.setText(String.valueOf(selectedEvent.getMaxVolunteers()));
            txtEventDescription.setText(selectedEvent.getEventDescription());
            comboLocation.setValue(Location.reutrnLocationName(selectedEvent.getLocationID()));
            btnAddNewEvent.setText("Save Changes");
        });
    }

    public void addLocationsTab() {
        // FX Controls
        Label lblCurrentLocations = new Label("Current Locations:");
        Label lblLocationID = new Label("Location ID:");
        Label lblLocationName = new Label("Location Name:");
        Label lblLocationStreet = new Label("Street Address:");
        Label lblLocationCity = new Label("City:");
        Label lblLocationState = new Label("State:");
        Label lblLocationZip = new Label("Zip Code:");
        Label lblLocationType = new Label("Location Type:");
        Text txtLocationID = new Text();
        TextField txtLocationName = new TextField();
        TextField txtLocationStreet = new TextField();
        TextField txtLocationCity = new TextField();
        TextField txtLocationState = new TextField();
        TextField txtLocationZip = new TextField();
        TextField txtLocationType = new TextField();
        Button btnAddLocation = new Button("Add New Location");
        Button btnEdit = new Button("Edit Selected Location");

        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        HBox idHBox = new HBox(lblLocationID, txtLocationID);
        HBox nameHBox = new HBox(lblLocationName, txtLocationName);
        HBox streetHBox = new HBox(lblLocationStreet, txtLocationStreet);
        HBox cityHBox = new HBox(lblLocationCity, txtLocationCity);
        HBox stateHBox = new HBox(lblLocationState, txtLocationState);
        HBox zipHBox = new HBox(lblLocationZip, txtLocationZip);
        HBox typeHBox = new HBox(lblLocationType, txtLocationType);

        adminLocationsPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        adminLocationsPane.add(leftVBox, 0, 0);
        adminLocationsPane.add(rightVBox, 1, 0);

        idHBox.setSpacing(10);
        nameHBox.setSpacing(10);
        streetHBox.setSpacing(10);
        cityHBox.setSpacing(10);
        stateHBox.setSpacing(10);
        zipHBox.setSpacing(10);
        typeHBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idHBox, nameHBox, typeHBox, streetHBox, cityHBox, stateHBox, zipHBox, btnAddLocation);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblCurrentLocations, currentLocationsList, btnEdit);

        txtLocationID.setText("location" + Location.locationCount);

        // Add Location button action
        btnAddLocation.setOnAction(e -> {
            Location tempLocation;
            if (btnAddLocation.getText().equalsIgnoreCase("Save Changes")) {
                for (Location l : Location.locationList) {
                    if (l.getLocationID().equalsIgnoreCase(txtLocationID.getText())) {
                        tempLocation = Location.returnLocationObject(txtLocationID.getText());
                        tempLocation.setName(txtLocationName.getText());
                        tempLocation.setStreet(txtLocationStreet.getText());
                        tempLocation.setCity(txtLocationCity.getText());
                        tempLocation.setState(txtLocationState.getText());
                        tempLocation.setZip(Integer.valueOf(txtLocationZip.getText()));
                        tempLocation.setLtype(txtLocationType.getText());
                    }
                }
            } else {
                tempLocation = new Location(
                        txtLocationID.getText(),
                        txtLocationName.getText(),
                        txtLocationStreet.getText(),
                        txtLocationCity.getText(),
                        txtLocationState.getText(),
                        Integer.valueOf(txtLocationZip.getText()),
                        txtLocationType.getText()
                );
                Location.locationList.add(tempLocation);
            }

            txtLocationID.setText("location" + Location.locationCount);
            txtLocationName.clear();
            txtLocationStreet.clear();
            txtLocationCity.clear();
            txtLocationState.clear();
            txtLocationZip.clear();
            txtLocationType.clear();
            if (btnAddLocation.getText().equalsIgnoreCase("Save Changes")) {
                Alert confirmEditLocation = new Alert(Alert.AlertType.CONFIRMATION,
                        "Changes to location have been saved.",
                        ButtonType.OK);
                confirmEditLocation.show();
            } else {
                Alert confirmAddLocation = new Alert(Alert.AlertType.CONFIRMATION,
                        "New Location has been added to the list.",
                        ButtonType.OK);
                confirmAddLocation.show();
            }
            btnAddLocation.setText("Add New Location");
        });

        // Edit Button actions
        btnEdit.setOnAction(e -> {
            Location selectedLocation = currentLocationsList.getSelectionModel().getSelectedItem();
            txtLocationID.setText(selectedLocation.getLocationID());
            txtLocationName.setText(selectedLocation.getName());
            txtLocationStreet.setText(selectedLocation.getStreet());
            txtLocationCity.setText(selectedLocation.getCity());
            txtLocationState.setText(selectedLocation.getState());
            txtLocationZip.setText(String.valueOf(selectedLocation.getZip()));
            txtLocationType.setText(selectedLocation.getType());
            btnAddLocation.setText("Save Changes");
        });
    }

    public void reportsTab() {

        // FX Controls
        Button btnViewHours = new Button("View Volunteer Hours");
        Button btnViewRegistered = new Button("View Volunteers Registered for Events");
        VBox reportButtonVBox = new VBox();

        adminReportsPane.setAlignment(Pos.CENTER);
        reportButtonVBox.setAlignment(Pos.CENTER);

        adminReportsPane.add(reportButtonVBox, 0, 0);
        reportButtonVBox.setSpacing(10);
        reportButtonVBox.setPadding(new Insets(10, 20, 10, 20));
        reportButtonVBox.getChildren().addAll(btnViewHours, btnViewRegistered);

        btnViewHours.setOnAction(e -> {
            // displays report of volunteer hours
        });

        btnViewRegistered.setOnAction(e -> {
            // displays report of volunteers registered for events
            eventVolunteerReport();
        });
    }

    public void eventVolunteerReport() {

        Label eventLBL = new Label("Event Name(s):");
        Label volunteerLBL = new Label("Volunteer Name(s)");
        ObservableList<Event> eventNames = FXCollections.observableArrayList(Event.eventList);
        ObservableList<Event> volunteerNames = FXCollections.observableArrayList();
        ListView eventLV = new ListView<>(eventNames);
        ListView volunteerLV = new ListView<>(volunteerNames);
        Button showVolunteersBT = new Button("Show Volunteer(s)");
        VBox eventVB = new VBox();
        VBox volunteerVB = new VBox();
        HBox buttonHB = new HBox();

        Stage stage = new Stage();
        stage.setTitle("Volunteers Registered for Events Report");
        GridPane reportEVPane = new GridPane();

        reportEVPane.add(eventVB, 0, 0);
        eventVB.setAlignment(Pos.CENTER);
        eventVB.setPadding(new Insets(10, 10, 0, 20));
        eventVB.getChildren().addAll(eventLBL, eventLV);

        reportEVPane.add(volunteerVB, 4, 0);
        volunteerVB.setAlignment(Pos.CENTER);
        volunteerVB.setPadding(new Insets(22, 20, 20, 10));
        volunteerVB.getChildren().addAll(volunteerLBL, volunteerLV);

        reportEVPane.add(buttonHB, 0, 6);
        buttonHB.setAlignment(Pos.CENTER);
        buttonHB.setPadding(new Insets(0, 20, 20, 20));
        buttonHB.getChildren().addAll(showVolunteersBT);

        //2):Populate Corresponding Volunteer Name
        showVolunteersBT.setOnAction(e -> {

        });

        Scene scene = new Scene(reportEVPane, 500, 500);
        stage.setScene(scene);
        stage.show();

    }

    public void manageVolunteersTab() {

        // FX Controls
        Label lblConditional = new Label("Volunteers Awaiting Approval:");
        Button btnReview = new Button("Review Selected Applicant");
        Label lblCurrent = new Label("Current Volunteers:");
        Button btnEdit = new Button("Edit Selected Volunteer");
        Label lblInactive = new Label("Inactive Volunteers:");
        Button btnSetInactive = new Button("Set Selected Volunteer as Inactive");
        Button btnDetails = new Button("View Details for\nSelected Volunteer");
        Button btnSetActive = new Button("Set Selected Volunteer as Active");

        VBox leftVBox = new VBox();
        VBox middleVBox = new VBox();
        VBox rightVBox = new VBox();
        VBox currentVBox = new VBox(btnEdit, btnSetInactive);

        adminVolunteerPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.CENTER);
        middleVBox.setAlignment(Pos.CENTER);
        rightVBox.setAlignment(Pos.CENTER);

        adminVolunteerPane.add(leftVBox, 0, 0);
        adminVolunteerPane.add(middleVBox, 1, 0);
        adminVolunteerPane.add(rightVBox, 2, 0);

        currentVBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(lblConditional, conditionalVolList, btnReview);

        middleVBox.setSpacing(10);
        middleVBox.setPadding(new Insets(10, 20, 10, 20));
        middleVBox.getChildren().addAll(lblCurrent, currentVolList, currentVBox);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblInactive, inactiveVolList, btnDetails, btnSetActive);

        // adding each volunteer to corresponding list
        for (Volunteer v : Volunteer.volunteerArrayList) {
            if (v.getStatus().equals("conditional")) {
                conditionalVolunteers.add(v);
            } else if (v.getStatus().equals("inactive")) {
                inactiveVolunteers.add(v);
            } else {
                currentVolunteers.add(v);
            }
        }
        conditionalVolList.setItems(conditionalVolunteers);
        currentVolList.setItems(currentVolunteers);
        inactiveVolList.setItems(inactiveVolunteers);

        // Review Button action
        btnReview.setOnAction(e -> {
            Volunteer selectedVolunteer = conditionalVolList.getSelectionModel().getSelectedItem();
            try {
                reviewApplication(selectedVolunteer);
            } catch (NullPointerException npe) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR,
                        "You must select a volunteer.",
                        ButtonType.OK);
                noSelection.show();
                npe.toString();
            }
        });

        // Set Inactive Button action
        btnSetInactive.setOnAction(e -> {
            Volunteer selectedVolunteer = currentVolList.getSelectionModel().getSelectedItem();
            try {
                selectedVolunteer.setStatus("inactive");
                currentVolunteers.remove(selectedVolunteer);
                inactiveVolunteers.add(selectedVolunteer);
            } catch (NullPointerException npe) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR,
                        "You must select a volunteer.",
                        ButtonType.OK);
                noSelection.show();
                npe.toString();
            }
        });

        // Edit Button actions
        btnEdit.setOnAction(e -> {
            Volunteer selectedVolunteer = currentVolList.getSelectionModel().getSelectedItem();
            try {
                editAccountWindow(selectedVolunteer);
            } catch (NullPointerException npe) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR,
                        "You must select a volunteer.",
                        ButtonType.OK);
                noSelection.show();
                npe.toString();
            }
        });

        // View Details for inactive volunteer action
        btnDetails.setOnAction(e -> {
            Volunteer selectedVolunteer = inactiveVolList.getSelectionModel().getSelectedItem();
            try {
                reviewApplication(selectedVolunteer);
            } catch (NullPointerException npe) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR,
                        "You must select a volunteer.",
                        ButtonType.OK);
                noSelection.show();
                npe.toString();
            }
        });
        
        // Set Active Button action
        btnSetActive.setOnAction(e -> {
            Volunteer selectedVolunteer = inactiveVolList.getSelectionModel().getSelectedItem();
            try {
                selectedVolunteer.setStatus("active");
                inactiveVolunteers.remove(selectedVolunteer);
                currentVolunteers.add(selectedVolunteer);
            } catch (NullPointerException npe) {
                Alert noSelection = new Alert(Alert.AlertType.ERROR,
                        "You must select a volunteer.",
                        ButtonType.OK);
                noSelection.show();
                npe.toString();
            }
        });

    }

    public void animalsTab() {
        // FX Controls
        Label lblCurrentAnimals = new Label("Current Animals:");
        Label lblAnimalID = new Label("Animal ID:");
        Label lblAnimalName = new Label("Name:");
        Label lblAnimalSpecies = new Label("Species:");
        Label lblAnimalBreed = new Label("Breed:");
        Label lblAnimalAge = new Label("Age:");
        Text txtAnimalID = new Text("Animal ID:");
        TextField txtAnimalName = new TextField();
        TextField txtAnimalSpecies = new TextField();
        TextField txtAnimalBreed = new TextField();
        TextField txtAnimalAge = new TextField();
        Button btnAdd = new Button("Add New Animal");
        Button btnEdit = new Button("Edit Selected Animal");

        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        HBox idHBox = new HBox(lblAnimalID, txtAnimalID);
        HBox nameHBox = new HBox(lblAnimalName, txtAnimalName);
        HBox speciesHBox = new HBox(lblAnimalSpecies, txtAnimalSpecies);
        HBox breedHBox = new HBox(lblAnimalBreed, txtAnimalBreed);
        HBox ageHBox = new HBox(lblAnimalAge, txtAnimalAge);

        adminAnimalPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        adminAnimalPane.add(leftVBox, 0, 0);
        adminAnimalPane.add(rightVBox, 1, 0);
        adminAnimalPane.add(btnAdd, 1, 4);

        idHBox.setSpacing(10);
        nameHBox.setSpacing(10);
        speciesHBox.setSpacing(10);
        breedHBox.setSpacing(10);
        ageHBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idHBox, nameHBox, speciesHBox, breedHBox, ageHBox, btnAdd);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblCurrentAnimals, currentAnimalsList, btnEdit);

        txtAnimalID.setText("animal" + Animal.animalCount);

        // Add Animal button action
        btnAdd.setOnAction(e -> {
            Animal tempAnimal;
            if (btnAdd.getText().equalsIgnoreCase("Save Changes")) {
                for (Animal a : Animal.animalList) {
                    if (a.getAnimalID().equalsIgnoreCase(txtAnimalID.getText())) {
                        tempAnimal = Animal.returnAnimalObject(txtAnimalID.getText());
                        tempAnimal.setAnimalName(txtAnimalName.getText());
                        tempAnimal.setAnimalSpecies(txtAnimalSpecies.getText());
                        tempAnimal.setAnimalBreed(txtAnimalBreed.getText());
                        tempAnimal.setAnimalAge(Integer.valueOf(txtAnimalAge.getText()));
                    }
                }
            } else {
                tempAnimal = new Animal(
                        txtAnimalID.getText(),
                        txtAnimalName.getText(),
                        txtAnimalSpecies.getText(),
                        txtAnimalBreed.getText(),
                        Integer.valueOf(txtAnimalAge.getText())
                );
                Animal.animalList.add(tempAnimal);
            }
            jobData.clear();
            for (Animal a : Animal.animalList) {
                currentAnimals.add(a);
            }
            txtAnimalID.setText("animal" + Animal.animalCount);
            txtAnimalName.clear();
            txtAnimalSpecies.clear();
            txtAnimalBreed.clear();
            txtAnimalAge.clear();
            if (btnAdd.getText().equalsIgnoreCase("Save Changes")) {
                Alert confirmEditAnimal = new Alert(Alert.AlertType.CONFIRMATION,
                        "Changes to animal have been saved.",
                        ButtonType.OK);
                confirmEditAnimal.show();
            } else {
                Alert confirmAddAnimal = new Alert(Alert.AlertType.CONFIRMATION,
                        "New animal has been added to the list.",
                        ButtonType.OK);
                confirmAddAnimal.show();
            }
            btnAdd.setText("Add New Animal");
        });

        // Edit Button actions
        btnEdit.setOnAction(e -> {
            Animal selectedAnimal = currentAnimalsList.getSelectionModel().getSelectedItem();
            txtAnimalID.setText(selectedAnimal.getAnimalID());
            txtAnimalName.setText(selectedAnimal.getAnimalName());
            txtAnimalSpecies.setText(selectedAnimal.getAnimalSpecies());
            txtAnimalBreed.setText(selectedAnimal.getAnimalBreed());
            txtAnimalAge.setText(String.valueOf(selectedAnimal.getAnimalAge()));
            btnAdd.setText("Save Changes");
        });
    }

    public void specializationsTab() {
        // FX Controls
        Label lblNew = new Label("Add New Specialization");
        TextField txtAdd = new TextField();
        Button btnAdd = new Button("Add -->");
        Label lblSpecializations = new Label("Current Specializations:");
        Button btnDelete = new Button("Delete Selected Specialization");

        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();

        adminSpecializationsPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        adminSpecializationsPane.add(leftVBox, 0, 0);
        adminSpecializationsPane.add(rightVBox, 1, 0);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(lblNew, txtAdd, btnAdd);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblSpecializations, specializationList, btnDelete);

        specializationList.setItems(specializations);

        // Add Button action
        btnAdd.setOnAction(e -> {
            Specialization newSpecial = new Specialization(txtAdd.getText());
            specializations.addAll(newSpecial.getSpecializationName());
            specializationList.setItems(specializations);
            newSpecial.writeSpecialization();
            txtAdd.clear();
        });

        // Delete Button action
        btnDelete.setOnAction(e -> {
            String selectedSpecial = specializationList.getSelectionModel().getSelectedItem();
            specializations.remove(selectedSpecial);
        });
    }

    public void editAccountWindow(Volunteer volunteer) {
        //     JavaFX Controls

        // Create GridPane
        GridPane editPane = new GridPane();

        // Labels, text fields/areas, button, combo box
        Label lblvolunteerID = new Label("Volunteer ID:");
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
        Label lblTotHours = new Label("Total Hours Worked To-Date:");

        Text txtvolunteerID = new Text();
        TextField txtFirstName = new TextField();
        TextField txtLastName = new TextField();
        TextField txtDateOfBirth = new TextField();
        TextField txtEmail = new TextField();
        TextField txtPhone = new TextField();
        TextField txtStreet = new TextField();
        TextField txtCity = new TextField();
        TextField txtState = new TextField();
        TextField txtZip = new TextField();
        TextArea txtInfo = new TextArea();
        Text txtTotHours = new Text();
        ComboBox<String> comboSpecialization = new ComboBox<>(specializations);
        TextArea txtExperience = new TextArea();
        Button submit = new Button("Save Changes");

        txtvolunteerID.setText(volunteer.getVolunteerID());
        txtFirstName.setText(volunteer.getFirstName());
        txtLastName.setText(volunteer.getLastName());
        txtDateOfBirth.setText(volunteer.getDateOfBirth());
        txtEmail.setText(volunteer.getEmail());
        txtPhone.setText(volunteer.getPhone());
        txtStreet.setText(volunteer.getStreet());
        txtCity.setText(volunteer.getCity());
        txtState.setText(volunteer.getState());
        txtZip.setText(String.valueOf(volunteer.getZip()));
        txtInfo.setText(volunteer.getPersonalInfo());
        comboSpecialization.valueProperty().setValue(volunteer.getSpecialization());
        txtExperience.setText(volunteer.getExperience());
        txtTotHours.setText(String.valueOf(volunteer.getTotalHours()));

        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        HBox idBox = new HBox(lblvolunteerID, txtvolunteerID);
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
        VBox infoBox = new VBox(lblInfo, txtInfo);
        VBox experienceBox = new VBox(lblExperience, txtExperience);
        HBox hoursBox = new HBox(lblTotHours, txtTotHours);

        editPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.TOP_LEFT);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        editPane.add(leftVBox, 0, 0);
        editPane.add(rightVBox, 1, 0);
        editPane.add(submit, 1, 20);

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
        hoursBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idBox, firstNameBox, lastNameBox, dateOfBirthBox, emailBox, phoneBox, specialBox, streetBox, cityBox, stateBox, zipBox, hoursBox);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(experienceBox, infoBox);

        Stage primaryStage = new Stage();
        Scene primaryScene = new Scene(editPane, 900, 550);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Edit Account");
        primaryStage.show();

        submit.setOnAction(e -> {
            Alert confirmChanges = new Alert(Alert.AlertType.CONFIRMATION,
                    "Account changes have been saved.",
                    ButtonType.OK);
            confirmChanges.show();
            //send query to update to DB
            sendDBCommand("UPDATE Volunteer "
                    + "SET FirstName = '" + txtFirstName.getText() + "', "
                    + "LastName = '" + txtLastName.getText() + "', "
                    + "DateOfBirth = '" + txtDateOfBirth.getText() + "', "
                    + "Email = '" + txtEmail.getText() + "', "
                    + "PhoneNumber = '" + txtPhone.getText() + "', "
                    + "Specialization = '" + comboSpecialization.getSelectionModel().getSelectedItem() + "', "
                    + "VolunteerStreet = '" + txtStreet.getText() + "', "
                    + "VolunteerCity = '" + txtCity.getText() + "', "
                    + "VolunteerState = '" + txtState.getText() + "', "
                    + "VolunteerZip = '" + Integer.parseInt(txtZip.getText()) + "', "
                    + "PersonalInfo = '" + txtInfo.getText() + "', "
                    + "Experience = '" + txtExperience.getText() + "' "
                    + "WHERE VolunteerID = '" + volunteer.getVolunteerID() + "'");

            //Update the instance object
            volunteer.setFirstName(txtFirstName.getText());
            volunteer.setLastName(txtLastName.getText());
            volunteer.setDateOfBirth(txtDateOfBirth.getText());
            volunteer.setEmail(txtEmail.getText());
            volunteer.setPhone(txtPhone.getText());
            volunteer.setSpecialization(comboSpecialization.getSelectionModel().getSelectedItem());
            volunteer.setStreet(txtStreet.getText());
            volunteer.setCity(txtCity.getText());
            volunteer.setState(txtState.getText());
            volunteer.setZip(Integer.parseInt(txtZip.getText()));
            volunteer.setPersonalInfo(txtInfo.getText());
            volunteer.setExperience(txtExperience.getText());
        });
    }

    // Method for reviewing applications window
    public void reviewApplication(Volunteer volunteer) {
        //     JavaFX Controls

        // Create GridPane
        GridPane reviewPane = new GridPane();

        // Labels, text fields/areas, button, combo box
        Label lblvolunteerID = new Label("Volunteer ID:");
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

        Text txtvolunteerID = new Text();
        Text txtFirstName = new Text();
        Text txtLastName = new Text();
        Text txtDateOfBirth = new Text();
        Text txtEmail = new Text();
        Text txtPhone = new Text();
        Text txtStreet = new Text();
        Text txtCity = new Text();
        Text txtState = new Text();
        Text txtZip = new Text();
        Text txtInfo = new Text();

        ComboBox<String> comboSpecialization = new ComboBox<>(specializations);

        Text txtExperience = new Text();

        Button approve = new Button("Approve Volunteer");
        Button deny = new Button("Deny Volunteer");

        txtvolunteerID.setText(volunteer.getVolunteerID());
        txtFirstName.setText(volunteer.getFirstName());
        txtLastName.setText(volunteer.getLastName());
        txtDateOfBirth.setText(volunteer.getDateOfBirth());
        txtEmail.setText(volunteer.getEmail());
        txtPhone.setText(volunteer.getPhone());
        txtStreet.setText(volunteer.getStreet());
        txtCity.setText(volunteer.getCity());
        txtState.setText(volunteer.getState());
        txtZip.setText(String.valueOf(volunteer.getZip()));
        txtInfo.setText(volunteer.getPersonalInfo());
        comboSpecialization.valueProperty().setValue(volunteer.getSpecialization());
        txtExperience.setText(volunteer.getExperience());

        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        HBox idBox = new HBox(lblvolunteerID, txtvolunteerID);
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
        HBox bottomHBox = new HBox();

        reviewPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.TOP_LEFT);
        rightVBox.setAlignment(Pos.TOP_CENTER);
        bottomHBox.setAlignment(Pos.BOTTOM_CENTER);

        reviewPane.add(leftVBox, 0, 0);
        reviewPane.add(rightVBox, 1, 0);
        if (volunteer.getStatus().equalsIgnoreCase("conditional")) {
            reviewPane.add(bottomHBox, 1, 20);
        }

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

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idBox, firstNameBox, lastNameBox, dateOfBirthBox, emailBox, phoneBox, specialBox, streetBox, cityBox, stateBox, zipBox, infoBox);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblExperience, txtExperience);

        bottomHBox.setSpacing(10);
        bottomHBox.setPadding(new Insets(10, 20, 10, 20));
        bottomHBox.getChildren().addAll(approve, deny);

        Stage primaryStage = new Stage();
        Scene primaryScene = new Scene(reviewPane, 900, 550);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Review Volunteer");
        primaryStage.show();

        // Approve / Deny button actions
        approve.setOnAction(e -> {
            Alert confirmApprove = new Alert(Alert.AlertType.CONFIRMATION,
                    "Volunteer application has been approved.",
                    ButtonType.OK);
            volunteer.setStatus("Volunteer in Training");
            sendDBCommand("UPDATE Volunteer SET status = 'Volunteer in Training' WHERE volunteerID = '" + volunteer.getVolunteerID() + "'");
            confirmApprove.show();
            primaryStage.close();
            conditionalVolList.getItems().clear();
            currentVolList.getItems().clear();
            inactiveVolList.getItems().clear();
            for (Volunteer v : Volunteer.volunteerArrayList) {
                if (v.getStatus().equals("conditional")) {
                    conditionalVolunteers.add(v);
                } else if (v.getStatus().equals("inactive")) {
                    inactiveVolunteers.add(v);
                } else {
                    currentVolunteers.add(v);
                }
            }
            conditionalVolList.setItems(conditionalVolunteers);
            currentVolList.setItems(currentVolunteers);
            inactiveVolList.setItems(inactiveVolunteers);
        });
        deny.setOnAction(e -> {
            Alert confirmDeny = new Alert(Alert.AlertType.CONFIRMATION,
                    "Volunteer application has been denied.",
                    ButtonType.OK);
            Volunteer.volunteerArrayList.remove(volunteer);
            conditionalVolunteers.remove(volunteer);
            sendDBCommand("DELETE FROM Volunteer WHERE volunteerID = '" + volunteer.getVolunteerID() + "'");
            confirmDeny.show();
            primaryStage.close();
        });

    }

    // Method to read volunteer data from database
    public void readDatabaseData(Volunteer currentUser) {
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

            //Read Specialization data into Specialization objects
            String specializationQuery = "SELECT SPECIALIZATIONNAME FROM SPECIALIZATION";
            ResultSet dbSpecializations = commStmt.executeQuery(specializationQuery);
            while (dbSpecializations.next()) {
                Specialization dbSpecialization = new Specialization(
                        dbSpecializations.getNString("SPECIALIZATIONNAME"));

                Specialization.specializationList.add(dbSpecialization);
            }
            for (Specialization s : Specialization.specializationList) {
                specializations.add(s.getSpecializationName());
            }

            // Reading Job data into Job objects
            String jobQuery = "SELECT JOBID,JOBNAME,JOBTYPE,LOCATIONID,JOBNOTES FROM JOB";
            ResultSet dbJobs = commStmt.executeQuery(jobQuery);
            System.out.println(jobQuery);
            while (dbJobs.next()) {
                Job dbJob = new Job(
                        dbJobs.getNString("JOBID"),
                        dbJobs.getNString("JOBNAME"),
                        dbJobs.getNString("JOBTYPE"),
                        dbJobs.getNString("LOCATIONID"),
                        dbJobs.getNString("JOBNOTES")
                );
                Job.jobList.add(dbJob);
                currentJobsList.getItems().clear();
                jobData.clear();
                for (Job j : Job.jobList) {
                    jobData.add(j);
                }
            }

            // Reading Event data into Event objects
            String eventQuery = "SELECT EVENTID,EVENTNAME,EVENTDATE,EVENTTIME,MAXVOLUNTEERS,REGISTEREDVOLUNTEERS,EVENTDESCRIPTION,LOCATIONID FROM EVENT E";
            ResultSet dbEvents = commStmt.executeQuery(eventQuery);
            System.out.println(eventQuery);
            while (dbEvents.next()) {
                Event dbEvent = new Event(
                        dbEvents.getNString("EVENTID"),
                        dbEvents.getNString("EVENTNAME"),
                        dbEvents.getNString("EVENTDATE"),
                        dbEvents.getNString("EVENTTIME"),
                        dbEvents.getInt("MAXVOLUNTEERS"),
                        dbEvents.getInt("REGISTEREDVOLUNTEERS"),
                        dbEvents.getNString("EVENTDESCRIPTION"),
                        dbEvents.getNString("LOCATIONID")
                );
                Event.eventList.add(dbEvent);
                eventData.clear();
                currentEventsList.getItems().clear();
                for (Event e : Event.eventList) {
                    eventData.add(e);
                }
            }

            // Reading Animal data into Animal objects
            String animalQuery = "SELECT ANIMALID,ANIMALNAME,ANIMALSPECIES,ANIMALBREED,ANIMALAGE FROM ANIMAL";
            ResultSet dbAnimals = commStmt.executeQuery(animalQuery);
            System.out.println(animalQuery);
            while (dbAnimals.next()) {
                Animal dbAnimal = new Animal(
                        dbAnimals.getNString("ANIMALID"),
                        dbAnimals.getNString("ANIMALNAME"),
                        dbAnimals.getNString("ANIMALSPECIES"),
                        dbAnimals.getNString("ANIMALBREED"),
                        dbAnimals.getInt("ANIMALAGE")
                );
                Animal.animalList.add(dbAnimal);
            }
            currentAnimalsList.getItems().clear();
            for (Animal a : Animal.animalList) {
                currentAnimals.add(a);
            }

            // Reading Location data into Location objects
            String locationQuery = "SELECT LOCATIONID,LOCATIONNAME,LOCATIONSTREET,LOCATIONCITY,LOCATIONSTATE,LOCATIONZIP,LOCATIONTYPE FROM LOCATION";
            ResultSet dbLocations = commStmt.executeQuery(locationQuery);
            System.out.println(locationQuery);
            while (dbLocations.next()) {
                Location dbLocation = new Location(
                        dbLocations.getNString("LOCATIONID"),
                        dbLocations.getNString("LOCATIONNAME"),
                        dbLocations.getNString("LOCATIONSTREET"),
                        dbLocations.getNString("LOCATIONCITY"),
                        dbLocations.getNString("LOCATIONSTATE"),
                        dbLocations.getInt("LOCATIONZIP"),
                        dbLocations.getNString("LOCATIONTYPE")
                );
                Location.locationList.add(dbLocation);
            }
            currentLocationsList.getItems().clear();
            for (Location l : Location.locationList) {
                currentLocations.add(l);
                locationNames.add(l.getName());
            }

            // Reading Drive data into Drives objects
            String driveQuery = "SELECT DRIVEID,VOLUNTEERID,LOCATIONID,MILES,DRIVEDATE,DRIVENOTES FROM DRIVES";
            ResultSet dbDrives = commStmt.executeQuery(driveQuery);
            System.out.println(driveQuery);
            while (dbDrives.next()) {
                Drives dbDrive = new Drives(
                        dbDrives.getNString("DRIVEID"),
                        dbDrives.getNString("VOLUNTEERID"),
                        dbDrives.getNString("LOCATIONID"),
                        dbDrives.getDouble("MILES"),
                        dbDrives.getNString("DRIVEDATE"),
                        dbDrives.getNString("DRIVENOTES")
                );
                Drives.drivesList.add(dbDrive);
            }
            driveHistoryList.getItems().clear();
            for (Drives d : Drives.drivesList) {
                if (d.getVolunteerID().equalsIgnoreCase(currentUser.getVolunteerID())) {
                    drivesData.add(d);
                }
            }

            // Reading Work data into Work objects
            String workQuery = "SELECT WORKID,WORKSTATUS,VOLUNTEERID,JOBID,EVENTID,ANIMALID FROM WORK";
            ResultSet dbWorks = commStmt.executeQuery(workQuery);
            System.out.println(workQuery);
            while (dbWorks.next()) {
                Work dbWork = new Work(
                        dbWorks.getNString("WORKID"),
                        dbWorks.getNString("WORKSTATUS"),
                        dbWorks.getNString("VOLUNTEERID"),
                        dbWorks.getNString("JOBID"),
                        dbWorks.getNString("EVENTID"),
                        dbWorks.getNString("ANIMALID")
                );
                Work.workList.add(dbWork);
            }
            for (Work w : Work.workList) {
                System.out.println(w.getWorkID());
            }

            // Reading Shift data into Shift objects
            String shiftQuery = "SELECT SHIFTID,CLOCKIN,CLOCKOUT,VOLUNTEERID FROM SHIFT";
            ResultSet dbShifts = commStmt.executeQuery(shiftQuery);
            System.out.println(shiftQuery);
            while (dbShifts.next()) {
                Shift dbShift = new Shift(
                        dbShifts.getNString("SHIFTID"),
                        dbShifts.getObject("CLOCKIN", Instant.class),
                        dbShifts.getObject("CLOCKOUT", Instant.class),
                        dbShifts.getNString("VOLUNTEERID")
                );
                Shift.shiftList.add(dbShift);
            }
            for (Shift s : Shift.shiftList) {
                System.out.println(s.getShiftID() + " " + s.getClockIn() + " " + s.getClockOut());
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
