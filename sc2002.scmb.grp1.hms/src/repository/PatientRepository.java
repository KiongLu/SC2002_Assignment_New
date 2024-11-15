package repository;

import controller.ValidationInterface;
import entity.Patient;
import entity.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PatientRepository implements ValidationInterface {

	private static final String FILE_PATH_PATIENT = "sc2002.scmb.grp1.hms\\resource\\Patient.csv";

 // create Patient object using csv
    private Patient createPatientFromCSV(String[] parts) {
        // Create a Patient using the CSV parts in the exact order of columns
        return new Patient(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]);
    }

    //Validate Password
	public User validateCredentials(String id, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_PATIENT))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id) && parts[3].equals(password)) { // UserID and Password
                    return createPatientFromCSV(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public List<Patient> loadPatients() throws IOException {
        List<Patient> patients = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream(FILE_PATH_PATIENT))));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            
            patients.add(createPatientFromCSV(data));
        }
        br.close();
        return patients;
    }

    // Find a patient by their PatientID
    public Patient findPatientById(String patientId) throws IOException {
        List<Patient> patients = loadPatients();
        return patients.stream()
                .filter(patient -> patient.getUserId().equals(patientId))
                .findFirst()
                .orElse(null);
    }


    public boolean updatePatient(Patient updatedPatient) throws IOException{
        List<Patient> patients = loadPatients();
        boolean isUpdated = false;

        for (Patient patient : patients){
            if (patient.getUserId().equals(updatedPatient.getUserId())){
                patient.setEmail(updatedPatient.getEmail());
                patient.setPhoneNumber(updatedPatient.getPhoneNumber());
                isUpdated = true;
                break;
            }
        }

        //update CSV with updated data
        if (isUpdated){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_PATIENT, false))) {
                writer.write("UserID,Name,Role,Password,Gender,Age,PhoneNumber,Email,DOB,BloodType");
                writer.newLine();
                for (Patient patient : patients){
                    writer.write(String.join(",",
                            patient.getUserId(),
                            patient.getName(),
                            patient.getRole(),
                            patient.getPassword(),
                            patient.getGender(),
                            patient.getAge(),
                            patient.getPhoneNumber(),
                            patient.getEmail(),
                            patient.getDob(),
                            patient.getBloodtype()));
                    writer.newLine();
                }
            }
        }
        return isUpdated;
    }
}
