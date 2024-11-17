package repository;

import controller.ChangeSecurityQuestionInterface;
import controller.PasswordChangerInterface;
import controller.PasswordController;
import controller.ValidationInterface;
import controller.checkHaveQuestionsInterface;
import entity.Doctor;
import entity.Pharmacist;
import entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PharmacistRepository implements ValidationInterface, checkHaveQuestionsInterface, PasswordChangerInterface, ChangeSecurityQuestionInterface {

    private static final String FILE_PATH_PHARMACISTS = "sc2002.scmb.grp1.hms//resource//Pharmacist.csv";

    
    // create pharmacist object using csv
    private Pharmacist createPharmacistFromCSV(String[] parts) {
        // Create a Pharmacist using the CSV parts in the exact order of columns
        return new Pharmacist(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
    }

    //Validate Password
	public User validateCredentials(String id, String password) {
        PasswordController pc = new PasswordController();
        String df = "Password";
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PHARMACISTS))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts[0].equals(id) && parts[3].equals(df) && parts[3].equals(password)){
                    return createPharmacistFromCSV(parts);
                }
                else if (parts[0].equals(id) && parts[3].equals(pc.hashPassword(password))) { // UserID and Password
                    return createPharmacistFromCSV(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
    public boolean checkHaveQuestions(String hospitalID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PHARMACISTS))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 8 && parts[0].equals(hospitalID) && !parts[8].isEmpty()) { // UserID and Password
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String returnQuestion(String hospitalID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PHARMACISTS))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(hospitalID) && !parts[8].isEmpty()) { // UserID and Password
                    return parts[8];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public boolean questionVerification(String hospitalID, String answer) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PHARMACISTS))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(hospitalID) && parts[9].equals(answer.toLowerCase())) { // Match ID and Answer
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
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PHARMACISTS))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_PHARMACISTS))) {
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
    public boolean changeSecurityQuestion(String hospitalID, String question, String answer) {
    List<String[]> allRecords = new ArrayList<>();
    boolean questionUpdated = false;

    // Load all records from the file
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PHARMACISTS))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");

            // Check if the record matches the hospitalID
            if (parts[0].equals(hospitalID)) {
                // Ensure the CSV has enough columns for Question and Answer
                if (parts.length <= 8) {
                    // Add blank placeholders if Question and Answer columns are missing
                    parts = Arrays.copyOf(parts, 10);
                    parts[8] = ""; // Question placeholder
                    parts[9] = ""; // Answer placeholder
                }
                // Update Question and Answer
                parts[8] = question;
                parts[9] = answer;
                questionUpdated = true;
            }
            allRecords.add(parts); // Add the record to the list
        }
    } catch (IOException e) {
        System.err.println("Error reading the file: " + e.getMessage());
        return false; // Indicate failure
    }

    // Rewrite the file with updated records
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_PHARMACISTS))) {
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


    public List<Pharmacist> loadPharmacists() throws IOException
    {
        List<Pharmacist> pharmacists = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH_PHARMACISTS));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            
            pharmacists.add(createPharmacistFromCSV(data));
        }
        br.close();
        return pharmacists;
    }
}
