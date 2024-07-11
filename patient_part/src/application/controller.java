package application;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class controller {

    private LoginService loginService;
    private RecommendationService recommendationService;
    private UserDAO userDAO;

    public controller() {
        this.loginService = new LoginService();
        this.recommendationService = new RecommendationService();
        this.userDAO = new UserDAO();
    }
    // login.fxml
    @FXML
    private TextField Password;

    @FXML
    private TextField Username;

    @FXML
    private Button login_btn;
    
    @FXML
    private ToggleGroup userTypeGroup;
    
    @FXML
    private RadioButton rd_Doctor;

    @FXML
    private RadioButton rd_Pharma;

    @FXML
    private RadioButton rd_patient;

    // Register.fxml
    @FXML
    private Button Register_btn;

    @FXML
    private RadioButton reg_rd_Doctor;

    @FXML
    private RadioButton reg_rd_Pharma;

    @FXML
    private RadioButton reg_rd_patient;

    // Patient_Register.fxml
    @FXML
    private TextField Pat_Address;

    @FXML
    private TextField Pat_Con_No;

    @FXML
    private TextField Pat_Emg_ConNO;

    @FXML
    private TextField Pat_Name;

    @FXML
    private TextField Pat_Password;

    @FXML
    private Button Pat_Registerbtn;

    // Doctor.fxml (registration)
    @FXML
    private TextField Doc_Email;

    @FXML
    private TextField Doc_Name;
    
    @FXML
    private TextField Doc_UserName;

    @FXML
    private TextField Doc_Number;

    @FXML
    private TextField Doc_Password;

    @FXML
    private Button Doc_Registerbtn;

    @FXML
    private TextField Doc_Spec;

    // Pharmacist.fxml (registration)
    @FXML
    private TextField Pharma_ContactInfo;

    @FXML
    private TextField Pharma_Email;
    
    @FXML
    private TextField Pharma_userName;

    @FXML
    private TextField Pharma_Name;

    @FXML
    private TextField Pharma_Password;

    @FXML
    private Button Pharma_Registerbtn;

    // Homepage.fxml
    @FXML
    private Button Appointment_btn;

    @FXML
    private Button Inventory_btn;

    @FXML
    private Button Logout_btn;

    @FXML
    private ImageView ProfileBtn;

    @FXML
    private Button Recomend_btn;

    @FXML
    private Button toLogin_btn;
    
    @FXML
    private Button search_btn;
    
    @FXML
    private Button Reminder_btn;

    @FXML
    private Button Reports_btn;

    @FXML
    private TextField Search;

    @FXML
    private Button Setting_bn;

    @FXML
    private ImageView logo;
    
    @FXML
    private TextField pat_name;

    @FXML
    private TextField pat_username;

    @FXML
    private DatePicker Pat_DOB;

    @FXML
    private SplitMenuButton Pat_Gender;

    @FXML
    private TextField pat_contact;

    @FXML
    private TextField pat_pass;

    @FXML
    void registerpat(ActionEvent event) {
        String name = pat_name.getText();
        String username = pat_username.getText();
        LocalDate dob = Pat_DOB.getValue();
        String gender = Pat_Gender.getText();
        String contact = pat_contact.getText();
        String password = pat_pass.getText();

        if (name.isEmpty() || username.isEmpty() || dob == null || gender.isEmpty() || contact.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill all fields.");
            return;
        }

        Patient newPatient = new Patient(name, dob, gender, contact);
        UserAccount newUser = new UserAccount(username, password, "patient");

        RegisterService registerService = new RegisterService();
        try {
            boolean isRegistered = registerService.registerPatient(newPatient, newUser);
            if (isRegistered) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful!", "Patient registered successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Error!", "Username already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error!", "An error occurred while registering the patient.");
        }
    }



    @FXML
    void toregister(ActionEvent event) 
    {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("register_proj.fxml"));
             Parent root = loader.load();

             Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
             Scene scene = new Scene(root);

             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
             showAlert(AlertType.ERROR, "Navigation Error", "Could not load the home page.");
         }
    }
    

    @FXML
    void switchregisterpage(ActionEvent event) {
        String fxmlFile = null;

        if (reg_rd_patient.isSelected()) {
            fxmlFile = "patreg_proj.fxml";
        } else if (reg_rd_Doctor.isSelected()) {
            fxmlFile = "Docreg_proj.fxml";
        } else if (reg_rd_Pharma.isSelected()) {
            fxmlFile = "pharmreg_proj.fxml";
        }

        if (fxmlFile != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Navigation Error", "Could not load the registration page.");
            }
        } else {
            showAlert(AlertType.ERROR, "Selection Error", "Please select a user type.");
        }
    }
    
    @FXML
    private void handleGenderSelection(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        Pat_Gender.setText(menuItem.getText());
    }
    
    @FXML
    private void medSelection(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        updaterem.setText(menuItem.getText());
    }
    
    @FXML
    private void mSelection(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        addmed.setText(menuItem.getText());
    }

    String usernamepat="";
    private int patientId = 0;
    
    @FXML
    void switchHomePage(ActionEvent event) 
    {
    	
        String username =Username.getText();
        try {
        	GlobalData.patientId = userDAO.getPatientIdByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "An error occurred while retrieving the patient ID.");
        }
        
        System.out.print(GlobalData.patientId);
        
        String password = Password.getText();
        String role = "";

        if (rd_patient.isSelected()) {
            role = "patient";
        } else if (rd_Doctor.isSelected()) {
            role = "physician";
        } else if (rd_Pharma.isSelected()) {
            role = "pharmacist";
        }

        if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Please enter your username, password, and select a role.");
            return;
        }

        if (loginService.login(username, password, role)) {
            try {
                String fxmlFile;
                switch (role) {
                    case "patient":
                        fxmlFile = "patdash_proj.fxml";
                        break;
                    case "physician":
                        fxmlFile = "Docdash_proj.fxml";
                        break;
                    case "pharmacist":
                        fxmlFile = "phardash_proj.fxml";
                        break;
                    default:
                        showAlert(AlertType.ERROR, "Role Error", "Invalid role selected.");
                        return;
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();
                
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Navigation Error", "Could not load the home page.");
            }
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid login credentials or role.");
        }
    }
    
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    void registerdoc(ActionEvent event) {
        String name = Doc_Name.getText();
        String username = Doc_UserName.getText();
        String specialization = Doc_Spec.getText();
        String email = Doc_Email.getText();
        String contactInfo = Doc_Number.getText();
        String password = Doc_Password.getText();

        if (name.isEmpty() || username.isEmpty() || specialization.isEmpty() || email.isEmpty() || contactInfo.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Please fill all fields.");
            return;
        }

        Doctor newDoctor = new Doctor(name, specialization, email, contactInfo);
        UserAccount newUser = new UserAccount(username, password, "physician");

        RegisterService registerService = new RegisterService();
        try {
            boolean isRegistered = registerService.registerDoctor(newDoctor, newUser);
            if (isRegistered) {
                showAlert(AlertType.INFORMATION, "Registration Successful!", "Doctor registered successfully.");
            } else {
                showAlert(AlertType.ERROR, "Registration Error!", "Username already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error!", "An error occurred while registering the doctor.");
        }
    }
    
    @FXML
    void registerPharmacist(ActionEvent event) {
        String name = Pharma_Name.getText();
        String username = Pharma_userName.getText();
        String email = Pharma_Email.getText();
        String contact = Pharma_ContactInfo.getText();
        String password = Pharma_Password.getText();

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || contact.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill all fields.");
            return;
        }

        Pharmacist newPharmacist = new Pharmacist(name, email, contact);
        UserAccount newUser = new UserAccount(username, password, "pharmacist");

        RegisterService registerService = new RegisterService();
        try {
            boolean isRegistered = registerService.registerPharmacist(newPharmacist, newUser);
            if (isRegistered) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful!", "Pharmacist registered successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Error!", "Username already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error!", "An error occurred while registering the pharmacist.");
        }
    }

    
    @FXML
    void toMedRecommendation(ActionEvent event) {
        navigateTo("patmedrecom_proj.fxml", event);
    }
    
