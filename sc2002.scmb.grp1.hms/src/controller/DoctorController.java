package controller;

import entity.Doctor;
import repository.DoctorRepository;
import java.io.IOException;


/**
 * Manages doctor-related operations.
 */
public class DoctorController {
	private DoctorRepository doctorRepository = new DoctorRepository();
    
    
 // Get a doctor by DoctorID
    public Doctor getDoctorById(String doctorId) throws IOException {
        return doctorRepository.findDoctorById(doctorId);
    }

    // Example: Print the doctor's information
    public void printDoctorInfo(String doctorId) {
        try {
            Doctor doctor = getDoctorById(doctorId);
            if (doctor != null) {
                System.out.println("Doctor Details:\n" + doctor);
            } else {
                System.out.println("No doctor found with ID: " + doctorId);
            }
        } catch (IOException e) {
            System.out.println("Error retrieving doctor information: " + e.getMessage());
        }
    }
}

