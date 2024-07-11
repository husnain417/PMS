package application;
import java.time.LocalTime;

public class MedicationReminder {
    private int reminderId;
    private int medicationId;
    private LocalTime reminderTime;
    private String status;

    // Constructor, getters, and setters
    public MedicationReminder(int reminderId, int medicationId, LocalTime reminderTime, String status) {
        this.reminderId = reminderId;
        this.medicationId = medicationId;
        this.reminderTime = reminderTime;
        this.status = status;
    }
    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public int getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }

    public LocalTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}