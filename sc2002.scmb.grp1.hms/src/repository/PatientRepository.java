package repository;

import entity.Doctor;
import entity.Patient;
import entity.Pharmacist;
import entity.User;
import util.PasswordUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.ChangeSecurityQuestionInterface;
import controller.PasswordChangerInterface;
import controller.PasswordController;
import controller.ValidationInterface;
import controller.checkHaveQuestionsInterface;

public class PatientRepository implements ValidationInterface, checkHaveQuestionsInterface, PasswordChangerInterface, ChangeSecurityQuestionInterface{

	private static final String FILE_PATH_PATIENT = "sc2002.scmb.grp1.hms//resource//Patient.csv";

 // create Patient object using csv
    private Patient createPatientFromCSV(String[] parts) {
        // Create a Patient using the CSV parts in the exact order of columns
        return new Patient(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]);
    }

    //Validate Password
    public User validateCredentials(String id, String password) {
        PasswordController pc = new PasswordController();
        String df = "Password";
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PATIENT))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts[0].equals(id) && parts[3].equals(df) && parts[3].equals(password)){
                    return createPatientFromCSV(parts);
                }
                else if (parts[0].equals(id) && parts[3].equals(pc.hashPassword(password))) { // UserID and Password
                    return createPatientFromCSV(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
    public boolean checkHaveQuestions(String hospitalID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PATIENT))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 10 && parts[0].equals(hospitalID) && !parts[10].isEmpty()) { // UserID and Password
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String returnQuestion(String hospitalID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PATIENT))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(hospitalID) && !parts[10].isEmpty()) { // UserID and Password
                    return parts[10];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public boolean questionVerification(String hospitalID, String answer) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PATIENT))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(hospitalID) && parts[11].equals(answer.toLowerCase())) { // Match ID and Answer
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean changePassword(String hospitalID, String newHashedPassword) {
        List<String[]> allRecords = new ArrayList<>();
        boolean passwordUpdated = false;
    
        // Load all records from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PATIENT))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(hospitalID)) {
                    parts[3] = newHashedPassword; // Update password
                    passwordUpdated = true;
                }
                allRecords.add(parts);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return false; // Indicate failure
        }
    
        // Rewrite the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_PATIENT))) {
            for (String[] record : allRecords) {
                writer.write(String.join(",", record));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            return false; // Indicate failure
        }
    
        return passwordUpdated;
    }
	
	public List<Patient> loadPatients() throws IOException {
        List<Patient> patients = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH_PATIENT));
        String line;
        boolean isFirstLine = true;
        while ((line = br.readLine()) != null) {
            if (isFirstLine){
                isFirstLine = false; //skips the first line
                continue;
            }
            String[] data = line.split(",");
            
            patients.add(createPatientFromCSV(data));
        }
        br.close();
        return patients;
    }

    // Find a patient by their PatientID
    public Patient findPatientById(String patientId) throws IOException {
        List<Patient> patients = loadPatients();
        return patients.stream()
                .filter(patient -> patient.getUserId().equals(patientId))
                .findFirst()
                .orElse(null);
    }

    public boolean updatePatient(Patient updatedPatient) throws IOException{
        List<Patient> patients = loadPatients();
        boolean isUpdated = false; 

        for (Patient patient : patients){
            if (patient.getUserId().equals(updatedPatient.getUserId())){
                patient.setEmail(updatedPatient.getEmail());
                patient.setPhoneNumber(updatedPatient.getPhoneNumber());
                isUpdated = true;
                break;
            }
        }

        //update CSV with updated data
        if (isUpdated){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_PATIENT, false))) {
                writer.write("UserID,Name,Role,Password,Gender,Age,PhoneNumber,Email,DOB,BloodType");
                writer.newLine();
                for (Patient patient : patients){
                    writer.write(String.join(",", 
                        patient.getUserId(),
                        patient.getName(),
                        patient.getRole(),
                        patient.getPassword(),
                        patient.getGender(),
                        patient.getAge(),
                        patient.getPhoneNumber(),
                        patient.getEmail(),
                        patient.getDob(),
                        patient.getBloodtype()));
                    writer.newLine();
                }
            }
        }
        return isUpdated;
    }
    public boolean changeSecurityQuestion(String hospitalID, String question, String answer) {
    List<String[]> allRecords = new ArrayList<>();
    boolean questionUpdated = false;

    // Load all records from the file
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PATIENT))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");

            // Check if the record matches the hospitalID
            if (parts[0].equals(hospitalID)) {
                // Ensure the CSV has enough columns for Question and Answer
                if (parts.length <= 10) {
                    // Add blank placeholders if Question and Answer columns are missing
                    parts = Arrays.copyOf(parts, 12);
                    parts[10] = ""; // Question placeholder
                    parts[11] = ""; // Answer placeholder
                }
                // Update Question and Answer
                parts[10] = question;
                parts[11] = answer;
                questionUpdated = true;
            }
            allRecords.add(parts); // Add the record to the list
        }
    } catch (IOException e) {
        System.err.println("Error reading the file: " + e.getMessage());
        return false; // Indicate failure
    }

    // Rewrite the file with updated records
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_PATIENT))) {
        for (String[] record : allRecords) {
            writer.write(String.join(",", record));
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Error writing to the file: " + e.getMessage());
        return false; // Indicate failure
    }

    return questionUpdated; // Return true if the question was updated
}
}
