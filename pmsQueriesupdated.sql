CREATE DATABASE IF NOT EXISTS Pharms;

USE Pharms;

-- Patients table
CREATE TABLE Patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    date_of_birth DATE,
    gender VARCHAR(10),
    contact_info VARCHAR(100),
    address TEXT,
    emergency_contact VARCHAR(100)
);

-- Physicians table
CREATE TABLE Physicians (
    physician_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    specialization VARCHAR(100),
    contact_info VARCHAR(100),
    email VARCHAR(100)
);

-- Pharmacists table
CREATE TABLE Pharmacists (
    pharmacist_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    contact_info VARCHAR(100),
    email VARCHAR(100)
);

-- Medications table
CREATE TABLE Medications (
    medication_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    dosage VARCHAR(50),
    side_effects TEXT
);

-- Prescriptions table
CREATE TABLE Prescriptions (
    prescription_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    physician_id INT,
    medication_id INT,
    dosage VARCHAR(50),
    start_date DATE,
    end_date DATE,
    instructions TEXT,
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (physician_id) REFERENCES Physicians(physician_id),
    FOREIGN KEY (medication_id) REFERENCES Medications(medication_id)
);

-- MedicationReminders table
CREATE TABLE MedicationReminders (
    reminder_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    medication_id INT,
    reminder_time TIME,
    status VARCHAR(20),
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (medication_id) REFERENCES Medications(medication_id)
);

-- MedicalReports table
CREATE TABLE MedicalReports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    report_date DATE,
    description TEXT,
    file_path VARCHAR(255),
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id)
);

-- MedicationInventory table
CREATE TABLE MedicationInventory (
    inventory_id INT AUTO_INCREMENT PRIMARY KEY,
    medication_id INT,
    quantity INT,
    expiration_date DATE,
    FOREIGN KEY (medication_id) REFERENCES Medications(medication_id)
);

-- Symptoms table
CREATE TABLE Symptoms (
    symptom_id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) UNIQUE
);

-- Recommendations table
CREATE TABLE Recommendations (
    recommendation_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    physician_id INT,
    symptom_id INT,
    medication_id INT,
    recommendation_date DATE,
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (physician_id) REFERENCES Physicians(physician_id),
    FOREIGN KEY (symptom_id) REFERENCES Symptoms(symptom_id),
    FOREIGN KEY (medication_id) REFERENCES Medications(medication_id)
);

-- Payments table
CREATE TABLE Payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    physician_id INT,
    pharmacist_id INT,
    amount DECIMAL(10, 2),
    payment_date DATE,
    payment_status VARCHAR(20),
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (physician_id) REFERENCES Physicians(physician_id),
    FOREIGN KEY (pharmacist_id) REFERENCES Pharmacists(pharmacist_id)
);

-- UserAccounts table
CREATE TABLE UserAccounts (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(20)
);

-- PatientsSymptoms table (junction table for many-to-many relationship)
CREATE TABLE PatientsSymptoms (
    patient_symptom_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    symptom_id INT,
    report_date DATE,
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (symptom_id) REFERENCES Symptoms(symptom_id)
);

-- Diseases table
CREATE TABLE Diseases (
    disease_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    description TEXT
);

-- DiseaseSymptoms table (junction table for many-to-many relationship)
CREATE TABLE DiseaseSymptoms (
    disease_symptom_id INT AUTO_INCREMENT PRIMARY KEY,
    disease_id INT,
    symptom_id INT,
    FOREIGN KEY (disease_id) REFERENCES Diseases(disease_id),
    FOREIGN KEY (symptom_id) REFERENCES Symptoms(symptom_id)
);

-- DiseaseMedications table (junction table for many-to-many relationship)
CREATE TABLE DiseaseMedications (
    disease_medication_id INT AUTO_INCREMENT PRIMARY KEY,
    disease_id INT,
    medication_id INT,
    FOREIGN KEY (disease_id) REFERENCES Diseases(disease_id),
    FOREIGN KEY (medication_id) REFERENCES Medications(medication_id)
);

-- Sample Data Inserts
INSERT INTO Patients (name, date_of_birth, gender, contact_info, address, emergency_contact) VALUES 
('Zarnab Ali', '1985-02-15', 'Male', '03001234567', 'House #12, Street #4, G-10/4, Islamabad', '03211234567'),
('Husnain Khan', '1990-07-25', 'Male', '03019876543', 'House #22, Block B, DHA Phase 5, Lahore', '03329876543'),
('Zaid Butt', '1978-12-05', 'Male', '03123456789', '123 Shadman Colony, Karachi', '03411234567'),
('Tamim Bibi', '1995-11-10', 'Male', '03451237890', '45 Model Town, Faisalabad', '03129876543'),
('Ayesha Ahmed', '1988-03-17', 'Female', '03006789432', 'House #34, Gulberg III, Lahore', '03215678943'),
('Fatima Noor', '1992-08-21', 'Female', '03106789432', '78 Clifton Block 5, Karachi', '03315678943');


