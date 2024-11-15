package controller;

import entity.User;
//import boundary.*;
//import repository.*;
import util.PasswordUtil;

import java.io.IOException;
import java.util.Scanner;

public class AuthenticationController {

//    private final DoctorRepository doctorRepository = new DoctorRepository();
//    private final PatientRepository patientRepository = new PatientRepository();
//    private final PharmacistRepository pharmacistRepository = new PharmacistRepository();
//    private final AdministratorRepository administratorRepository = new AdministratorRepository();

//    private final DoctorView doctorView = new DoctorView();
//    private final PatientView patientView = new PatientView();
//    private final PharmacistView pharmacistView = new PharmacistView();
//    private final AdministratorView administratorView = new AdministratorView();
//    private final Scanner scanner = new Scanner(System.in);

//    private boolean signInDoctor() {
//        User user = signIn("Doctor", doctorRepository);
//        if (user == null) {
//            System.out.println("Invalid ID or password. Returning to main menu.");
//            System.out.println();
//            return false;
//        }
//        System.out.println("Welcome, Doctor " + user.getName());
//        doctorView.doctorMenu(user);
//        return true;
//    }
//
//    private boolean signInPatient() {
//        User user = signIn("Patient", patientRepository);
//        if (user == null) {
//            System.out.println("Invalid ID or password. Returning to main menu.");
//            System.out.println();
//            return false;
//        }
//        System.out.println("Welcome, Patient " + user.getName());
//        patientView.patientMenu(user);
//        return true;
//    }
//
//    private boolean signInPharmacist() {
//        User user = signIn("Pharmacist", pharmacistRepository);
//        if (user == null) {
//            System.out.println("Invalid ID or password. Returning to main menu.");
//            System.out.println();
//            return false;
//        }
//        System.out.println("Welcome, Pharmacist " + user.getName());
//        pharmacistView.pharmacistMenu(user);
//        return true;
//    }
//
//    private boolean signInAdministrator() {
//        User user = signIn("Administrator", administratorRepository);
//        if (user == null) {
//            System.out.println("Invalid ID or password. Returning to main menu.");
//            System.out.println();
//            return false;
//        }
//        System.out.println("Welcome, Administrator " + user.getName());
//        administratorView.administratorMenu(user);
//        return true;
//    }
private final LoginController logincontroller = new LoginController();


    public void startSignIn() {
        while (true) {
            //System.out.println("Welcome to the Hospital Management System");
            System.out.println(" _   _                 _ _        _                            ");
            System.out.println("| | | | ___  ___ _ __ (_) |_ __ _| |                           ");
            System.out.println("| |_| |/ _ \\/ __| '_ \\| | __/ _` | |                           ");
            System.out.println("|  _  | (_) \\__ \\ |_) | | || (_| | |                           ");
            System.out.println("|_| |_|\\___/|___/ .__/|_|\\__\\__,_|_|                      _   ");
            System.out.println("|  \\/  | __ _ _ |_|  __ _  __ _  ___ _ __ ___   ___ _ __ | |_ ");
            System.out.println("| |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '_ ` _ \\ / _ \\ '_ \\| __|");
            System.out.println("| |  | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_ ");
            System.out.println("|_|__|_|\\__,_|_| |_|\\__,_|\\__, |\\___|_| |_| |_|\\___|_| |_|\\__|");
            System.out.println("/ ___| _   _ ___| |_ ___ _|___/__                             ");
            System.out.println("\\___ \\| | | / __| __/ _ \\ '_ ` _ \\                             ");
            System.out.println(" ___) | |_| \\__ \\ ||  __/ | | | | |                            ");
            System.out.println("|____/ \\__, |___/\\__\\___|_| |_| |_|                            ");
            System.out.println("       |___/                                                   ");
            System.out.println("\n");
            System.out.println("  What would you like to do?");


            System.out.println("  +----------------------+");
            System.out.println("  |      [1] Login       |");
            System.out.println("  |  [2] Forget Password |");
            System.out.println("  |       [3] Exit       |");
            System.out.println("  +----------------------+");


        

            //System.out.println("Select Your Domain:");
            //System.out.println("1. Doctor");
            //System.out.println("2. Patient");
            //System.out.println("3. Pharmacist");
            //System.out.println("4. Administrator");
            //System.out.println("5. Exit");
            Scanner scanner = new Scanner(System.in);
            int choice = -1;
            boolean validInput = false;
            
            // Loop to handle non-integer input
            while (!validInput) {
                System.out.print("Please select an option (1-3): ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    validInput = true; // Exit the loop if valid integer input is received
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }

            boolean loginSuccessful = false;
            switch(choice){
                case 1:
                    //TODO login gui
                    System.out.println("\n");
                    System.out.println("Please enter your credentials");

                    // Box for Hospital ID and Password
                    System.out.println("  +------------------------------+");
                    System.out.print("  | Hospital ID: ");
                    String hospitalID = scanner.nextLine();
                    System.out.print("  | Password:    ");
                    String password = scanner.nextLine();
                    System.out.println("  +------------------------------+");
                    // Attempting login message outside the box
                    System.out.println("\nAttempting login with provided credentials...");
                    loginSuccessful = logincontroller.login(hospitalID, password);
                    break;

                case 2:
                    //TODO forget password
                    break;

                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }

//            switch (choice) {
//                case 1 -> loginSuccessful = signInDoctor();
//                case 2 -> loginSuccessful = signInPatient();
//                case 3 -> loginSuccessful = signInPharmacist();
//                case 4 -> loginSuccessful = signInAdministrator();
//                case 5 -> {
//                    System.out.println("Exiting the system. Goodbye!");
//                    return;
//                }
//                default -> System.out.println("Invalid choice, please try again.");
//            }
        }
    }
}