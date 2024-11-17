package controller;

import entity.MedicalRecord;
import entity.Patient;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import repository.DoctorRepository;
import repository.MedicalRecordRepository;
import repository.PatientRepository;

public class MedicalRecordController {
	private final MedicalRecordRepository medicalrecordrepository = new MedicalRecordRepository();
	private final DoctorRepository doctorrepository = new DoctorRepository();
	private final PatientRepository patientrepository = new PatientRepository();

	// get all patient medical record for patient view
	public void loadMedicalRecordsForPatient(String patientID) throws IOException {
		List<MedicalRecord> records = medicalrecordrepository.findRecordsByPatientId(patientID);
		Patient temp = patientrepository.findPatientById(patientID);
		if (records.isEmpty()) {

			System.out.println("+-----------------------------------------------------------+");
			System.out.println("|                      Medical Records                      |");
			System.out.println("+-----------------------------------------------------------+");
			System.out.printf("| %-15s: %-30s |\n", "Patient ID", temp.getUserId());
			System.out.printf("| %-15s: %-30s |\n", "Name", temp.getName());
			System.out.printf("| %-15s: %-30s |\n", "Date of Birth", temp.getDob());
			System.out.printf("| %-15s: %-30s |\n", "Gender", temp.getGender());
			System.out.printf("| %-15s: %-30s |\n", "Phone Number", temp.getPhoneNumber());
			System.out.printf("| %-15s: %-30s |\n", "Email", temp.getEmail());
			System.out.printf("| %-15s: %-30s |\n", "Blood Type", temp.getBloodtype());
			System.out.println("+-----------------------------------------------------------+");
			System.out.println("No medical records found");

			// System.out.println("Medical records \n" +
			// "Patient ID: " + temp.getUserId() + "\n" +
			// "Name: " + temp.getName() + "\n" +
			// "Date of Birth: " + temp.getDob() + "\n" +
			// "Gender: " + temp.getGender() + "\n" +
			// "Phone Number: " + temp.getPhoneNumber() + "\n" +
			// "Email: " + temp.getEmail() + "\n" +
			// "Blood Type: " + temp.getBloodtype());
			// System.out.println("No medical records found");
		} else {
			System.out.println("+-----------------------------------------------------------------+");
			System.out.println("|                          Medical Records                        |");
			System.out.println("+-----------------------------------------------------------------+");
			System.out.printf("| %-15s: %-46s |\n", "Patient ID", temp.getUserId());
			System.out.printf("| %-15s: %-46s |\n", "Name", temp.getName());
			System.out.printf("| %-15s: %-46s |\n", "Date of Birth", temp.getDob());
			System.out.printf("| %-15s: %-46s |\n", "Gender", temp.getGender());
			System.out.printf("| %-15s: %-46s |\n", "Phone Number", temp.getPhoneNumber());
			System.out.printf("| %-15s: %-46s |\n", "Email", temp.getEmail());
			System.out.printf("| %-15s: %-46s |\n", "Blood Type", temp.getBloodtype());
			System.out.println("+-----------------------------------------------------------------+");

			System.out.println("+-----------------------------------------------------------------+");
			System.out.println("| Doctor Name | Record ID | Diagnosis  | Treatment | Prescription |");
			System.out.println("+-----------------------------------------------------------------+");

			for (MedicalRecord record : records) {
				System.out.printf("| %-12s", doctorrepository.findDoctorById(record.getDoctorId()).getName());
				//System.out.println(doctorrepository.findDoctorById(record.getDoctorId()).getName());
				System.out.printf(record.patientMRToString());
			}
			System.out.println("+-----------------------------------------------------------------+");
		}
	}

// Get all medical records for doctor view
public void loadMedicalRecordsForDoctor(String doctorID) throws IOException {
    List<MedicalRecord> records = medicalrecordrepository.findRecordsByDoctorId(doctorID);

    System.out.println("+-----------------------------------------------------------------------------------------------+");
    System.out.println("|                                       Medical Records                                         |");
    System.out.println("+-----------------------------------------------------------------------------------------------+");
    System.out.println("| No medical records found for Doctor ID: " + doctorID + "                                        |");
    System.out.println("+-----------------------------------------------------------------------------------------------+");

    if (records.isEmpty()) {
        System.out.println("| No medical records found for Doctor ID: " + doctorID + "                                        |");
        System.out.println("+-----------------------------------------------------------------------------------------------+");
    } else {
        System.out.println("+-----------+------------+----------------+------------------+----------------+-----------------+");
        System.out.println("| Record ID | Patient ID | Patient Name   | Diagnosis        | Treatment      | Prescription    |");
        System.out.println("+-----------+------------+----------------+------------------+----------------+-----------------+");

        for (MedicalRecord record : records) {
            Patient temp = patientrepository.findPatientById(record.getPatientId());
            System.out.printf("| %-9s | %-10s | %-14s | %-16s | %-14s | %-15s |\n",
                    record.getRecordId(),
                    temp.getUserId(),
                    temp.getName(),
                    record.getDiagnosis(),
                    record.getTreatment(),
                    record.getPrescription());
        }

        System.out.println("+-----------+------------+----------------+------------------+----------------+-----------------+");
    }
}



