package controller;

import entity.Patient;
import repository.PatientRepository;
import java.util.Scanner;
import java.io.IOException;


public class PatientController {
    private PatientRepository patientRepository = new PatientRepository();
    private final Scanner scanner = new Scanner(System.in);


    // get a patient by PatientID
    public Patient getPatientById(String patientId) throws IOException{
        return patientRepository.findPatientById(patientId);
        
    }

    // This is the method for updating patient info (only email and phone number)
    public void updatePatientInfo(String patientId) throws IOException{ 
        try {
            Patient patient = getPatientById(patientId);

            if (patient == null){
                System.out.println("Patient not found!");
                return;
            }

            System.out.println("Current Email: " + patient.getEmail());
            System.out.println("Current Phone Number: " + patient.getPhoneNumber());

            System.out.println("Enter new email (leave blank to keep current email): ");
            String newEmail = scanner.nextLine();
            if(!newEmail.isEmpty()){
                patient.setEmail(newEmail);
            }

            System.out.println("Enter new phone number (leave blank to keep current number): ");
            String newPhoneNumber = scanner.nextLine();
            if(!newPhoneNumber.isEmpty()){
                patient.setPhoneNumber(newPhoneNumber);
            }

            boolean success = patientRepository.updatePatient(patient);
            if (success){
                System.out.println("Contact information updated successfully.");
            }
            else{
                System.out.println("Failed to update contact information.");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
