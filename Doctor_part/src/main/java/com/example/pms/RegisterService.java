package com.example.pms;

import java.sql.*;

public class RegisterService {
    private UserDAO userDAO;

    public RegisterService() {
        this.userDAO = new UserDAO();
    }

    private final String url = "jdbc:mysql://127.0.0.1:3306/Pharms";
    private final String dbUsername = "root";
    private final String dbPassword = "ZarnabAli12.";
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
    public void generateReport(Report report) throws SQLException, ClassNotFoundException {

        userDAO.insertReport(report);

    }

    public void removeReport(int report) throws SQLException, ClassNotFoundException {

        userDAO.deleteReport(report);

    }



    public boolean registerDoctor(Doctor doctor, UserAccount user) throws SQLException {

         String url = "jdbc:mysql://127.0.0.1:3306/Pharms";
         String dbUsername = "root";
         String dbPassword = "ZarnabAli12.";  // Your DB password

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

                        try (PreparedStatement doctorStmt = conn.prepareStatement(
                                "INSERT INTO Physicians (name, specialization, email, contact_info, user_id) VALUES (?, ?, ?, ?, ?)")) {
                            doctorStmt.setString(1, doctor.getName());
                            doctorStmt.setString(2, doctor.getSpecialization());
                            doctorStmt.setString(3, doctor.getEmail());
                            doctorStmt.setString(4, doctor.getContactInfo());
                            doctorStmt.setLong(5, userId);
                            doctorStmt.executeUpdate();
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

    public boolean registerPharmacist(Pharmacist pharmacist, UserAccount user) throws SQLException, ClassNotFoundException {
        if (userDAO.isUsernameTaken(user.getUsername())) {
            return false;
        }
        userDAO.insertPharmacist(pharmacist);
        userDAO.insertUserAccount(user);
        return true;
    }
}
