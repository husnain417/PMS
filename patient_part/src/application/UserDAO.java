package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final String url = "jdbc:mysql://localhost:3306/Pharms";
    private final String dbUsername = "root";
    private final String dbPassword = "";  // Your DB password

    
    private Connection databaseLink;

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, dbUsername, dbPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Failed to load database driver: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to establish database connection: " + e.getMessage());
        }
        return databaseLink;
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
    
    public Recommendation getRecommendation(List<String> symptoms) throws SQLException {
        if (symptoms.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder(
            "SELECT d.name AS disease, m.name AS medication " +
            "FROM Diseases d " +
            "JOIN DiseaseSymptoms ds ON d.disease_id = ds.disease_id " +
            "JOIN Symptoms s ON ds.symptom_id = s.symptom_id " +
            "JOIN DiseaseMedications dm ON d.disease_id = dm.disease_id " +
            "JOIN Medications m ON dm.medication_id = m.medication_id " +
            "WHERE s.description IN (");

        for (int i = 0; i < symptoms.size(); i++) {
            query.append("?");
            if (i < symptoms.size() - 1) {
                query.append(", ");
            }
        }
        query.append(") GROUP BY d.name, m.name " +
                     "HAVING COUNT(DISTINCT s.symptom_id) = ?");

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < symptoms.size(); i++) {
                stmt.setString(i + 1, symptoms.get(i));
            }
            stmt.setInt(symptoms.size() + 1, symptoms.size());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String disease = rs.getString("disease");
                String medication = rs.getString("medication");
                return new Recommendation(disease, medication);
            } else {
                return null;
            }
        }
    }
    
    public int getPatientIdByUsername(String username) throws SQLException {
        int patientId = -1;
        String query = "SELECT p.patient_id FROM Patients p JOIN UserAccounts u ON p.user_id = u.user_id WHERE u.username = ?";
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
        		  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                patientId = rs.getInt("patient_id");
            }
        }
        return patientId;
    }
    
}