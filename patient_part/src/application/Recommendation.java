package application;

public class Recommendation {
    private String disease;
    private String medication;

    public Recommendation(String disease, String medication) {
        this.disease = disease;
        this.medication = medication;
    }

    public String getDisease() {
        return disease;
    }

    public String getMedication() {
        return medication;
    }
}
