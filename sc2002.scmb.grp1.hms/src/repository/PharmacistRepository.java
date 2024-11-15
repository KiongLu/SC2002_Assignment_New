package repository;

import controller.ValidationInterface;
import entity.Doctor;
import entity.Pharmacist;
import entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PharmacistRepository implements ValidationInterface {

    private static final String FILE_PATH_PHARMACISTS = "/data/Pharmacist.csv";

    
    // create pharmacist object using csv
    private Pharmacist createPharmacistFromCSV(String[] parts) {
        // Create a Pharmacist using the CSV parts in the exact order of columns
        return new Pharmacist(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
    }

    //Validate Password
	public User validateCredentials(String id, String password) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream(FILE_PATH_PHARMACISTS))))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id) && parts[3].equals(password)) { // UserID and Password
                    return createPharmacistFromCSV(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
}
