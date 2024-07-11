package com.example.pms;

import java.util.Date;

public class Prescriptions {
    private int prescription_id =0;
    private int patient_id;
    private int physician_id=0;
    private int medication_id;
    private String dosage;
    private Date start_date;
    private Date end_date;
    private String instructions;

    // Constructors
    public  Prescriptions( int patient_id, int medication_id, String dosage, Date start_date, Date end_date, String instructions) {

        this.patient_id = patient_id;
        this.medication_id = medication_id;
        this.dosage = dosage;
        this.start_date =  start_date;
        this.end_date =  end_date;
        this.instructions = instructions;
    }

    // Getters and Setters
    public int getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(int prescription_id) {
        this.prescription_id = prescription_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getPhysician_id() {
        return physician_id;
    }

    public void setPhysician_id(int physician_id) {
        this.physician_id = physician_id;
    }

    public int getMedication_id() {
        return medication_id;
    }

    public void setMedication_id(int medication_id) {
        this.medication_id = medication_id;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public java.sql.Date getStart_date() {
        return (java.sql.Date) start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public java.sql.Date getEnd_date() {
        return (java.sql.Date) end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
