package repository;

import entity.Doctor;
import entity.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import controller.ValidationInterface;
import controller.checkHaveQuestionsInterface;

public class DoctorRepository implements ValidationInterface, checkHaveQuestionsInterface{

    private static final String FILE_PATH_DOCTORS = "sc2002.scmb.grp1.hms//resource//Doctor.csv";

    // Create Doctor object from CSV line
    private Doctor createDoctorFromCSV(String[] parts) {
        // Create a Doctor using the CSV parts in the exact order of columns
        return new Doctor(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
    }

    // Validate doctor credentials
    public User validateCredentials(String id, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_DOCTORS))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id) && parts[3].equals(password)) { // UserID and Password
                    return createDoctorFromCSV(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Doctor> loadDoctors() throws IOException {
        List<Doctor> doctors = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH_DOCTORS));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            
            doctors.add(createDoctorFromCSV(data));
        }
        br.close();
        return doctors;
    }

    // Find a doctor by their DoctorID
    public Doctor findDoctorById(String doctorId) throws IOException {
        List<Doctor> doctors = loadDoctors();
        // Search for the doctor with the given ID
        return doctors.stream()
                .filter(doctor -> doctor.getUserId().equals(doctorId))
                .findFirst()
                .orElse(null);  // Return null if no doctor is found
    }
    public boolean checkHaveQuestions(String hospitalID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_DOCTORS))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 9 && parts[0].equals(hospitalID) && !parts[9].isEmpty()) { // UserID and Password
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String returnQuestion(String hospitalID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_DOCTORS))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(hospitalID) && !parts[9].isEmpty()) { // UserID and Password
                    return parts[9];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public boolean questionVerification(String hospitalID, String answer) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_DOCTORS))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(hospitalID) && parts[10].equals(answer.toLowerCase())) { // Match ID and Answer
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
}


