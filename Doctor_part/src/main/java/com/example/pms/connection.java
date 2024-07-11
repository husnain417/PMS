package com.example.pms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.*;

public class connection {


    Connection conn = null;

    public static Connection ConnectDb() {
        try {
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Pharms", "root", "ZarnabAli12.");
            JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }

    static String url = "jdbc:mysql://127.0.0.1:3306/Pharms";
    static String dbUsername = "root";
    static String dbPassword = "ZarnabAli12.";

    public static ObservableList<Report> getDatausers() {
        ObservableList<Report> list = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT * FROM MedicalReports";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                list.add(new Report(
                        rs.getInt("report_id"),
                        rs.getInt("patient_id"),
                        rs.getDate("report_date"),
                        rs.getString("description"),
                        rs.getString("file_path")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList<patientview> getpatientview() {
        ObservableList<patientview> list = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT patient_id, name FROM Patients";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                list.add(new patientview(
                        rs.getInt("patient_id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ObservableList<medication> getmedication() {
        ObservableList<medication> list = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String query = "SELECT medication_id, name FROM Medications";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                list.add(new medication(
                        rs.getInt("medication_id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

