package repository;

import entity.Doctor;
import entity.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import controller.ValidationInterface;

public class DoctorRepository implements ValidationInterface{

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
    
    
}


