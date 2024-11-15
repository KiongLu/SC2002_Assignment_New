package repository;

import entity.Administrator;
import entity.Doctor;
import entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdministratorRepository {
	private static final String FILE_PATH_Administrator = "src/data/Administrator.csv";

    // Create Doctor object from CSV line
    private Administrator createAdministratorFromCSV(String[] parts) {
        // Create a Doctor using the CSV parts in the exact order of columns
        return new Administrator(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
    }

    // Validate doctor credentials
    public User validateAdministratorCredentials(String id, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_Administrator))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id) && parts[3].equals(password)) { // UserID and Password
                    return createAdministratorFromCSV(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
	
}