INSERT INTO Physicians (name, specialization, contact_info, email) VALUES 
('DrDrake', 'Cardiologist', '03001234568', 'drdrake@hospital.pk'),
('DrImtisal', 'Dermatologist', '03101234569', 'drimtisal@hospital.pk'),
('DrFahar', 'General Physician', '03211234570', 'drfahar@clinic.pk'),
('DrHaider', 'Pediatrician', '03311234571', 'drhaider@hospital.pk');

INSERT INTO Pharmacists (name, contact_info, email) VALUES 
('Waqas Shah', '03001234572', 'waqas.shah@pharmacy.pk'),
('Farah Jameel', '03101234573', 'farah.jameel@pharmacy.pk'),
('Kashif Malik', '03211234574', 'kashif.malik@pharmacy.pk'),
('Sadia Noor', '03311234575', 'sadia.noor@pharmacy.pk');

INSERT INTO Medications (name, description, dosage, side_effects) VALUES 
('Paracetamol', 'Pain reliever and a fever reducer', '500mg', 'Nausea, rash'),
('Amoxicillin', 'Antibiotic for treating bacterial infections', '250mg', 'Diarrhea, allergic reaction'),
('Lisinopril', 'Used to treat high blood pressure', '10mg', 'Cough, dizziness'),
('Metformin', 'Used to treat type 2 diabetes', '500mg', 'Nausea, upset stomach');

INSERT INTO Prescriptions (patient_id, physician_id, medication_id, dosage, start_date, end_date, instructions) VALUES 
(1, 1, 3, '10mg', '2024-01-01', '2024-01-15', 'Take once daily in the morning'),
(2, 2, 1, '500mg', '2024-01-05', '2024-01-10', 'Take every 6 hours as needed for pain'),
(3, 3, 4, '500mg', '2024-01-10', '2024-01-25', 'Take twice daily with meals'),
(4, 4, 2, '250mg', '2024-01-15', '2024-01-20', 'Take every 8 hours for 5 days'),
(5, 1, 1, '500mg', '2024-01-02', '2024-01-07', 'Take every 6 hours as needed for fever'),
(6, 2, 2, '250mg', '2024-01-08', '2024-01-12', 'Take every 8 hours for 5 days');


select * from  Patients
select * from UserAccounts
INSERT INTO MedicationReminders (patient_id, medication_id, reminder_time, status) VALUES 
(1, 3, '08:00:00', 'Active'),
(2, 1, '12:00:00', 'Active'),
(3, 4, '08:00:00', 'Active'),
(4, 2, '09:00:00', 'Active'),
(5, 1, '08:00:00', 'Active'),
(6, 2, '09:00:00', 'Active');

INSERT INTO MedicationReminders (patient_id, medication_id, reminder_time, status) VALUES 
(11, 3, '08:00:00', 'Active'),
(11, 1, '12:00:00', 'Active'),
(11, 4, '08:00:00', 'Active');

INSERT INTO MedicalReports (patient_id, report_date, description, file_path) VALUES 
(1, '2023-12-01', 'Blood Test Report', 'reports/blood_test_report_1.pdf'),
(2, '2023-12-10', 'X-ray Report', 'reports/xray_report_2.pdf'),
(3, '2023-12-15', 'MRI Report', 'reports/mri_report_3.pdf'),
(4, '2023-12-20', 'CT Scan Report', 'reports/ct_scan_report_4.pdf'),
(5, '2023-12-25', 'Ultrasound Report', 'reports/ultrasound_report_5.pdf'),
(6, '2023-12-30', 'Blood Test Report', 'reports/blood_test_report_6.pdf');

INSERT INTO MedicationInventory (medication_id, quantity, expiration_date) VALUES 
(1, 100, '2025-01-01'),
(2, 50, '2025-01-01'),
(3, 200, '2025-01-01'),
(4, 150, '2025-01-01');


INSERT INTO Symptoms (description) VALUES 
('Fever'),
('Cough'),
('Headache'),
('Nausea'),
('Rash'),
('Fatigue'),
('Abdominal Pain');

UPDATE Symptoms 
SET description = 'Nausea' 
WHERE symptom_id = 5;

select * from Symptoms

INSERT INTO Recommendations (patient_id, physician_id, symptom_id, medication_id, recommendation_date) VALUES 
(1, 1, 2, 1, '2024-01-01'),
(2, 2, 1, 2, '2024-01-05'),
(3, 3, 3, 4, '2024-01-10'),
(4, 4, 4, 2, '2024-01-15'),
(5, 1, 5, 1, '2024-01-02'),
(6, 2, 1, 2, '2024-01-08');

