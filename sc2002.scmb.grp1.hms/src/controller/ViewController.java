package controller;

import boundary.*;


public class ViewController {
    private final DoctorView doctorView = new DoctorView();
    private final PatientView patientView = new PatientView();
    private final PharmacistView pharmacistView = new PharmacistView();
    private final AdministratorView administratorView = new AdministratorView();
    

    public Object getView(String role){
        return switch (role) {
            case "D" -> doctorView;
            case "P" -> patientView;
            case "PH" -> pharmacistView;
            case "A" -> administratorView;
            default -> null;
        };
    }

}
