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
import javafx.util.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.*;
import javafx.scene.control.TabPane.*;
import oracle.jdbc.pool.OracleDataSource;

public class MainWindow {

    // Data Fields
    BarkApplication signInForm;
    String volunteerID;

    // Storing data in memory
    ArrayList<Event> eventList = new ArrayList<>();
    ArrayList<Drives> drivesList = new ArrayList<>();
    ArrayList<Work> workList = new ArrayList<>();
    ArrayList<Shift> shiftList = new ArrayList<>();

//     JavaFX Controls
    // Labels, TableView, and Button for Jobs Pane
    Label lblJobs = new Label("Jobs for Today");
    Button btnSelectJob = new Button("Select Job");
    ObservableList<Job> jobTableData = FXCollections.observableArrayList();
    TableView<Job> jobTable = new TableView<>(jobTableData);
    VBox jobVBox = new VBox();

    // Labels, TableView, and Button for Events Pane
    Label lblEvents = new Label("Upcoming Events");
    Button btnSelectEvent = new Button("Register for Selected Event");
    ObservableList<Event> eventTableData = FXCollections.observableArrayList();
    TableView<Event> eventTable = new TableView<>(eventTableData);
    VBox eventVBox = new VBox();

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
    ListView<Volunteer> conditionalVolList = new ListView<>(conditionalVolunteers);
    ListView<Volunteer> currentVolList = new ListView<>(currentVolunteers);
    static ObservableList<String> specializations = FXCollections.observableArrayList("Animal Health Care", "Feeding", "Enclosure Care", "Adopter Relations", "Event Volunteer");
    ListView<String> specializationList = new ListView<>(specializations);
    ObservableList<Job> currentJobs = FXCollections.observableArrayList();
    ListView<Job> currentJobsList = new ListView<>(currentJobs);
    ObservableList<Event> currentEvents = FXCollections.observableArrayList();
    ListView<Event> currentEventsList = new ListView<>(currentEvents);
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

    public MainWindow(BarkApplication signInForm, String volunteerID) {
//        this.signInForm = signInForm;

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
        tab9.setContent(adminLocationsPane);
        tab10.setContent(adminAnimalPane);
        tab11.setContent(adminVolunteerPane);
        tab13.setContent(adminSpecializationsPane);
        tbPaneAdmin.getTabs().addAll(tab6, tab7, tab8, tab9, tab10, tab13, tab11);
        readDatabaseData();
        addJobTab();
        addEventsTab();
        addLocationsTab();
        animalsTab();
        specializationsTab();
        manageVolunteersTab();

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
        if (currentUser.status.equalsIgnoreCase("admin")) {
            tbPane.getTabs().addAll(tab1, tab2, tab3, tab4, tab5, tab12);
        } else {
            tbPane.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);
        }

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

        populateEventsTable();
        populateJobsTable();