INSERT INTO Payments (patient_id, physician_id, pharmacist_id, amount, payment_date, payment_status) VALUES 
(1, 1, 1, 1500.00, '2024-01-01', 'Completed'),
(2, 2, 2, 2000.00, '2024-01-05', 'Pending'),
(3, 3, 3, 2500.00, '2024-01-10', 'Completed'),
(4, 4, 4, 3000.00, '2024-01-15', 'Failed'),
(5, 1, 1, 3500.00, '2024-01-02', 'Completed'),
(6, 2, 2, 4000.00, '2024-01-08', 'Pending');

INSERT INTO UserAccounts (username, password, role) VALUES 
('zarnab_ali', 'password123', 'patient'),
('husnain_khan', 'password456', 'patient'),
('zaid_butt', 'password789', 'patient'),
('tamim_bibi', 'password012', 'patient'),
('ayesha_ahmed', 'password345', 'patient'),
('fatima_noor', 'password678', 'patient'),
('drdrake', 'drakepass', 'physician'),
('drimtisal', 'imtisalpass', 'physician'),
('drfahar', 'faharpass', 'physician'),
('drhaider', 'haiderpass', 'physician'),
('waqas_shah', 'waqaspas', 'pharmacist'),
('farah_jameel', 'farahpass', 'pharmacist'),
('kashif_malik', 'kashifpass', 'pharmacist'),
('sadia_noor', 'sadiapass', 'pharmacist');

INSERT INTO PatientsSymptoms (patient_id, symptom_id, report_date) VALUES 
(1, 1, '2023-12-01'),
(2, 2, '2023-12-05'),
(3, 3, '2023-12-10'),
(4, 4, '2023-12-15'),
(5, 5, '2023-12-20'),
(6, 1, '2023-12-25');

INSERT INTO Diseases (name, description) VALUES 
('Common Cold', 'A viral infection of your nose and throat (upper respiratory tract).'),
('Flu', 'A common viral infection that can be deadly, especially in high-risk groups.'),
('Stomach Ache', 'Pain in the stomach area.'),
('Migraine', 'A headache of varying intensity, often accompanied by nausea and sensitivity to light and sound.'),
('Bronchitis', 'Inflammation of the lining of your bronchial tubes.'),
('Diabetes', 'A group of diseases that result in too much sugar in the blood.'),
('Hypertension', 'A condition in which the force of the blood against the artery walls is too high.'),
('Asthma', 'A condition in which your airways narrow and swell and may produce extra mucus.');

-- Updated DiseaseSymptoms insertions
INSERT INTO DiseaseSymptoms (disease_id, symptom_id) VALUES 
(1, 1), -- Common Cold has Fever
(1, 2), -- Common Cold has Cough
(1, 4), -- Common Cold has Sore Throat
(2, 1), -- Flu has Fever
(2, 2), -- Flu has Cough
(2, 6), -- Flu has Fatigue
(3, 7), -- Stomach Ache has Abdominal Pain
(3, 5), -- Stomach Ache has Nausea=
(4, 3), -- Migraine has Headache
(4, 5), -- Migraine has Nausea
(5, 2), -- Bronchitis has Cough
(5, 4), -- Bronchitis has Sore Throat
(5, 6), -- Bronchitis has Fatigue
(6, 5), -- Diabetes has Nausea
(6, 7), -- Diabetes has Abdominal Pain
(7, 1), -- Hypertension has Fever
(7, 3), -- Hypertension has Headache
(7, 6), -- Hypertension has Fatigue
(8, 2), -- Asthma has Cough
(8, 6); -- Asthma has Fatigue

-- Updated DiseaseMedications insertions
INSERT INTO DiseaseMedications (disease_id, medication_id) VALUES 
(1, 1), -- Common Cold -> Paracetamol
(1, 2), -- Common Cold -> Amoxicillin
(2, 1), -- Flu -> Paracetamol
(2, 4), -- Flu -> Metformin
(3, 3), -- Stomach Ache -> Lisinopril
(3, 4), -- Stomach Ache -> Metformin
(4, 1), -- Migraine -> Paracetamol
(4, 4), -- Migraine -> Metformin
(5, 2), -- Bronchitis -> Amoxicillin
(5, 3), -- Bronchitis -> Lisinopril
(6, 4), -- Diabetes -> Metformin
(7, 3), -- Hypertension -> Lisinopril
(8, 2); -- Asthma -> Amoxicillin

Copy code
ALTER TABLE Patients ADD user_id INT;
ALTER TABLE Patients ADD FOREIGN KEY (user_id) REFERENCES UserAccounts(user_id);
select * from patients
select * from Physicians
select * from Pharmacists
select * from UserAccounts