//    @FXML
//    void tomedrem(ActionEvent event) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("medrem_proj.fxml"));
//            Parent root = fxmlLoader.load();
//
//            controller controller = fxmlLoader.getController();
//            controller.initialize();
//
//            Stage stage = new Stage();
//            stage.setTitle("Medication Reminders");
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void navigateTo(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
          
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Navigation Error", "Could not load the page.");
        }
    }

    @FXML
    void tologin(ActionEvent event) {
        navigateTo("log_proj.fxml", event);
    }
    
    @FXML
    void tohome(ActionEvent event) {
        navigateTo("patdash_proj.fxml", event);
    }

    
    @FXML
    private RadioButton fever_rd;
    
    @FXML
    private RadioButton cough_rd;
    
    @FXML
    private RadioButton headache_rd;
    
    @FXML
    private RadioButton sore_rd;
    
    @FXML
    private RadioButton mild_rd;
    
    @FXML
    private RadioButton mod_rd;
    
    @FXML
    private RadioButton severe_rd;
    
    @FXML
    private RadioButton fatigue_rd;
    
    @FXML
    private RadioButton day_rd;
    
    @FXML
    private RadioButton week_rd;
    
    @FXML
    private RadioButton week2_rd;
    
    @FXML
    private RadioButton nausea_rd;
    
    @FXML
    private RadioButton pain_rd;
    
    @FXML
    private TextField additional_info;

    @FXML
    private Label disease;
    
    @FXML
    private Label medicine;
    

    
    
    @FXML
    void handleSearchClick(ActionEvent event) {
        List<String> selectedSymptoms = new ArrayList<>();

        if (fever_rd.isSelected()) selectedSymptoms.add("Fever");
        if (cough_rd.isSelected()) selectedSymptoms.add("Cough");
        if (headache_rd.isSelected()) selectedSymptoms.add("Headache");
        if (sore_rd.isSelected()) selectedSymptoms.add("Sore Throat");
        if (fatigue_rd.isSelected()) selectedSymptoms.add("Fatigue");
        if (nausea_rd.isSelected()) selectedSymptoms.add("Nausea");
        if (pain_rd.isSelected()) selectedSymptoms.add("Abdominal Pain");

        try {
            Recommendation recommendation = recommendationService.getRecommendation(selectedSymptoms);
            if (recommendation != null) {
                disease.setText(recommendation.getDisease());
                medicine.setText(recommendation.getMedication());
            } else {
                showAlert(AlertType.INFORMATION, "No Match", "No matching disease and medication found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "An error occurred while fetching the recommendations.");
        }
    }

    @FXML
    private Button addbtn;

    @FXML
    private SplitMenuButton addmed;

    @FXML
    private TextField addtime;

    @FXML
    private Button deletebtn;

    @FXML
    private TextField deleteremid;

    @FXML
    private Button tohome_btn;

    @FXML
    private Button updatebtn;

    @FXML
    private SplitMenuButton updaterem;

    @FXML
    private TextField updateremid;

    @FXML
    private TextField updatetime;
    
    @FXML
    private TextField updatestatus;
    
    
    @FXML
    private TableView<MedicationReminder> tableView;

    @FXML
    private TableColumn<MedicationReminder, String> remidcol;

    @FXML
    private TableColumn<MedicationReminder, String> medidcol;

    @FXML
    private TableColumn<MedicationReminder, String> timeicol;

    @FXML
    private TableColumn<MedicationReminder, String> status;
    
    private static boolean isMedRemOpen = false;


    @FXML
    void tomedrem(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("patmedrem_proj.fxml"));
            Parent root = fxmlLoader.load();

            isMedRemOpen = true;
            
            controller controller = fxmlLoader.getController();
            controller.initialize();
            
            
            
            Stage stage = new Stage();
            stage.setTitle("Medication Reminders");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void viewrem(ActionEvent event) {

    }
    
    
    private UserDAO dbConnection = new UserDAO();
    ObservableList<MedicationReminder> list = FXCollections.observableArrayList();
    
//  public void initialize() 
//  {
//	  if(isMedRemOpen)
//	  {
//	  MedicationReminder b1 = new MedicationReminder("3","3","08:00:00","Active");
//	  MedicationReminder b2 = new MedicationReminder("1","2","12:00:00","Active");
//	  MedicationReminder b3 = new MedicationReminder("4","1","08:00:00","Active");
//	  
//      list.addAll(b1,b2,b3);
//      tableView.setItems(list);
//      remidcol.setCellValueFactory(cellData -> cellData.getValue().reminderId);
//      medidcol.setCellValueFactory(cellData -> cellData.getValue().medicationId);
//      timeicol.setCellValueFactory(cellData -> cellData.getValue().reminderTime);
//      status.setCellValueFactory(cellData -> cellData.getValue().status);
//	  }
//	  isMedRemOpen=false;
//  }

  
    private void initialize() {
        if (isMedRemOpen) {
            remidcol.setCellValueFactory(cellData -> cellData.getValue().reminderId);
            medidcol.setCellValueFactory(cellData -> cellData.getValue().medicationId);
            timeicol.setCellValueFactory(cellData -> cellData.getValue().reminderTime);
            status.setCellValueFactory(cellData -> cellData.getValue().status);
           
            System.out.print(GlobalData.patientId);

            
            tableView.setItems(reminderList);
            loadcurrreminders();
        }
        isMedRemOpen=false;
    }

    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    ObservableList<MedicationReminder> reminderList = FXCollections.observableArrayList();


    private void loadcurrreminders() {
        //reminderList.clear();
        String query = "SELECT * FROM MedicationReminders WHERE patient_id = ?";
        try (Connection connection = dbConnection.getConnection()) {
            System.out.println("Connection to the database successful.");

            // Prepare and execute the SQL query
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, GlobalData.patientId);
                // Execute the query and print message
                if (statement.execute()) {
                    System.out.println("Query executed successfully.");
                } else {
                    System.out.println("Query did not execute successfully.");
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int reminderId = resultSet.getInt("reminder_id");
                        int medicationId = resultSet.getInt("medication_id");
                        Timestamp timestamp = resultSet.getTimestamp("reminder_time");
                        LocalTime reminderTime = timestamp.toLocalDateTime().toLocalTime();
                        String status = resultSet.getString("status");

                        // Print data from the current row
                        System.out.println("reminder_id: " + reminderId + ", MedicationID: " + medicationId + ", ReminderTime: " + reminderTime + ", Status: " + status);

                        // Create MedicationReminder object and add to list
                        MedicationReminder reminder = new MedicationReminder(
                                String.valueOf(reminderId),
                                String.valueOf(medicationId),
                                reminderTime.toString(), // Convert LocalTime to String
                                status
                        );
                        reminderList.add(reminder);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database Error", "Error executing SQL query: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Connection Error", "Error connecting to the database: " + e.getMessage());
        }
    }

    public String getMedicationIdByName(String medicationName) throws SQLException {
        String query = "SELECT medication_id FROM Medications WHERE name = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medicationName);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getString("medication_id");
            } else {
                throw new SQLException("Medication not found for name: " + medicationName);
            }
        }
    }


    @FXML
    void addrem(ActionEvent event) {
        String medicationName = addmed.getText();
        String reminderTime = addtime.getText();

        if (medicationName.isEmpty() || reminderTime.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Please select a medication and enter a time.");
            return;
        }

        try {
            // Get the medication ID from the medication name
            String medicationId = getMedicationIdByName(medicationName);

            // Create a MedicationReminder object with the retrieved medication ID
            MedicationReminder newReminder = new MedicationReminder(toString(GlobalData.patientId), medicationId, reminderTime, "Active");

            // Attempt to add the medication reminder to the database
            boolean isAdded = addMedicationReminder(newReminder, patientId);
            if (isAdded) {
                showAlert(Alert.AlertType.INFORMATION, "Reminder Added", "Medication reminder added successfully.");
                reminderList.add(newReminder); // Add to local list
                tableView.refresh(); // Refresh TableView to show new data
            } else {
                showAlert(Alert.AlertType.ERROR, "Add Reminder Failed", "Failed to add the medication reminder.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while adding the medication reminder.");
        }
    }


    private String toString(int patientId2) {
		// TODO Auto-generated method stub
		return null;
	}



	public boolean addMedicationReminder(MedicationReminder reminder, int patientId) throws SQLException {
        String query = "INSERT INTO MedicationReminders (patient_id, medication_id, reminder_time, status) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, GlobalData.patientId);
            statement.setString(2, reminder.getMedicationId());

            Time sqlTime = Time.valueOf(reminder.getReminderTime() + ":00");
            statement.setTime(3, sqlTime);
            statement.setString(4, reminder.getStatus());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    @FXML
    void deleterem(ActionEvent event) {
        String reminderId = deleteremid.getText();

        if (reminderId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Please enter a reminder ID to delete.");
            return;
        }

        try {
            boolean isDeleted = deleteMedicationReminder(Integer.parseInt(reminderId));
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Reminder Deleted", "Medication reminder deleted successfully.");
                // Update the TableView after deletion
                //loadcurrreminders();
                tableView.refresh();
            } else {
                showAlert(Alert.AlertType.ERROR, "Delete Reminder Failed", "Failed to delete the medication reminder.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while deleting the medication reminder.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Reminder ID", "Please enter a valid reminder ID.");
        }
    }
    
    public boolean deleteMedicationReminder(int reminderId) throws SQLException {
        String query = "DELETE FROM MedicationReminders WHERE reminder_id = ? AND patient_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reminderId);
            statement.setInt(2, GlobalData.patientId); // Assuming patientId is accessible in this method

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    @FXML
    void updaterem(ActionEvent event) {
        String reminderId = updateremid.getText();
        String medicationName = updaterem.getText();
        String reminderTime = updatetime.getText();
        String status = updatestatus.getText();

        if (reminderId.isEmpty() || medicationName.isEmpty() || reminderTime.isEmpty() || status.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Please fill all fields.");
            return;
        }

        try {
            // Get the medication ID from the medication name
            String medicationId = getMedicationIdByName(medicationName);

            // Create a MedicationReminder object with the retrieved medication ID
            MedicationReminder updatedReminder = new MedicationReminder(reminderId, medicationId, reminderTime, status);

            // Attempt to update the medication reminder in the database
            boolean isUpdated = updateMedicationReminder(updatedReminder);
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Reminder Updated", "Medication reminder updated successfully.");
                // Update the TableView after updating
                //loadcurrreminders();
                tableView.refresh();
            } else {
                showAlert(Alert.AlertType.ERROR, "Update Reminder Failed", "Failed to update the medication reminder.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the medication reminder.");
        }
    }

    public boolean updateMedicationReminder(MedicationReminder reminder) throws SQLException {
        String query = "UPDATE MedicationReminders SET medication_id = ?, reminder_time = ?, status = ? WHERE reminder_id = ? AND patient_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, reminder.getMedicationId());

            Time sqlTime = Time.valueOf(reminder.getReminderTime() + ":00");
            statement.setTime(2, sqlTime);
            statement.setString(3, reminder.getStatus());
            statement.setInt(4, Integer.parseInt(reminder.getReminderId()));
            statement.setInt(5, GlobalData.patientId); // Assuming patientId is accessible in this method

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }


    @FXML
    void toprofset(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("patprof_proj.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller instance from the FXML loader
            controller controller = fxmlLoader.getController();
            controller.initializeprof();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Profile Settings");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the profile settings page.");
        }
    }

    
    @FXML
    private TextField name;

    @FXML
    private TextField dob;

    @FXML
    private TextField gender;

    @FXML
    private TextField contact;

    @FXML
    private TextField address;

    @FXML
    private TextField contactemer;
    
    
    public void initializeprof() {
        try (Connection connection = getConnection()) {
            String query = "SELECT name, date_of_birth, gender, contact_info, address, emergency_contact FROM Patients WHERE patient_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, GlobalData.patientId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        name.setText(resultSet.getString("name"));
                        dob.setText(resultSet.getString("date_of_birth"));
                        gender.setText(resultSet.getString("gender"));
                        contact.setText(resultSet.getString("contact_info"));
                        address.setText(resultSet.getString("address"));
                        contactemer.setText(resultSet.getString("emergency_contact"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while fetching user data.");
        }
        loadUserData();
    }
    
    @FXML
    void updatename(ActionEvent event) {
        updateField("name", name.getText());
    }

    @FXML
    void updatedob(ActionEvent event) {
        updateField("date_of_birth", dob.getText());
    }

    @FXML
    void updategender(ActionEvent event) {
        updateField("gender", gender.getText());
    }

    @FXML
    void updatecon(ActionEvent event) {
        updateField("contact_info", contact.getText());
    }

    @FXML
    void updateaddress(ActionEvent event) {
        updateField("address", address.getText());
    }

    @FXML
    void updateemer(ActionEvent event) {
        updateField("emergency_contact", contactemer.getText());
    }

    private void updateField(String fieldName, String newValue) {
        try (Connection connection = getConnection()) {
            String currentValue = getCurrentFieldValue(connection, fieldName);
            if (!newValue.equals(currentValue)) {
                boolean confirmed = showConfirmationAlert(fieldName, currentValue, newValue);
                if (confirmed) {
                    updateFieldInDatabase(connection, fieldName, newValue);
                    loadUserData(); // Reload the user data to reflect the changes
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the user data.");
        }
    }

    private String getCurrentFieldValue(Connection connection, String fieldName) throws SQLException {
        String query = "SELECT " + fieldName + " FROM Patients WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, GlobalData.patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(fieldName);
                }
            }
        }
        return null;
    }

    private void updateFieldInDatabase(Connection connection, String fieldName, String newValue) throws SQLException {
        String query = "UPDATE Patients SET " + fieldName + " = ? WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newValue);
            statement.setInt(2, GlobalData.patientId);
            statement.executeUpdate();
        }
    }

    private boolean showConfirmationAlert(String fieldName, String currentValue, String newValue) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update " + fieldName);
        alert.setHeaderText(null);
        alert.setContentText("Do you want to update " + fieldName + " from '" + currentValue + "' to '" + newValue + "'?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }


    private void loadUserData() {
        try (Connection connection = getConnection()) {
            String query = "SELECT name, date_of_birth, gender, contact_info, address, emergency_contact FROM Patients WHERE patient_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, GlobalData.patientId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        name.setText(resultSet.getString("name"));
                        dob.setText(resultSet.getString("date_of_birth"));
                        gender.setText(resultSet.getString("gender"));
                        contact.setText(resultSet.getString("contact_info"));
                        address.setText(resultSet.getString("address"));
                        contactemer.setText(resultSet.getString("emergency_contact"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while fetching user data.");
        }
    }


    // Example getConnection() method (this should be implemented to return a valid connection)
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Pharms";
        String dbUsername = "root";
        String dbPassword = "";
        return DriverManager.getConnection(url, dbUsername, dbPassword);
    }
    




    
    public class MedicationReminder {
        private final StringProperty reminderId;
        private final StringProperty medicationId;
        private final StringProperty reminderTime;
        private final StringProperty status;

        public MedicationReminder(String reminderId, String medicationId, String reminderTime, String status) {
            this.reminderId = new SimpleStringProperty(reminderId);
            this.medicationId = new SimpleStringProperty(medicationId);
            this.reminderTime = new SimpleStringProperty(reminderTime);
            this.status = new SimpleStringProperty(status);
        }

        public String getReminderId() {
            return reminderId.get();
        }

        public void setReminderId(String reminderId) {
            this.reminderId.set(reminderId);
        }

        public StringProperty reminderIdProperty() {
            return reminderId;
        }

        public String getMedicationId() {
            return medicationId.get();
        }

        public void setMedicationId(String medicationId) {
            this.medicationId.set(medicationId);
        }

        public StringProperty medicationIdProperty() {
            return medicationId;
        }

        public String getReminderTime() {
            return reminderTime.get();
        }

        public void setReminderTime(String reminderTime) {
            this.reminderTime.set(reminderTime);
        }

        public StringProperty reminderTimeProperty() {
            return reminderTime;
        }

        public String getStatus() {
            return status.get();
        }

        public void setStatus(String status) {
            this.status.set(status);
        }

        public StringProperty statusProperty() {
            return status;
        }
    }

}

    