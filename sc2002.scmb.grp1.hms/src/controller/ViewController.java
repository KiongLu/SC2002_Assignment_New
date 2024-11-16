package controller;

import boundary.*;

import java.util.Scanner;

public class ViewController {
    private final DoctorView doctorView = new DoctorView();
    private final PatientView patientView = new PatientView();
    private final PharmacistView pharmacistView = new PharmacistView();
    private final AdministratorView administratorView = new AdministratorView();
    private final PasswordForgetView passwordForgetView = new PasswordForgetView();

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
