package controller;

import repository.*;

public class RepositoryController {
    private  final DoctorRepository doctorRepository = new DoctorRepository();
    private  final PatientRepository patientRepository = new PatientRepository();
    private  final PharmacistRepository pharmacistRepository = new PharmacistRepository();
    private  final AdministratorRepository administratorRepository = new AdministratorRepository();

    public Object getRepository(String role) {
        return switch (role) {
            case "D" -> doctorRepository;
            case "P" -> patientRepository;
            case "PH" -> pharmacistRepository;
            case "A" -> administratorRepository;
            default -> null;
        };


    }
}
