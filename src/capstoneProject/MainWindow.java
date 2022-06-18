package capstoneProject;

import java.sql.*;
import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.*;
import java.util.*;
import javafx.scene.control.cell.*;
import javafx.scene.control.TabPane.*;
import oracle.jdbc.pool.OracleDataSource;

public class MainWindow {

    // Data Fields
    BarkApplication signInForm;
    String volunteerID;

    
    // Storing data in memory
    ArrayList<Volunteer> volunteerList = new ArrayList<>();
    ArrayList<Job> jobList = new ArrayList<>();
    ArrayList<Event> eventList = new ArrayList<>();
    ArrayList<Animal> animalList = new ArrayList<>();
    ArrayList<Location> locationList = new ArrayList<>();
    ArrayList<Drives> drivesList = new ArrayList<>();
    ArrayList<Work> workList = new ArrayList<>();
    ArrayList<Shift> shiftList = new ArrayList<>();

//     JavaFX Controls
    // Labels, TableView, and Button for Jobs Pane
    Label lblJobs = new Label("Jobs for Today");
    Button btnSelectJob = new Button("Select Job");
    TableView<Job> jobTable = new TableView<>();
    ObservableList<Job> jobTableData = FXCollections.observableArrayList();
    VBox jobVBox = new VBox();

    // Labels, TableView, and Button for Events Pane
    Label lblEvents = new Label("Upcoming Events");
    Button btnSelectEvent = new Button("Register for Selected Event");
    TableView<Event> eventTable = new TableView<>();
    ObservableList<Event> eventTableData = FXCollections.observableArrayList();
    VBox eventVBox = new VBox();

    // Tabs and controls for admin pane
    TabPane tbPaneAdmin = new TabPane();
    Tab tab6 = new Tab("Reports");
    Tab tab7 = new Tab("Add/Edit Jobs");
    Tab tab8 = new Tab("Add/Edit Events");
    Tab tab9 = new Tab("Manage Volunteers");
    Tab tab10 = new Tab("Animals");
    ComboBox selectEditJob = new ComboBox<>();
    VBox adminVBox = new VBox();
    ObservableList<Volunteer> conditionalVolunteers = FXCollections.observableArrayList();
    ObservableList<Volunteer> currentVolunteers = FXCollections.observableArrayList();
    ListView<Volunteer> conditionalVolList = new ListView<>(conditionalVolunteers);
    ListView<Volunteer> currentVolList = new ListView<>(currentVolunteers);

    // Create GridPanes for all tabs
    GridPane overallPane = new GridPane(); // holds menuBar and tab pane
    GridPane homePane = new GridPane();
    GridPane jobPane = new GridPane();
    GridPane eventPane = new GridPane();
    GridPane volunteerPane = new GridPane();
    GridPane adminPane = new GridPane();
    GridPane adminReportsPane = new GridPane();
    GridPane adminJobsPane = new GridPane();
    GridPane adminEventsPane = new GridPane();
    GridPane adminVolunteerPane = new GridPane();
    GridPane adminAnimalPane = new GridPane();

    // Create Menu Bar
    MenuBar menuBar = new MenuBar();
    Menu menuMyAccount = new Menu("My Account");
    MenuItem miEditAccount = new MenuItem("Edit Account");
    MenuItem miCheckOut = new MenuItem("Check Out");

    // Create tab pane object and create 5 tabs
    TabPane tbPane = new TabPane();
    Tab tab1 = new Tab("Home");
    Tab tab2 = new Tab("Jobs");
    Tab tab3 = new Tab("BARK Events");
    Tab tab4 = new Tab("Volunteer Summary");
    Tab tab5 = new Tab("Admin");

    // Class wide variable that can be used to display content related to the logged in user
    String currentLoggedInUser;
    
