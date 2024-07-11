package com.example.pms;

public class Doctor {
    private String name;
    private String specialization;
    private String email;
    private String contactInfo;

    public Doctor(String name, String specialization, String email, String contactInfo) {
        this.name = name;
        this.specialization = specialization;
        this.email = email;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getEmail() {
        return email;
    }

    public String getContactInfo() {
        return contactInfo;
    }
}
