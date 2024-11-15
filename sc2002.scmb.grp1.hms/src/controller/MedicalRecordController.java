package controller;

import entity.MedicalRecord;
import entity.Patient;
import repository.DoctorRepository;
import repository.MedicalRecordRepository;
import repository.PatientRepository;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MedicalRecordController {
	private final MedicalRecordRepository medicalrecordrepository = new MedicalRecordRepository();
	private final DoctorRepository doctorrepository = new DoctorRepository();
	private final PatientRepository patientrepository = new PatientRepository();
	
	//get all patient medical record for patient view
	public void loadMedicalRecordsForPatient(String patientID) throws IOException {
        List<MedicalRecord> records = medicalrecordrepository.findRecordsByPatientId(patientID);
        Patient temp = patientrepository.findPatientById(patientID);
        if (records.isEmpty()) {
        	System.out.println("Medical records \n" +
                    "Patient ID:    " + temp.getUserId() + "\n" +
                    "Name:          " + temp.getName() + "\n" +
                    "Date of Birth: " + temp.getDob() + "\n" +
                    "Gender:        " + temp.getGender() + "\n" +
                    "Phone Number:  " + temp.getPhoneNumber() + "\n" +
                    "Email:         " + temp.getEmail() + "\n" +
                    "Blood Type:    " + temp.getBloodtype());
            System.out.println("No medical records found");
        } else {
            System.out.println("Medical records \n" +
                    "Patient ID:    " + temp.getUserId() + "\n" +
                    "Name:          " + temp.getName() + "\n" +
                    "Date of Birth: " + temp.getDob() + "\n" +
                    "Gender:        " + temp.getGender() + "\n" +
                    "Phone Number:  " + temp.getPhoneNumber() + "\n" +
                    "Email:         " + temp.getEmail() + "\n" +
                    "Blood Type:    " + temp.getBloodtype());

            for (MedicalRecord record : records) {
            	System.out.println("Doctor Name:  " + doctorrepository.findDoctorById(record.getDoctorId()).getName());
            	System.out.println(record.toString());
            	System.out.println();
            }
        }
    }
	
	//get all medical record for doctpr view
	 public void loadMedicalRecordsForDoctor(String doctorID) throws IOException {
	        List<MedicalRecord> records = medicalrecordrepository.findRecordsByDoctorId(doctorID);

	        if (records.isEmpty()) {
	            System.out.println("No medical records found for Doctor ID: " + doctorID);
	        } else {
	            System.out.println("Medical records for Doctor ID: " + doctorID);
	            for (MedicalRecord record : records) {
	            	System.out.println();
	            	Patient temp = patientrepository.findPatientById(record.getPatientId());
	            	System.out.println("Medical records \n" +
	            	"Patient ID: " + temp.getUserId() + "\n" +
                    "Name: " + temp.getName() + "\n" +
                    "Date of Birth: " + temp.getDob() + "\n" +
                    "Gender: " + temp.getGender() + "\n" +
                    "Phone Number: " + temp.getPhoneNumber() + "\n" +
                    "Email: " + temp.getEmail() + "\n" +
                    "Blood Type: " + temp.getBloodtype());
	            	System.out.println("Patient Name: " + patientrepository.findPatientById(record.getPatientId()).getName());
	                System.out.println(record.toString());
	                System.out.println();
	            }
	        }
	  }
	 
	 //genereate a new record id
	 public String generateNextRecordId() throws IOException {
		 String lastRecordId = medicalrecordrepository.getLastRecordId();
	     String numberPart = lastRecordId.substring(1);  // Remove the 'R' prefix
	     int nextNumber = Integer.parseInt(numberPart) + 1;  // Increment the number
	     return "R" + String.format("%03d", nextNumber);
	     
	 }
	 
	 // create new medical record
	 public void createMedicalRecord(String DoctorID) throws IOException {
		Scanner scanner = new Scanner(System.in);
		
		String recordId = generateNextRecordId();
		
		System.out.print("Enter Patient ID: ");
		String patientId = scanner.nextLine();
		
		String doctorId = DoctorID;
		
		System.out.print("Enter Diagnosis: ");
		String diagnosis = scanner.nextLine();
		
		System.out.print("Enter Treatment: ");
		String treatment = scanner.nextLine();
		
		System.out.print("Enter Prescription: ");
		String prescription = scanner.nextLine();
		
		// Create a new MedicalRecord object
		MedicalRecord newRecord = new MedicalRecord(recordId, patientId, doctorId, diagnosis, treatment, prescription);
		
		// Add the record to the repository (save it to the file)
		medicalrecordrepository.addMedicalRecord(newRecord);
		
		System.out.println("Medical record added successfully.");
      }
	 
	 public void updateMedicalRecord(String doctorID) throws IOException {
		loadMedicalRecordsForDoctor(doctorID);
	    Scanner scanner = new Scanner(System.in);
	    
	    System.out.print("Enter Record ID to update: ");
	    String recordId = scanner.nextLine();
	    
	    System.out.print("Enter new Diagnosis: ");
	    String newDiagnosis = scanner.nextLine();
	    
	    System.out.print("Enter new Treatment: ");
	    String newTreatment = scanner.nextLine();
	    
	    System.out.print("Enter new Prescription: ");
	    String newPrescription = scanner.nextLine();
	    
	    boolean success = medicalrecordrepository.updateMedicalRecord(recordId, newDiagnosis, newTreatment, newPrescription);
	    
	    if (success) {
	        System.out.println("Medical record updated successfully.");
	    } else {
	        System.out.println("Record ID not found. Update failed.");
	    }
	}
	 
	 
}