    public MainWindow(BarkApplication signInForm, String volunteerID) {
//        this.signInForm = signInForm;

        // Class wide variable that can be used to display content related to the logged in user
        currentLoggedInUser = volunteerID;
        
        overallPane.setAlignment(Pos.CENTER);
        homePane.setAlignment(Pos.CENTER);
        jobPane.setAlignment(Pos.CENTER);
        eventPane.setAlignment(Pos.CENTER);
        volunteerPane.setAlignment(Pos.CENTER);
        adminPane.setAlignment(Pos.CENTER);
        jobVBox.setAlignment(Pos.CENTER);
        eventVBox.setAlignment(Pos.CENTER);
        adminVBox.setAlignment(Pos.CENTER);

        // Set up MenuBar and add to overallPane
        menuMyAccount.getItems().addAll(miEditAccount, miCheckOut);
        menuBar.getMenus().add(menuMyAccount);
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
        // Admin Pane
        //So tabs cannot close
        tbPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tbPaneAdmin.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        adminPane.add(tbPaneAdmin, 0, 0);
        // tabs on admin pane
        tab6.setContent(adminReportsPane);
        tab7.setContent(adminJobsPane);
        tab8.setContent(adminEventsPane);
        tab9.setContent(adminVolunteerPane);
        tab10.setContent(adminAnimalPane);
        tbPaneAdmin.getTabs().addAll(tab6, tab7, tab8, tab9, tab10);
        addJobTab();
        addEventsTab();
        manageVolunteersTab();
        animalsTab();

        // Placing tabs in overallPane and setting content of tabs to correspoding panes
        overallPane.add(tbPane, 0, 1);
        tab1.setContent(homePane);
        tab2.setContent(jobPane);
        tab3.setContent(eventPane);
        tab4.setContent(volunteerPane);
        tab5.setContent(adminPane);
        tbPane.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);

        Stage primaryStage = new Stage();
        Scene primaryScene = new Scene(overallPane, 800, 650);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("BARK Volunteer Information System");
        primaryStage.show();

        // Set height and width of FX controls
        tbPane.setMinHeight(primaryScene.getHeight());
        tbPane.setMinWidth(primaryScene.getWidth());
        tbPaneAdmin.setMinHeight(primaryScene.getHeight());
        tbPaneAdmin.setMinWidth(primaryScene.getWidth());
        jobTable.setMinWidth(primaryScene.getWidth());
        eventTable.setMinWidth(primaryScene.getWidth());

        readDatabaseData();
        // Populate Event table data
        eventTable.setItems(eventTableData);
        TableColumn tblcEventID = new TableColumn("ID");
        TableColumn tblcEventName = new TableColumn("Name");
        TableColumn tblcEventDate = new TableColumn("Date");
        TableColumn tblcEventTime = new TableColumn("Time");
        TableColumn tblcEventLocation = new TableColumn("Location");
        TableColumn tblcMaxVolunteers = new TableColumn("Max Volunteers");
        TableColumn tblcSpotsLeft = new TableColumn("Spots Left");
        TableColumn tblcEventDescription = new TableColumn("Description");

        tblcEventID.setCellValueFactory(new PropertyValueFactory<Event, String>("eventID"));
        tblcEventName.setCellValueFactory(new PropertyValueFactory<Event, String>("eventName"));
        tblcEventDate.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDate"));
        tblcEventTime.setCellValueFactory(new PropertyValueFactory<Event, String>("eventTime"));
        tblcEventLocation.setCellValueFactory(new PropertyValueFactory<Event, String>("locationID"));
        tblcMaxVolunteers.setCellValueFactory(new PropertyValueFactory<Event, Integer>("maxVolunteers"));
        tblcSpotsLeft.setCellValueFactory(new PropertyValueFactory<Event, Integer>("spotsLeft"));
        tblcEventDescription.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDescription"));

        eventTable.getColumns().addAll(tblcEventID, tblcEventName, tblcEventDate, tblcEventTime, tblcMaxVolunteers, tblcSpotsLeft, tblcEventDescription);

        tblcEventName.setMinWidth(150);
        tblcEventDescription.setMinWidth(200);

        // Populate job table data
        jobTable.setItems(jobTableData);
        TableColumn tblcJobID = new TableColumn("ID");
        TableColumn tblcJobName = new TableColumn("Name");
        TableColumn tblcJobType = new TableColumn("Type");
        TableColumn tblcJobLocation = new TableColumn("Location");
        TableColumn tblcJobNotes = new TableColumn("Notes");

        tblcJobID.setCellValueFactory(new PropertyValueFactory<Job, String>("jobID"));
        tblcJobName.setCellValueFactory(new PropertyValueFactory<Job, String>("jobName"));
        tblcJobType.setCellValueFactory(new PropertyValueFactory<Job, Double>("jobType"));
        tblcJobLocation.setCellValueFactory(new PropertyValueFactory<Job, String>("jobLocation"));
        tblcJobNotes.setCellValueFactory(new PropertyValueFactory<Job, String>("jobNotes"));

        tblcJobName.setMinWidth(150);
        tblcJobType.setMinWidth(150);
        tblcJobLocation.setMinWidth(150);
        tblcJobNotes.setMinWidth(200);

        jobTable.getColumns().addAll(tblcJobID, tblcJobName, tblcJobType, tblcJobLocation, tblcJobNotes);

        tblcMaxVolunteers.setMinWidth(120);

