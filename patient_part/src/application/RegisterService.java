package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterService {
    private UserDAO userDAO;

    public RegisterService() {
        this.userDAO = new UserDAO();
    }
    private final String url = "jdbc:mysql://localhost:3306/Pharms";
    private final String dbUsername = "root";
    private final String dbPassword = "";  // Your DB password
    public boolean registerPatient(Patient patient, UserAccount user) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            conn.setAutoCommit(false);

            try (PreparedStatement userStmt = conn.prepareStatement(
                    "INSERT INTO UserAccounts (username, password, role) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, user.getUsername());
                userStmt.setString(2, user.getPassword());
                userStmt.setString(3, user.getRole());
                int affectedRows = userStmt.executeUpdate();

                if (affectedRows == 0) {
                    conn.rollback();
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long userId = generatedKeys.getLong(1);

                        try (PreparedStatement patientStmt = conn.prepareStatement(
                                "INSERT INTO Patients (name, date_of_birth, gender, contact_info, user_id) VALUES (?, ?, ?, ?, ?)")) {
                            patientStmt.setString(1, patient.getName());
                            patientStmt.setDate(2, Date.valueOf(patient.getDateOfBirth()));
                            patientStmt.setString(3, patient.getGender());
                            patientStmt.setString(4, patient.getContactInfo());
                            patientStmt.setLong(5, userId);
                            patientStmt.executeUpdate();
                        }
                    } else {
                        conn.rollback();
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public boolean registerDoctor(Doctor doctor, UserAccount user) throws SQLException {
        if (userDAO.isUsernameTaken(user.getUsername())) {
            return false;
        }
        userDAO.insertDoctor(doctor);
        userDAO.insertUserAccount(user);
        return true;
   }
    
    public boolean registerPharmacist(Pharmacist pharmacist, UserAccount user) throws SQLException {
        if (userDAO.isUsernameTaken(user.getUsername())) {
            return false;
        }
        userDAO.insertPharmacist(pharmacist);
        userDAO.insertUserAccount(user);
        return true;
    }
}