	// genereate a new record id
	public String generateNextRecordId() throws IOException {
		String lastRecordId = medicalrecordrepository.getLastRecordId();
		String numberPart = lastRecordId.substring(1); // Remove the 'R' prefix
		int nextNumber = Integer.parseInt(numberPart) + 1; // Increment the number
		return "R" + String.format("%03d", nextNumber);

	}

	// create new medical record
	public void createMedicalRecord(String doctorID) throws IOException {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		String recordId = generateNextRecordId();

		// Validate Patient ID
		System.out.print("Enter Patient ID: ");
		String patientId = scanner.nextLine();

		// Check if Patient ID exists
		if (!doesPatientExist(patientId)) {
			System.out.println("Invalid Patient ID. Exiting record creation.");
			return; // Exit the method if the Patient ID is not found
		}

		// Proceed with record creation
		System.out.print("Enter Diagnosis: ");
		String diagnosis = scanner.nextLine();

		System.out.print("Enter Treatment: ");
		String treatment = scanner.nextLine();

		System.out.print("Enter Prescription: ");
		String prescription = scanner.nextLine();

		// Create a new MedicalRecord object
		MedicalRecord newRecord = new MedicalRecord(recordId, patientId, doctorID, diagnosis, treatment, prescription);

		// Add the record to the repository (save it to the file)
		medicalrecordrepository.addMedicalRecord(newRecord);

		System.out.println("Medical record added successfully.");
	}


	public void updateMedicalRecord(String doctorID) throws IOException {
		// Load all medical records for the doctor
		loadMedicalRecordsForDoctor(doctorID);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter Record ID to update: ");
		String recordId = scanner.nextLine();

		// Check if the Record ID exists
		if (!doesRecordExist(recordId)) {
			System.out.println("Record ID not found.");
			return;
		}

		// Proceed with the update if the record ID exists
		System.out.print("Enter new Diagnosis: ");
		String newDiagnosis = scanner.nextLine();

		System.out.print("Enter new Treatment: ");
		String newTreatment = scanner.nextLine();

		System.out.print("Enter new Prescription: ");
		String newPrescription = scanner.nextLine();

		// Attempt to update the medical record
		boolean success = medicalrecordrepository.updateMedicalRecord(recordId, newDiagnosis, newTreatment, newPrescription);

		if (success) {
			System.out.println("Medical record updated successfully.");
		} else {
			System.out.println("An error occurred. Update failed.");
		}
	}



	// Check if PatientID exists
	public boolean doesPatientExist(String patientId) throws IOException {
		List<Patient> patients = patientrepository.loadPatients();
		return patients.stream()
				.anyMatch(patient -> patient.getUserId().equalsIgnoreCase(patientId));
	}

	public boolean doesRecordExist(String recordId) throws IOException{
		List<MedicalRecord> medicalrecords = medicalrecordrepository.loadMedicalRecords();
		return medicalrecords.stream()
				.anyMatch(record -> record.getRecordId().equalsIgnoreCase(recordId));
	}


}