        // Menu Bar item actions
        miEditAccount.setOnAction(e -> {
//            editAccountWindow();
        });

    }

    public void addJobTab() {
        Label lblJobID = new Label("Job ID:");
        Label lblJobName = new Label("Name:");
        Label lblJobType = new Label("Type:");
        Label lblJoblocation = new Label("Location:");
        Label lblJobNotes = new Label("Notes:");
        Text txtJobID = new Text();
        TextField txtJobName = new TextField();
        TextField txtJobType = new TextField();
        TextField txtJoblocation = new TextField();
        TextField txtJobNotes = new TextField();
        Button btnAddNewJob = new Button("Add Job");
        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        HBox idHBox = new HBox(lblJobID, txtJobID);
        HBox nameHBox = new HBox(lblJobName, txtJobName);
        HBox typeHBox = new HBox(lblJobType, txtJobType);
        HBox locationHBox = new HBox(lblJoblocation, txtJoblocation);
        HBox notesHBox = new HBox(lblJobNotes, txtJobNotes);

        adminJobsPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        adminJobsPane.add(leftVBox, 0, 0);
        adminJobsPane.add(rightVBox, 1, 0);
        adminJobsPane.add(btnAddNewJob, 1, 4);

        idHBox.setSpacing(10);
        nameHBox.setSpacing(10);
        typeHBox.setSpacing(10);
        locationHBox.setSpacing(10);
        notesHBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idHBox, nameHBox, typeHBox);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(locationHBox, notesHBox);

        // Add Job button action
        btnAddNewJob.setOnAction(e -> {
            Job tempJob = new Job();
            jobList.add(tempJob);
            jobTableData.clear();
            for (Job j : jobList) {
                jobTableData.add(j);
            }
            Alert confirmAddJob = new Alert(Alert.AlertType.CONFIRMATION,
                    "New job has been added to the list.",
                    ButtonType.OK);
            confirmAddJob.show();
        });

    }

    public void addEventsTab() {
        Label lblEventID = new Label("Event ID:");
        Label lblEventName = new Label("Name:");
        Label lblEventDate = new Label("Date:");
        Label lblEventTime = new Label("Time:");
        Label lblMaxVolunteers = new Label("Max Volunteers:");
        Label lblEventDescription = new Label("Description:");
        Label lblLocation = new Label("Location:");
        Text txtEventID = new Text();
        TextField txtEventName = new TextField();
        TextField txtEventDate = new TextField();
        TextField txtEventTime = new TextField();
        TextField txtMaxVolunteers = new TextField();
        TextField txtEventDescription = new TextField();
        TextField txtLocation = new TextField();
        Button btnAddNewEvent = new Button("Add Event");
        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        HBox idHBox = new HBox(lblEventID, txtEventID);
        HBox nameHBox = new HBox(lblEventName, txtEventName);
        HBox dateHBox = new HBox(lblEventDate, txtEventDate);
        HBox timeHBox = new HBox(lblEventTime, txtEventTime);
        HBox maxVolHBox = new HBox(lblMaxVolunteers, txtMaxVolunteers);
        HBox descHBox = new HBox(lblEventDescription, txtEventDescription);
        HBox locationHBox = new HBox(lblLocation, txtLocation);

        adminEventsPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        adminEventsPane.add(leftVBox, 0, 0);
        adminEventsPane.add(rightVBox, 1, 0);
        adminEventsPane.add(btnAddNewEvent, 1, 4);

        idHBox.setSpacing(10);
        nameHBox.setSpacing(10);
        dateHBox.setSpacing(10);
        timeHBox.setSpacing(10);
        maxVolHBox.setSpacing(10);
        descHBox.setSpacing(10);
        locationHBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idHBox, nameHBox, dateHBox, timeHBox);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(maxVolHBox, descHBox, locationHBox);

        // Add Job button action
        btnAddNewEvent.setOnAction(e -> {
            Event tempEvent = new Event("event0", "ButterBeer Festival", "6/15/2022",
                    1200, 4, "We will have a booth at the festival.", "location0"); // THIS IS A PLACEHOLDER EVENT
            eventList.add(tempEvent);
            eventTableData.clear();
            for (Event ev : eventList) {
                eventTableData.add(ev);
            }
            Alert confirmAddEvent = new Alert(Alert.AlertType.CONFIRMATION,
                    "New event has been added to the list.",
                    ButtonType.OK);
            confirmAddEvent.show();
        });
    }

    public void reportsWindow() {

        // FX Controls
        Button btnViewHours = new Button("View Volunteer Hours");
        Button btnViewRegistered = new Button("View Volunteers Registered for Events");
        VBox reportButtonVBox = new VBox();

        GridPane reportsPane = new GridPane();
        reportsPane.setAlignment(Pos.CENTER);
        reportButtonVBox.setAlignment(Pos.CENTER);

        reportsPane.add(reportButtonVBox, 0, 0);
        reportButtonVBox.setSpacing(10);
        reportButtonVBox.setPadding(new Insets(10, 20, 10, 20));
        reportButtonVBox.getChildren().addAll(btnViewHours, btnViewRegistered);

        Stage primaryStage = new Stage();
        Scene primaryScene = new Scene(reportsPane, 400, 200);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("View Reports");
        primaryStage.show();

        btnViewHours.setOnAction(e -> {
            // displays report of volunteer hours
        });

        btnViewRegistered.setOnAction(e -> {
            // displays report of volunteers registered for events
        });
    }

    public void manageVolunteersTab() {

        // FX Controls
        Label lblConditional = new Label("Volunteers Awaiting Approval:");
        Button btnReview = new Button("Review Selected Applicant");
        Label lblCurrent = new Label("Current Volunteers:");
        Button btnEdit = new Button("Edit Selected Volunteer");
        Button btnDelete = new Button("Remove Selected Volunteer");

        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        HBox currentHBox = new HBox(btnEdit, btnDelete);

        adminVolunteerPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        adminVolunteerPane.add(conditionalVolList, 0, 0);
        adminVolunteerPane.add(leftVBox, 0, 0);
        adminVolunteerPane.add(rightVBox, 1, 0);
        adminVolunteerPane.add(btnReview, 0, 10);
        adminVolunteerPane.add(currentHBox, 1, 10);

        currentHBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(lblConditional, conditionalVolList, btnReview);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblCurrent, currentVolList, currentHBox);

        // Review Button action
        btnReview.setOnAction(e -> {

        });

        // Delete Button action
        btnDelete.setOnAction(e -> {
            Volunteer selectedVolunteer = currentVolList.getSelectionModel().getSelectedItem();
            currentVolunteers.remove(selectedVolunteer);
            volunteerList.remove(selectedVolunteer);
        });

        // Edit Button actions
        btnEdit.setOnAction(e -> {
            Volunteer selectedVolunteer = currentVolList.getSelectionModel().getSelectedItem();
            editAccountWindow(selectedVolunteer);
        });
    }

    public void animalsTab() {

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
        TextField txtInfo = new TextField();
        
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

        ComboBox<String> comboSpecialization = new ComboBox<>();

        TextArea txtExperience = new TextArea();

        Button submit = new Button("Save Changes");

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
        HBox experienceBox = new HBox();

        editPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.TOP_LEFT);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        editPane.add(leftVBox, 0, 0);
        editPane.add(rightVBox, 1, 0);
