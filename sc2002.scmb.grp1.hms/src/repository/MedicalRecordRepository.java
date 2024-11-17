package repository;

import entity.MedicalRecord;
import util.CSVUtil;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
//Ignore

public class MedicalRecordRepository {
	private static final String FILE_PATH_MEDICALRECORD = "sc2002.scmb.grp1.hms//resource//MedicalRecord.csv";
	// private static final CSVUtil csvutil = new CSVUtil(); 
	
	// Load all medical records from the CSV file
    public List<MedicalRecord> loadMedicalRecords() throws IOException {
        List<MedicalRecord> records = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH_MEDICALRECORD));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            records.add(new MedicalRecord(data[0], data[1], data[2], data[3], data[4], data[5]));
        }
        br.close();
        return records;
    }
    
	// Find all medical records for a specific PatientID
    public List<MedicalRecord> findRecordsByPatientId(String patientID) throws IOException {
        List<MedicalRecord> records = loadMedicalRecords();
        // Filter the records based on the PatientID
        return records.stream()
                .filter(record -> record.getPatientId().equals(patientID))
                .collect(Collectors.toList());
    }
    
    
 // Find all medical records for a specific DoctorID
    public List<MedicalRecord> findRecordsByDoctorId(String doctorID) throws IOException {
        List<MedicalRecord> records = loadMedicalRecords();
        // Filter the records based on the DoctorID
        return records.stream()
                .filter(record -> record.getDoctorId().equals(doctorID))
                .collect(Collectors.toList());
    }
    
 // Add a new medical record to the CSV file
    public void addMedicalRecord(MedicalRecord newRecord) throws IOException {
        File file = new File(FILE_PATH_MEDICALRECORD);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // If the file is not empty, write a newline first
            if (file.length() > 0) {
                writer.newLine();
            }

            // Write the new record
            writer.write(newRecord.getRecordId() + "," + newRecord.getPatientId() + "," + newRecord.getDoctorId() + ","
                    + newRecord.getDiagnosis() + "," + newRecord.getTreatment() + "," + newRecord.getPrescription());
            
            writer.flush();
        }
        CSVUtil.removeEmptyRows(FILE_PATH_MEDICALRECORD);
    }

 // Get the last recordId from the existing records
    public String getLastRecordId() throws IOException {
        List<MedicalRecord> records = loadMedicalRecords();
        if (records.isEmpty()) {
            return "R000";  // Return the base value if no records exist
        }
        String lastRecordId = records.get(records.size() - 1).getRecordId();
        return lastRecordId;
    }
    
    
 // Update a specific medical record's diagnosis, treatment, and prescription based on recordId
    public boolean updateMedicalRecord(String recordId, String newDiagnosis, String newTreatment, String newPrescription) throws IOException {
        List<MedicalRecord> records = loadMedicalRecords();
        boolean recordFound = false;

        for (MedicalRecord record : records) {
            if (record.getRecordId().equals(recordId)) {
                // Update the medical record fields
                record.setDiagnosis(newDiagnosis);
                record.setTreatment(newTreatment);
                record.setPrescription(newPrescription);
                recordFound = true;
                break; // Exit the loop once the record is found and updated
            }
        }

        if (recordFound) {
            // Write the updated records back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_MEDICALRECORD))) {
                for (MedicalRecord record : records) {
                    writer.write(record.getRecordId() + "," + record.getPatientId() + "," + record.getDoctorId() + ","
                            + record.getDiagnosis() + "," + record.getTreatment() + "," + record.getPrescription());
                    writer.newLine();
                }
            }
        }

        return recordFound;
    }
    
}
