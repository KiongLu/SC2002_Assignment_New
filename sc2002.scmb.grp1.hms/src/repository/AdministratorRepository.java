package repository;

import controller.ValidationInterface;
import entity.Administrator;
import entity.User;

import java.io.*;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

public class AdministratorRepository implements ValidationInterface {
	private static final String FILE_PATH_ADMINISTRATOR = "sc2002.scmb.grp1.hms\\resource\\Administrator.csv";

    // Create Doctor object from CSV line
    private Administrator createAdministratorFromCSV(String[] parts) {
        // Create a Doctor using the CSV parts in the exact order of columns
        return new Administrator(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
    }

    // Validate doctor credentials
    public User validateCredentials(String id, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_ADMINISTRATOR))) {
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
