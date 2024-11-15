package controller;

import entity.User;
import boundary.*;
import repository.*;
import util.PasswordUtil;

import java.io.IOException;
import java.util.Scanner;

public class AuthenticationController {

    private final DoctorRepository doctorRepository = new DoctorRepository();
    private final PatientRepository patientRepository = new PatientRepository();
    private final PharmacistRepository pharmacistRepository = new PharmacistRepository();
    private final AdministratorRepository administratorRepository = new AdministratorRepository();
    private final DoctorView doctorView = new DoctorView();
    private final PatientView patientView = new PatientView();
    private final PharmacistView pharmacistView = new PharmacistView();
    private final AdministratorView administratorView = new AdministratorView();
    private final Scanner scanner = new Scanner(System.in);

    private boolean signInDoctor() {
        User user = signIn("Doctor", doctorRepository);
        if (user == null) {
            System.out.println("Invalid ID or password. Returning to main menu.");
            System.out.println();
            return false;
        }
        System.out.println("Welcome, Doctor " + user.getName());
        doctorView.doctorMenu(user);
        return true;
    }

    private boolean signInPatient() {
        User user = signIn("Patient", patientRepository);
        if (user == null) {
            System.out.println("Invalid ID or password. Returning to main menu.");
            System.out.println();
            return false;
        }
        System.out.println("Welcome, Patient " + user.getName());
        patientView.patientMenu(user);
        return true;
    }

    private boolean signInPharmacist() {
        User user = signIn("Pharmacist", pharmacistRepository);
        if (user == null) {
            System.out.println("Invalid ID or password. Returning to main menu.");
            System.out.println();
            return false;
        }
        System.out.println("Welcome, Pharmacist " + user.getName());
        pharmacistView.pharmacistMenu(user);
        return true;
    }

    private boolean signInAdministrator() {
        User user = signIn("Administrator", administratorRepository);
        if (user == null) {
            System.out.println("Invalid ID or password. Returning to main menu.");
            System.out.println();
            return false;
        }
        System.out.println("Welcome, Administrator " + user.getName());
        administratorView.administratorMenu(user);
        return true;
    }

    private User signIn(String role, Object repository) {
        System.out.print("Enter " + role + " ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (repository instanceof DoctorRepository doctorRepo) {
            return doctorRepo.validateDoctorCredentials(id, password);
        } else if (repository instanceof PatientRepository patientRepo) {
            return patientRepo.validatePatientCredentials(id, password);
        } else if (repository instanceof PharmacistRepository pharmacistRepo) {
            return pharmacistRepo.validatePharmacistCredentials(id, password);
        } else if (repository instanceof AdministratorRepository adminRepo) {
            return adminRepo.validateAdministratorCredentials(id, password);
        } else {
            System.out.println("Invalid repository type.");
            return null;
        }
    }

    public void startSignIn() {
        while (true) {
            System.out.println("Welcome to the Hospital Management System");
            System.out.println("Select Your Domain:");
            System.out.println("1. Doctor");
            System.out.println("2. Patient");
            System.out.println("3. Pharmacist");
            System.out.println("4. Administrator");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            boolean loginSuccessful = false;
            switch (choice) {
                case 1 -> loginSuccessful = signInDoctor();
                case 2 -> loginSuccessful = signInPatient();
                case 3 -> loginSuccessful = signInPharmacist();
                case 4 -> loginSuccessful = signInAdministrator();
                case 5 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
}