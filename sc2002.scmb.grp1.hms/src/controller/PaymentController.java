package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaymentController {
    private static final String FILE_PATH_APPOINTMENT = "sc2002.scmb.grp1.hms/resource/Appointment.csv";
    private static final String FILE_PATH_PAYMENT = "sc2002.scmb.grp1.hms/resource/Payment.csv";
    
    public boolean pay(String hospitalID){
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
    System.out.println("Please enter your credit card number:");
    String creditCardNumber = scanner.nextLine().trim();

    // Validate credit card number (basic validation for length)
    if (creditCardNumber.length() < 12 || creditCardNumber.length() > 16) {
        System.out.println("Invalid credit card number. Payment failed.");
        return false;
    }

    System.out.println("Please enter the name on the credit card:");
    String creditCardName = scanner.nextLine().trim();

    // Validate credit card name
    if (creditCardName.isEmpty()) {
        System.out.println("Invalid credit card name. Payment failed.");
        return false;
    }

    List<String[]> allRecords = new ArrayList<>();
    boolean paymentProcessed = false;

    
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PAYMENT))) {
        String line;
        reader.readLine(); 
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals(hospitalID)) { 
                int numberOfUnpaid = Integer.parseInt(parts[1]);
                int numberOfPaid = Integer.parseInt(parts[2]);

                if (numberOfUnpaid > 0) {
                    numberOfPaid += numberOfUnpaid; 
                    parts[1] = "0"; 
                    parts[2] = String.valueOf(numberOfPaid); 
                    paymentProcessed = true;
                } else {
                    System.out.println("No unpaid medical records to process.");
                    return false;
                }
            }
            allRecords.add(parts); 
        }
    } catch (IOException e) {
        System.err.println("Error reading Payment.csv: " + e.getMessage());
        return false;
    }

    
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_PAYMENT))) {
        writer.write("PatientID,numberOfUnpaid,numberOfPaid"); 
        writer.newLine();
        for (String[] record : allRecords) {
            writer.write(String.join(",", record));
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Error writing to Payment.csv: " + e.getMessage());
        return false;
    }

    if (paymentProcessed) {
        System.out.println("Payment processed successfully for hospital ID: " + hospitalID);
        System.out.println("Thank you for your payment, " + creditCardName + "!");
        return true;
    }

    System.out.println("Payment could not be processed.");
    return false;

    }

    public int calculate(String hospitalID){
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PAYMENT))) {
            String line;
            reader.readLine(); // Skip the header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(hospitalID)) { 
                    int numberOfUnpaid = Integer.parseInt(parts[1]); 
                    int totalAmount = numberOfUnpaid * 70; 
                    return totalAmount;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Payment.csv: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid data format in Payment.csv: " + e.getMessage());
        }
        return -1; 
    }


    //This function just adds 1 to unpaid
    public void recalculatePaymentsCSV(String appID){

        String hospitalID = null;
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_APPOINTMENT))) {
        String line;
        reader.readLine(); // Skip header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals(appID)) { // Check if AppointmentId matches
                hospitalID = parts[1]; // Get the PatientId
                break;
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading Appointment.csv: " + e.getMessage());
    }
        List<String[]> allRecords = new ArrayList<>();
        boolean hospitalIDFound = false;
    
        // Step 1: Load all records from the Payment.csv file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PAYMENT))) {
            String line;
            reader.readLine(); // Skip the header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(hospitalID)) {
                    // Increment the unpaid column for the specified hospitalID
                    int unpaidCount = Integer.parseInt(parts[1]) + 1;
                    parts[1] = String.valueOf(unpaidCount);
                    hospitalIDFound = true;
                }
                allRecords.add(parts);
            }
        } catch (IOException e) {
            System.err.println("Error reading Payment.csv: " + e.getMessage());
            return;
        }
    
        
        if (!hospitalIDFound) {
            allRecords.add(new String[] { hospitalID, "1", "0" }); // Default: 1 unpaid, 0 paid
        }
    
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_PAYMENT))) {
            // Write the header
            writer.write("PatientID,numberOfUnpaid,numberOfPaid");
            writer.newLine();
    
            
            for (String[] record : allRecords) {
                writer.write(String.join(",", record));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to Payment.csv: " + e.getMessage());
        }
    }
}

    


