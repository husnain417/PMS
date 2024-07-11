package com.example.pms;

import java.sql.*;

public class UserDAO {

    private final String url = "jdbc:mysql://127.0.0.1:3306/Pharms";
    private final String dbUsername = "root";
    private final String dbPassword = "ZarnabAli12.";  // Your DB password

    static {
        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    public boolean validateLogin(String username, String password, String role) {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT role FROM UserAccounts WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbRole = rs.getString("role");
                return dbRole.equals(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUsernameTaken(String username) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT username FROM UserAccounts WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void insertPatient(Patient patient) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "INSERT INTO Patients (name, date_of_birth, gender, contact_info) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, patient.getName());
            stmt.setDate(2, java.sql.Date.valueOf(patient.getDateOfBirth()));
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getContactInfo());
            stmt.executeUpdate();
        }
    }

    public void insertDoctor(Doctor doctor) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "INSERT INTO Physicians (name, specialization, email, contact_info) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getSpecialization());
            stmt.setString(3, doctor.getEmail());
            stmt.setString(4, doctor.getContactInfo());
            stmt.executeUpdate();
        }
    }

    public void insertUserAccount(UserAccount user) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "INSERT INTO UserAccounts (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();
        }
    }

    public void insertPharmacist(Pharmacist pharmacist) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "INSERT INTO Pharmacists (name, email, contact_info) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, pharmacist.getName());
            stmt.setString(2, pharmacist.getEmail());
            stmt.setString(3, pharmacist.getContactInfo());
            stmt.executeUpdate();
        }
    }

    public void insertReport(Report report) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String insertReportSQL = "INSERT INTO MedicalReports (patient_id, report_date, description, file_path) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertReportSQL);
            preparedStatement.setInt(1, report.getpatID());
            preparedStatement.setDate(2, report.getReport_date());
            preparedStatement.setString(3, report.getDescription());
            preparedStatement.setString(4, report.getReport_path());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new medical report was inserted successfully!");
            }
        }
    }


        public Boolean deleteReport(int reportId) throws SQLException {
            try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                String deleteReportSQL = "DELETE FROM MedicalReports WHERE report_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(deleteReportSQL);
                preparedStatement.setInt(1, reportId);
                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("The medical report with ID " + reportId + " was deleted successfully!");
                    return true;
                } else {
                    System.out.println("No medical report found with ID " + reportId);
                    return false;
                }
            }
        }



    public Boolean updateReportDescription(int reportId, String newDescription) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String updateReportSQL = "UPDATE MedicalReports SET description = ? WHERE report_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(updateReportSQL);
            preparedStatement.setString(1, newDescription);
            preparedStatement.setInt(2, reportId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("The medical report with ID " + reportId + " was updated successfully!");
                return true;
            } else {
                System.out.println("No medical report found with ID " + reportId);
                return false;
            }
        }
    }




    public int getdoctoridbyusername(String username) throws SQLException {
        int patientId = -1;
        String query = "SELECT p.physician_id FROM Physicians p JOIN UserAccounts u ON p.user_id = u.user_id WHERE u.username = ?";
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                patientId = rs.getInt("physician_id");
            }
        }
        return patientId;
    }



public Boolean insertPrescription(Prescriptions prescrip) throws SQLException {
try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
    String insertPrescriptionSQL = "INSERT INTO Prescriptions (patient_id, physician_id, medication_id, dosage, start_date, end_date, instructions) VALUES (?,2, ?, ?, ?, ?, ?)";
    PreparedStatement preparedStatement = conn.prepareStatement(insertPrescriptionSQL);
    preparedStatement.setInt(1, prescrip.getPatient_id());
    preparedStatement.setInt(2, prescrip.getMedication_id());
    preparedStatement.setString(3, prescrip.getDosage());
    preparedStatement.setDate(4, prescrip.getStart_date());
    preparedStatement.setDate(5, prescrip.getEnd_date());
    preparedStatement.setString(6, prescrip.getInstructions());
    int rowsInserted = preparedStatement.executeUpdate();
    if (rowsInserted > 0) {
        System.out.println("A new prescription was inserted successfully!");
        return true;
    }
    else
    {
        return false;
    }
}
}

}
