package application;

import java.time.LocalDate;

public class Patient {
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String contactInfo;

    public Patient(String name, LocalDate dateOfBirth, String gender, String contactInfo) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getContactInfo() {
        return contactInfo;
    }
}
