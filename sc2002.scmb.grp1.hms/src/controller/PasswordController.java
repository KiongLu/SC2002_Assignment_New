package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordController {

    public String hashPassword(String password) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Convert the password to a byte array and hash it
            byte[] hashBytes = digest.digest(password.getBytes());
            
            // Convert the hashed bytes to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b); // Convert each byte to a two-digit hex
                if (hex.length() == 1) {
                    hexString.append('0'); // Pad single-digit hex with a leading 0
                }
                hexString.append(hex);
            }
            
            return hexString.toString(); // Return the hashed password as a hex string
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // Return null if an error occurs
        }
    }

    public boolean changePassword(String hospitalID, String newPassword){
        String hashedPassword = hashPassword(newPassword);
        String role = extractPrefix(hospitalID);
        RepositoryController repositoryController = new RepositoryController();
        PasswordChangerInterface repository = (PasswordChangerInterface) repositoryController.getRepository(role);
        if(repository == null){
            System.out.println("Invalid ID or password. Returning to main menu.");
            System.out.println();
            return false;
        }
        return (repository.changePassword(hospitalID, hashedPassword));
    }


    private static String extractPrefix(String hospitalId) {
        if (hospitalId.startsWith("PH")) {
            return "PH"; // Return "Ph" if the ID starts with "Ph"
        } else if (hospitalId.startsWith("P")) {
            return "P"; // Return "P" if the ID starts with "P"
        } else if (hospitalId.startsWith("A")) {
            return "A";
        } else if (hospitalId.startsWith("D")) {
            return "D";
        }
        return "NULL"; // Return an empty string if there's no matching prefix
    }


}
