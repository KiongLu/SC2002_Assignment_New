package controller;

import entity.User;

public class LoginController {

    public boolean login(String hospitalID, String password){
        String role = extractPrefix(hospitalID);
        RepositoryController repositoryController = new RepositoryController();
        ValidationInterface repository = (ValidationInterface) repositoryController.getRepository(role);
        User user = repository.validateCredentials(hospitalID, password);
        if(user == null){
            System.out.println("Invalid ID or password. Returning to main menu.");
            System.out.println();
            return false;
        }
        else{
            ViewController viewController = new ViewController();
            MenuInterface view = (MenuInterface) viewController.getView(role);
            view.Menu(user);
            return true;
        }









//        switch(role){
//            case "D":
//                System.out.println("Welcome, Doctor " + user.getName());
//                doctorView.doctorMenu(user);
//                return true;
//
//            case "P":
//                System.out.println("Welcome, Patient " + user.getName());
//                patientView.patientMenu(user);
//                return true;
//
//            case "Ph":
//                System.out.println("Welcome, Pharmacist " + user.getName());
//                pharmacistView.pharmacistMenu(user);
//                return true;
//
//            case "A":
//                System.out.println("Welcome, Administrator " + user.getName());
//                administratorView.administratorMenu(user);
//                return true;
//
//            default:
//                return false;
//
//        }


    }





    private static String extractPrefix(String hospitalId) {
        if (hospitalId.startsWith("PH")) {
            return "PH"; // Return "Ph" if the ID starts with "Ph"
        } else if (hospitalId.startsWith("P")) {
            return "P"; // Return "P" if the ID starts with "P"
        } else if (hospitalId.startsWith("A")) {
            return "A";
        } else if (hospitalId.startsWith("D")) {
            return "D";
        }
        return "NULL"; // Return an empty string if there's no matching prefix
    }
//
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

//    private User signIn(String role, Object repository) {
//        System.out.print("Enter " + role + " ID: ");
//        String id = scanner.nextLine();
//        System.out.print("Enter Password: ");
//        String password = scanner.nextLine();
//
//        if (repository instanceof DoctorRepository doctorRepo) {
//            return doctorRepo.validateDoctorCredentials(id, password);
//        } else if (repository instanceof PatientRepository patientRepo) {
//            return patientRepo.validatePatientCredentials(id, password);
//        } else if (repository instanceof PharmacistRepository pharmacistRepo) {
//            return pharmacistRepo.validatePharmacistCredentials(id, password);
//        } else if (repository instanceof AdministratorRepository adminRepo) {
//            return adminRepo.validateAdministratorCredentials(id, password);
//        } else {
//            System.out.println("Invalid repository type.");
//            return null;
//        }
//    }


}