//        overallPane.add(experienceBox, 0, 7);
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
        experienceBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idBox, firstNameBox, lastNameBox, dateOfBirthBox, emailBox, phoneBox, specialBox, streetBox, cityBox, stateBox, zipBox, infoBox);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblExperience, txtExperience);

//        txtvolunteerID.setText(volunteer.getVolunteerID());
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
        });
    }

    // Method to read volunteer data from database
    public void readDatabaseData() {
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

                volunteerList.add(dbVolunteer);
                if (dbVolunteer.status.equals("conditional")) {
                    conditionalVolunteers.add(dbVolunteer);
                }
                else {
                    currentVolunteers.add(dbVolunteer);
                }
            }
            conditionalVolList.setItems(conditionalVolunteers);
            currentVolList.setItems(currentVolunteers);

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
                jobList.add(dbJob);
                jobTableData.clear();
                for (Job j : jobList) {
                    jobTableData.add(j);
                    System.out.println(j.jobID);
                }
            }

            // Reading Event data into Event objects
            String eventQuery = "SELECT EVENTID,EVENTNAME,EVENTDATE,TIME,MAXVOLUNTEERS,EVENTDESCRIPTION,LOCATIONID FROM EVENT";
            ResultSet dbEvents = commStmt.executeQuery(eventQuery);
            System.out.println(eventQuery);
            while (dbEvents.next()) {
                Event dbEvent = new Event(
                        dbEvents.getNString("EVENTID"),
                        dbEvents.getNString("EVENTNAME"),
                        dbEvents.getNString("EVENTDATE"),
                        dbEvents.getInt("TIME"),
                        dbEvents.getInt("MAXVOLUNTEERS"),
                        dbEvents.getNString("EVENTDESCRIPTION"),
                        dbEvents.getNString("LOCATIONID")
                );
                eventList.add(dbEvent);
                eventTableData.clear();
                for (Event e : eventList) {
                    eventTableData.add(e);
                    System.out.println(e.eventID);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

}