        // Menu Bar item actions
        miEditAccount.setOnAction(e -> {
            editAccountWindow(currentUser);
        });

    }

    public void populateJobsTable() {
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
        tblcJobLocation.setCellValueFactory(new PropertyValueFactory<Job, String>("locationName"));
        tblcJobNotes.setCellValueFactory(new PropertyValueFactory<Job, String>("jobNotes"));

        tblcJobName.setMinWidth(150);
        tblcJobType.setMinWidth(150);
        tblcJobLocation.setMinWidth(150);
        tblcJobNotes.setMinWidth(200);

        jobTable.getColumns().addAll(tblcJobID, tblcJobName, tblcJobType, tblcJobLocation, tblcJobNotes);
    }

    public void populateEventsTable() {
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
        tblcEventLocation.setCellValueFactory(new PropertyValueFactory<Event, String>("locationName"));
        tblcMaxVolunteers.setCellValueFactory(new PropertyValueFactory<Event, Integer>("maxVolunteers"));
        tblcSpotsLeft.setCellValueFactory(new PropertyValueFactory<Event, Integer>("spotsLeft"));
        tblcEventDescription.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDescription"));

        eventTable.getColumns().addAll(tblcEventID, tblcEventName, tblcEventDate, tblcEventTime, tblcEventLocation, tblcMaxVolunteers, tblcSpotsLeft, tblcEventDescription);

        tblcEventName.setMinWidth(150);
        tblcEventDescription.setMinWidth(200);
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
        TextField txtJobLocation = new TextField();
        TextField txtJobNotes = new TextField();
        Button btnAddNewJob = new Button("Add Job");
        Button btnEditJob = new Button("Edit Selected Job");
        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        HBox idHBox = new HBox(lblJobID, txtJobID);
        HBox nameHBox = new HBox(lblJobName, txtJobName);
        HBox typeHBox = new HBox(lblJobType, txtJobType);
        HBox locationHBox = new HBox(lblJoblocation, txtJobLocation);
        HBox notesHBox = new HBox(lblJobNotes, txtJobNotes);

        adminJobsPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.CENTER);
        rightVBox.setAlignment(Pos.CENTER);

        adminJobsPane.add(leftVBox, 0, 0);
        adminJobsPane.add(rightVBox, 1, 0);

        idHBox.setSpacing(10);
        nameHBox.setSpacing(10);
        typeHBox.setSpacing(10);
        locationHBox.setSpacing(10);
        notesHBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idHBox, nameHBox, typeHBox, locationHBox, notesHBox, btnAddNewJob);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblCurrentJobs, currentJobsList, btnEditJob);

        txtJobID.setText("job" + Job.jobCount);

        // Add Job button action
        btnAddNewJob.setOnAction(e -> {
            Job tempJob = new Job(
                    txtJobID.getText(),
                    txtJobName.getText(),
                    txtJobType.getText(),
                    txtJobLocation.getText(),
                    txtJobNotes.getText()
            );
            Job.jobList.add(tempJob);
            jobTableData.clear();
            for (Job j : Job.jobList) {
                jobTableData.add(j);
                currentJobs.add(j);
            }
            txtJobID.setText("job" + Job.jobCount);
            txtJobName.clear();
            txtJobType.clear();
            txtJobLocation.clear();
            txtJobNotes.clear();
            tempJob.writeJob();
            Alert confirmAddJob = new Alert(Alert.AlertType.CONFIRMATION,
                    "New job has been added to the list.",
                    ButtonType.OK);
            confirmAddJob.show();
            btnAddNewJob.setText("Add Job");
        });

        // Edit Button actions
        btnEditJob.setOnAction(e -> {
            Job selectedJob = currentJobsList.getSelectionModel().getSelectedItem();
            txtJobID.setText(selectedJob.getJobID());
            txtJobName.setText(selectedJob.getJobName());
            txtJobType.setText(selectedJob.getJobType());
            txtJobLocation.setText(selectedJob.getLocationName());
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
        Label lblLocation = new Label("Location:");
        Text txtEventID = new Text();
        TextField txtEventName = new TextField();
        TextField txtEventDate = new TextField();
        TextField txtEventTime = new TextField();
        TextField txtMaxVolunteers = new TextField();
        TextField txtEventDescription = new TextField();
        TextField txtLocation = new TextField();
        Button btnAddNewEvent = new Button("Add Event");
        Button btnEdit = new Button("Edit Selected Event");
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

        idHBox.setSpacing(10);
        nameHBox.setSpacing(10);
        dateHBox.setSpacing(10);
        timeHBox.setSpacing(10);
        maxVolHBox.setSpacing(10);
        descHBox.setSpacing(10);
        locationHBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idHBox, nameHBox, dateHBox, timeHBox, maxVolHBox, descHBox, locationHBox, btnAddNewEvent);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblCurrentEvents, currentEventsList, btnEdit);

        txtEventID.setText("event" + Event.eventCount);

        // Add Event button action
        btnAddNewEvent.setOnAction(e -> {
            Event tempEvent = new Event(
                    txtEventID.getText(),
                    txtEventName.getText(),
                    txtEventDate.getText(),
                    txtEventTime.getText(),
                    Integer.valueOf(txtMaxVolunteers.getText()),
                    0,
                    txtEventDescription.getText(),
                    txtLocation.getText()
            );
            eventList.add(tempEvent);
            eventTableData.clear();
            for (Event ev : eventList) {
                eventTableData.add(ev);
            }
            txtEventID.setText("event" + Event.eventCount);
            txtEventName.clear();
            txtEventDate.clear();
            txtEventTime.clear();
            txtMaxVolunteers.clear();
            txtEventDescription.clear();
            txtLocation.clear();
            tempEvent.writeEvent();
            Alert confirmAddEvent = new Alert(Alert.AlertType.CONFIRMATION,
                    "New event has been added to the list.",
                    ButtonType.OK);
            confirmAddEvent.show();
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
            txtLocation.setText(selectedEvent.getLocationID());
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
        adminLocationsPane.add(btnAddLocation, 0, 4);

        idHBox.setSpacing(10);
        nameHBox.setSpacing(10);
        streetHBox.setSpacing(10);
        cityHBox.setSpacing(10);
        stateHBox.setSpacing(10);
        zipHBox.setSpacing(10);
        typeHBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idHBox, nameHBox, typeHBox, streetHBox, cityHBox, stateHBox, zipHBox, btnEdit);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblCurrentLocations, currentLocationsList, btnEdit);

        txtLocationID.setText("location" + Location.locationCount);

        // Add Location button action
        btnAddLocation.setOnAction(e -> {
            Location tempLocation = new Location(
                    txtLocationID.getText(),
                    txtLocationName.getText(),
                    txtLocationStreet.getText(),
                    txtLocationCity.getText(),
                    txtLocationState.getText(),
                    Integer.valueOf(txtLocationZip.getText()),
                    txtLocationType.getText()
            );
            Location.locationList.add(tempLocation);

            txtLocationID.setText("location" + Location.locationCount);
            txtLocationName.clear();
            txtLocationStreet.clear();
            txtLocationCity.clear();
            txtLocationState.clear();
            txtLocationZip.clear();
            txtLocationType.clear();
            Alert confirmAddLocation = new Alert(Alert.AlertType.CONFIRMATION,
                    "New Location has been added to the list.",
                    ButtonType.OK);
            confirmAddLocation.show();
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

        adminVolunteerPane.add(leftVBox, 0, 0);
        adminVolunteerPane.add(rightVBox, 1, 0);
//        adminVolunteerPane.add(btnReview, 0, 10);
        adminVolunteerPane.add(currentHBox, 1, 10);

        currentHBox.setSpacing(10);

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(lblConditional, conditionalVolList, btnReview);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblCurrent, currentVolList, currentHBox);

        // adding each volunteer to corresponding list
        for (Volunteer v : Volunteer.volunteerArrayList) {
            if (v.status.equals("conditional")) {
                conditionalVolunteers.add(v);
            } else {
                currentVolunteers.add(v);
            }
        }
        conditionalVolList.setItems(conditionalVolunteers);
        currentVolList.setItems(currentVolunteers);

        // Review Button action
        btnReview.setOnAction(e -> {
            Volunteer selectedVolunteer = conditionalVolList.getSelectionModel().getSelectedItem();
            reviewApplication(selectedVolunteer);
        });

        // Delete Button action
        btnDelete.setOnAction(e -> {
            Volunteer selectedVolunteer = currentVolList.getSelectionModel().getSelectedItem();
            currentVolunteers.remove(selectedVolunteer);
            Volunteer.volunteerArrayList.remove(selectedVolunteer);
        });

        // Edit Button actions
        btnEdit.setOnAction(e -> {
            Volunteer selectedVolunteer = currentVolList.getSelectionModel().getSelectedItem();
            editAccountWindow(selectedVolunteer);
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
        TextField txtAnimalName = new TextField("Name:");
        TextField txtAnimalSpecies = new TextField("Species:");
        TextField txtAnimalBreed = new TextField("Breed:");
        TextField txtAnimalAge = new TextField("Age:");
        Button btnAdd = new Button("Add New Animal");
        Button btnEdit = new Button("Edit Selected Animal");
        
        VBox leftVBox = new VBox();
        VBox rightVBox = new VBox();
        HBox idHBox = new HBox(lblAnimalID, txtAnimalID);
        HBox nameHBox = new HBox(lblAnimalName, txtAnimalName);
        HBox speciesHBox = new HBox(lblAnimalSpecies, txtAnimalSpecies);
        HBox breedHBox = new HBox(lblAnimalBreed, txtAnimalBreed);
        HBox ageHBox = new HBox(lblAnimalAge, txtAnimalAge);

        adminEventsPane.setAlignment(Pos.CENTER);
        leftVBox.setAlignment(Pos.TOP_CENTER);
        rightVBox.setAlignment(Pos.TOP_CENTER);

        adminEventsPane.add(leftVBox, 0, 0);
        adminEventsPane.add(rightVBox, 1, 0);
        adminEventsPane.add(btnAdd, 1, 4);

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
        rightVBox.getChildren().addAll(lblCurrentAnimals, currentAnimalsList);

        txtAnimalID.setText("animal" + Animal.animalCount);
        
        // Add Job button action
        btnAdd.setOnAction(e -> {
            Animal tempAnimal = new Animal(
                    txtAnimalID.getText(),
                    txtAnimalName.getText(),
                    txtAnimalSpecies.getText(),
                    txtAnimalBreed.getText(),
                    Integer.valueOf(txtAnimalAge.getText())
            );
            Animal.animalList.add(tempAnimal);
            jobTableData.clear();
            for (Animal a : Animal.animalList) {
                currentAnimals.add(a);
            }
            txtAnimalID.setText("animal" + Animal.animalCount);
            txtAnimalName.clear();
            txtAnimalSpecies.clear();
            txtAnimalBreed.clear();
            txtAnimalAge.clear();
            Alert confirmAddAnimal = new Alert(Alert.AlertType.CONFIRMATION,
                    "New animal has been added to the list.",
                    ButtonType.OK);
            confirmAddAnimal.show();
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
            String newSpecial = txtAdd.getText();
            specializations.addAll(newSpecial);
            specializationList.setItems(specializations);
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

        leftVBox.setSpacing(10);
        leftVBox.setPadding(new Insets(10, 20, 10, 20));
        leftVBox.getChildren().addAll(idBox, firstNameBox, lastNameBox, dateOfBirthBox, emailBox, phoneBox, specialBox, streetBox, cityBox, stateBox, zipBox, infoBox);

        rightVBox.setSpacing(10);
        rightVBox.setPadding(new Insets(10, 20, 10, 20));
        rightVBox.getChildren().addAll(lblExperience, txtExperience);

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
        reviewPane.add(bottomHBox, 1, 20);

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
        primaryStage.setTitle("Review Application");
        primaryStage.show();

        // Approve / Deny button actions
        approve.setOnAction(e -> {
            Alert confirmApprove = new Alert(Alert.AlertType.CONFIRMATION,
                    "Volunteer application has been approved.",
                    ButtonType.OK);
            volunteer.setStatus("Volunteer in Training");
            confirmApprove.show();
            primaryStage.close();
            conditionalVolList.getItems().clear();
            currentVolList.getItems().clear();
            for (Volunteer v : Volunteer.volunteerArrayList) {
                if (v.status.equals("conditional")) {
                    conditionalVolunteers.add(v);
                } else {
                    currentVolunteers.add(v);
                }
            }
            conditionalVolList.setItems(conditionalVolunteers);
            currentVolList.setItems(currentVolunteers);
        });
        deny.setOnAction(e -> {
            Alert confirmDeny = new Alert(Alert.AlertType.CONFIRMATION,
                    "Volunteer application has been denied.",
                    ButtonType.OK);
            Volunteer.volunteerArrayList.remove(volunteer);
            confirmDeny.show();
            primaryStage.close();
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
                jobTableData.clear();
                for (Job j : Job.jobList) {
                    jobTableData.add(j);
                    currentJobs.add(j);
                }
            }
            for (Job j : Job.jobList) {
                System.out.println(j.jobID + " " + j.locationName);
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
                eventList.add(dbEvent);
                eventTableData.clear();
                for (Event e : eventList) {
                    eventTableData.add(e);
                }
            }
            for (Event e : eventList) {
                System.out.println(e.eventID);
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
            for (Animal a : Animal.animalList) {
                System.out.println(a.getAnimalID());
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
            for (Location l : Location.locationList) {
                System.out.println(l.getLocationID());
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
                drivesList.add(dbDrive);
            }
            for (Drives d : drivesList) {
                System.out.println(d.getDriveID());
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
                workList.add(dbWork);
            }
            for (Work w : workList) {
                System.out.println(w.getWorkID());
            }

            // Reading Shift data into Shift objects
            String shiftQuery = "SELECT SHIFTID,CLOCKIN,CLOCKOUT,VOLUNTEERID FROM SHIFT";
            ResultSet dbShifts = commStmt.executeQuery(shiftQuery);
            System.out.println(shiftQuery);
            while (dbShifts.next()) {
                Shift dbShift = new Shift(
                        dbShifts.getNString("SHIFTID"),
                        dbShifts.getInt("CLOCKIN"),
                        dbShifts.getInt("CLOCKOUT"),
                        dbShifts.getNString("VOLUNTEERID")
                );
                shiftList.add(dbShift);
            }
            for (Shift s : shiftList) {
                System.out.println(s.getShiftID());
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
