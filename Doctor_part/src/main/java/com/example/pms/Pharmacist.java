package com.example.pms;

public class Pharmacist {
    private String name;
    private String email;
    private String contactInfo;

    public Pharmacist(String name, String email, String contactInfo) {
        this.name = name;
        this.email = email;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactInfo() {
        return contactInfo;
    }
}
