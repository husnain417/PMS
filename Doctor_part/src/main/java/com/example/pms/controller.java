package com.example.pms;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;



public class controller implements Initializable {

    private LoginService loginService;


    public controller() {
        this.loginService = new LoginService();
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
    private Button Prescription_btn;
    @FXML
    private ImageView ProfileBtn;

    @FXML
    private Button Recomend_btn;

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





    //report.fxml (report page)
    @FXML
    private Button Report_Appointment_btn;

    @FXML
    private Button Report_Logout_btn;

    @FXML
    private Button Report_Recomend_btn;

    @FXML
    private Button Report_Reminder_btn;

    @FXML
    private Button Report_Reports_btn;

    @FXML
    private Button Report_Setting_bn;

    @FXML
    private ImageView Report_logo;

    @FXML
    private Button add_report_btn;

    @FXML
    private Button remove_report_btn;

    @FXML
    private Button update_report_btn;

    @FXML
    private Button view_report_btn;




    //ReportsAdd.fxml
    @FXML
    private TextField ReportAdd_descr;

    @FXML
    private TextField ReportAdd_path;

    @FXML
    private TextField ReportAdd_patid;

    @FXML
    private DatePicker ReportAdd_rep_date;

    @FXML
    private Button ReportsAdd_create;



    //ReportRemove.fxml
    @FXML
    private TableColumn<Report,String> ReportsRemove_repID_col;

    @FXML
    private TableColumn<Report,String> ReportRemove_patID_col;

    @FXML
    private TableColumn<Report, Date> ReportsRemove_date_col;


    @FXML
    private TableColumn<Report,String> ReportsRemove_desc_col;

    @FXML
    private TableColumn<Report, String> ReportsRemove_path_col;

    @FXML
    private Button ReportsRemove_delete;


    @FXML
    private TextField ReportsRemove_reportID;

    @FXML
    private TableView<Report> ReportsRemove_table;


    //ReportUpdate.fxml
    @FXML
    private TextField ReportsUpdate_disc;

    @FXML
    private TextField ReportsUpdate_repid;

    @FXML
    private Button ReportUpdate_updatebtn;



    //prescriptionAdd..fxml
    @FXML
    private TableColumn<medication,Integer > MedID_col;

    @FXML
    private TableColumn<medication, String> MedName_col;

    @FXML
    private TableView<medication> Medication_Table;

    @FXML
    private TableColumn<patientview, String> PatientName_col;

    @FXML
    private TableColumn<patientview, Integer> Patient_ID_col;

    @FXML
    private TableView<patientview> Patient_Table;

    @FXML
    private TextField PrecriptionAdd_Instruc;

    @FXML
    private TextField PrescriptionAdd_Dosage;

    @FXML
    private DatePicker PrescriptionAdd_EndDate;

    @FXML
    private TextField PrescriptionAdd_MedID;

    @FXML
    private DatePicker PrescriptionAdd_StDate;

    @FXML
    private Button PrescriptionAdd_btn;

    @FXML
    private TextField PrescriptionAdd_patID;




    //this is for to view reports in the tabble list
    ObservableList<Report> listR;
   // ObservableList<patientview> listP;
    //ObservableList<medication> listM;
    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;



    @Override
    public void initialize(URL url, ResourceBundle rb) {


        //to view reports table

            try {
                ReportsRemove_repID_col.setCellValueFactory(new PropertyValueFactory<>("report_id"));
                ReportRemove_patID_col.setCellValueFactory(new PropertyValueFactory<>("pat_id"));
                ReportsRemove_date_col.setCellValueFactory(new PropertyValueFactory<>("report_date"));
                ReportsRemove_desc_col.setCellValueFactory(new PropertyValueFactory<>("description"));
                ReportsRemove_path_col.setCellValueFactory(new PropertyValueFactory<>("report_path"));


//                Patient_ID_col.setCellValueFactory(new PropertyValueFactory<>("pat_id"));
//                PatientName_col.setCellValueFactory(new PropertyValueFactory<>("pat_name"));
//                MedID_col.setCellValueFactory(new PropertyValueFactory<>("med_id"));
//                MedName_col.setCellValueFactory(new PropertyValueFactory<>("med_name"));
//
//                Patient_Table.getItems().clear();
//                listP = connection.getpatientview();
//                listM = connection.getmedication() ;
//                Patient_Table.setItems(listP);
//                Medication_Table.setItems((listM));


                ReportsRemove_table.getItems().clear();
                listR = connection.getDatausers();
                ReportsRemove_table.setItems(listR);
            } catch (Exception e) {
                e.printStackTrace();
//              showAlert(AlertType.ERROR, "Data Loading Error", "Failed to load medical reports data.");
            }

      }






    @FXML
    void registerpat(ActionEvent event) {
        String name = pat_name.getText();
        String username = pat_username.getText();
        LocalDate dob = Pat_DOB.getValue();
        String gender = Pat_Gender.getText();
        String contact = pat_contact.getText();
        String password = pat_pass.getText();

        if (name.isEmpty() || username.isEmpty() || dob == null || gender.isEmpty() || contact.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Please fill all fields.");
            return;
        }

        Patient newPatient = new Patient(name, dob, gender, contact);
        UserAccount newUser = new UserAccount(username, password, "patient");

        RegisterService registerService = new RegisterService();
        try {
            boolean isRegistered = registerService.registerPatient(newPatient, newUser);
            if (isRegistered) {
                showAlert(AlertType.INFORMATION, "Registration Successful!", "Patient registered successfully.");
            } else {
                showAlert(AlertType.ERROR, "Registration Error!", "Username already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error!", "An error occurred while registering the patient.");
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

    UserDAO userDAO = new UserDAO();

    @FXML
    void switchHomePage(ActionEvent event) {
        String username = Username.getText();
        String password = Password.getText();
        try {
            glovalvar.doctorId=userDAO.getdoctoridbyusername(username);
            System.out.println(glovalvar.doctorId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                        fxmlFile = "home_proj.fxml";
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
            showAlert(AlertType.ERROR, "Form Error!", "Please fill all fields.");
            return;
        }

        Pharmacist newPharmacist = new Pharmacist(name, email, contact);
        UserAccount newUser = new UserAccount(username, password, "pharmacist");

        RegisterService registerService = new RegisterService();
        try {
            boolean isRegistered = registerService.registerPharmacist(newPharmacist, newUser);
            if (isRegistered) {
                showAlert(AlertType.INFORMATION, "Registration Successful!", "Pharmacist registered successfully.");
            } else {
                showAlert(AlertType.ERROR, "Registration Error!", "Username already exists.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error!", "An error occurred while registering the pharmacist.");
        }
    }



    //switch to Reports Page
    @FXML
    private void switchToReportsPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }


    //Switch to ReportsAdd Page
    @FXML
    private void switchToReportsAdd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportsAdd.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void GenerateReport(ActionEvent event) throws SQLException, ClassNotFoundException {

try {
        int pat_id = Integer.parseInt(ReportAdd_patid.getText());
        String description = ReportAdd_descr.getText();
        Date date = Date.valueOf(ReportAdd_rep_date.getValue());
        String path = ReportAdd_path.getText();

    if (pat_id == 0 || description.isEmpty() || path.isEmpty()) {
        showAlert(AlertType.ERROR, "Form Error!", "Please fill all fields.");
        return;
    }


    Report newReport = new Report(pat_id, date, description, path);

    RegisterService registerService = new RegisterService();

    registerService.generateReport(newReport);
    showAlert(AlertType.INFORMATION, "Success", "Report generated successfully.");

    } catch (NumberFormatException e) {
        showAlert(AlertType.ERROR, "Form Error!", "Patient ID must be a number.");
    } catch (NullPointerException e) {
        showAlert(AlertType.ERROR, "Form Error!", "Please select a date.");
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        showAlert(AlertType.ERROR, "Database Error!", "An error occurred while generating the report.");
    }



    }

    @FXML
    public void  deleteReport(ActionEvent event) throws SQLException {

        if (ReportsRemove_reportID.getText() == "") {
            showAlert(AlertType.ERROR, "Form Error!", "Please fill all fields.");
            return;
        }
        UserDAO dao = new UserDAO();
        Boolean test = dao.deleteReport(Integer.parseInt(ReportsRemove_reportID.getText()));
        if (test == true)
        {
            showAlert(AlertType.ERROR, "Form Error!", "The medical report with ID " + ReportsRemove_reportID.getText() + " was deleted successfully!");

            listR.removeIf(report -> report.report_id== Integer.parseInt(ReportsRemove_reportID.getText()));
            ReportsRemove_table.refresh();
        }
        else {
            showAlert(AlertType.ERROR, "Succesfully Deleted!", "No medical report found with ID " +ReportsRemove_reportID.getText());
        }
    }

    @FXML
    public void updateReport(ActionEvent event) throws SQLException {
        String reportIdText = ReportsUpdate_repid.getText();
        String newDescription = ReportsUpdate_disc.getText();

        if (reportIdText.isEmpty() || newDescription.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill all fields.");
            return;
        }

        int reportId;
        try {
            reportId = Integer.parseInt(reportIdText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Report ID must be a valid integer.");
            return;
        }

        UserDAO dao = new UserDAO();
        Boolean updated = dao.updateReportDescription(reportId, newDescription);

        if (updated) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "The medical report with ID " + reportId + " was updated successfully!");
            // Refresh the table view
            listR = connection.getDatausers();
            ReportsRemove_table.setItems(listR);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No medical report found with ID " + reportId);
        }
    }



    //Switch to ReportsRemove Page
    @FXML
    private void switchToReportsRemove(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportsRemove.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    //Switch To ReportsUpdate Page
    @FXML
    private void switchToReportsUpdate(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportsUpdate.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }


    //switch to Prescription
    @FXML
    private void switchToPrescription(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Prescriptions.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void switchToPrescriptionAdd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PrescriptionAdd.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void switchToPrescriptionRemove(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PrescriptionRemove.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }





    @FXML
    public void insertPrescription(ActionEvent event) throws SQLException {



        String dosage = PrescriptionAdd_Dosage.getText();
        Date StartDate = Date.valueOf(PrescriptionAdd_StDate.getValue());
        Date EndDate = Date.valueOf(PrescriptionAdd_EndDate.getValue());
        String Instruc = PrecriptionAdd_Instruc.getText();


        if (PrescriptionAdd_MedID.getText() == "" || PrescriptionAdd_patID.getText() ==""){
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill all fields.");
            return;
        }

        int pat_id;
        int med_id;
        try {
            pat_id = Integer.parseInt(PrescriptionAdd_patID.getText());
           med_id = Integer.parseInt(PrescriptionAdd_MedID.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Medicine ID and Patient ID must be a valid integer.");
            return;
        }

        UserDAO dao = new UserDAO();
        Prescriptions pres = new Prescriptions(pat_id,med_id,dosage,StartDate,EndDate,Instruc);
        Boolean stat = dao.insertPrescription(pres);

        if (stat) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "The medical prescription with med id " + med_id + " was inserted successfully!");

        }
    }

}



