package boundary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.AdministratorController;
import controller.AppointmentController;
import controller.AppointmentOutcomeController;
import controller.MenuInterface;
import controller.SecurityQuestionsController;
import entity.User;
import entity.MedicationInventory;
import entity.ReplenishmentRequests;
import entity.Appointment;

public class AdministratorView implements MenuInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final AdministratorController adminControl = new AdministratorController();
    private final AppointmentController appControl = new AppointmentController();
    private final AppointmentOutcomeController outcomeControl = new AppointmentOutcomeController();

    public void Menu(User user) throws IOException {
        while (true) {
            System.out.println();
            System.out.println("+------------------------------------------------+");
            System.out.println("|               Administrator Menu               |");
            System.out.println("+------------------------------------------------+");
            System.out.println("| 1. View and Manage Hospital Staff              |");
            System.out.println("| 2. View Appointment Details                    |");
            System.out.println("| 3. View and Manage Inventory                   |");
            System.out.println("| 4. Set Security Question for Recovery          |");
            System.out.println("| 5. Logout                                      |");
            System.out.println("+------------------------------------------------+");
            System.out.println();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println();
                    System.out.println("+------------------------------------------------+");
                    System.out.println("|         View and Manage Hospital Staff         |");
                    System.out.println("+------------------------------------------------+");
                    System.out.println("| 1. View Staff                                  |");
                    System.out.println("| 2. Add Staff                                   |");
                    System.out.println("| 3. Remove Staff                                |");
                    System.out.println("| 4. Update Staff                                |");
                    System.out.println("| 5. Return to Admin Menu                        |");
                    System.out.println("+------------------------------------------------+");
                    System.out.println();

                    int staffChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    while (staffChoice != 5) {
                        switch (staffChoice) {
                            case 1:
                                System.out.println();
                                System.out.println("+------------------------------------------------+");
                                System.out.println("|                   View Staff                   |");
                                System.out.println("+------------------------------------------------+");
                                System.out.println("| 1. All Staff                                   |");
                                System.out.println("| 2. By Role                                     |");
                                System.out.println("| 3. By Gender                                   |");
                                System.out.println("| 4. By Age                                      |");
                                System.out.println("| 5. Return to view menu                         |");
                                System.out.println("+------------------------------------------------+");
                                System.out.println();

                                int viewChoice = scanner.nextInt();
                                scanner.nextLine();

                                while (viewChoice != 5) {
                                    List<User> view = new ArrayList<>();
                                    switch (viewChoice) {
                                        case 1:
                                            view = adminControl.viewStaff("All");
                                            adminControl.displayStaffList(view);
                                            break;
                                        case 2:
                                            System.out.println(
                                                    "Please choose staff type to view (Admin/Doctor/Pharmacist) ");
                                            String role = scanner.next();
                                            view = adminControl.viewStaff(role);
                                            adminControl.displayStaffList(view);
                                            break;
                                        case 3:
                                            System.out.println("Please choose staff gender to view (Male/Female) ");
                                            String gender = scanner.next();
                                            view = adminControl.viewStaff(gender);
                                            adminControl.displayStaffList(view);
                                            break;
                                        case 4:
                                            System.out.println(
                                                    "Please choose staff age range to view (20/30/40) ");
                                            String age = scanner.next();
                                            view = adminControl.viewStaff(age);
                                            adminControl.displayStaffList(view);
                                            break;
                                        case 5:
                                            System.out.println("Returning to view menu...");
                                            break;

                                    }
                                    System.out.println();
                                    System.out.println("+------------------------------------------------+");
                                    System.out.println("|                   View Staff                   |");
                                    System.out.println("+------------------------------------------------+");
                                    System.out.println("| 1. All Staff                                   |");
                                    System.out.println("| 2. By Role                                     |");
                                    System.out.println("| 3. By Gender                                   |");
                                    System.out.println("| 4. By Age                                      |");
                                    System.out.println("| 5. Return to view menu                         |");
                                    System.out.println("+------------------------------------------------+");
                                    System.out.println();

                                    viewChoice = scanner.nextInt();
                                    scanner.nextLine();
                                }
                                break;
                            case 2:
                                System.out.println("Choose staff role (Admin/Doctor/Pharmacist)");
                                String roleChoice = scanner.next();
                                scanner.nextLine(); // Consume newline

                                if (roleChoice.equals("Admin")) {
                                    System.out.println("Please enter user ID: ");
                                    String userID = scanner.next();
                                    System.out.println("Please enter user name: ");
                                    String userName = scanner.next();
                                    System.out.println("Please enter user gender: ");
                                    String userGender = scanner.next();
                                    System.out.println("Please enter user age: ");
                                    String userAge = scanner.next();
                                    System.out.println("Please enter user email: ");
                                    String userEmail = scanner.next();
                                    System.out.println("Please enter user contact: ");
                                    String userContact = scanner.next();
                                
                                    adminControl.addAdmin(userID, userName, roleChoice, userName, userGender, userAge, userEmail, userContact);
                                } else if (roleChoice.equals("Doctor")) {
                                    System.out.println("Please enter user ID: ");
                                    String userID = scanner.next();
                                    System.out.println("Please enter user name: ");
                                    String userName = scanner.next();
                                    System.out.println("Please enter user gender: ");
                                    String userGender = scanner.next();
                                    System.out.println("Please enter user age: ");
                                    String userAge = scanner.next();
                                    System.out.println("Please enter user specialization: ");
                                    String specialization = scanner.next();
                                    System.out.println("Please enter user email: ");
                                    String userEmail = scanner.next();
                                    System.out.println("Please enter user contact: ");
                                    String userContact = scanner.next();
                                
                                    adminControl.addDoctor(userID, userName, roleChoice, userName, userGender, userAge, specialization, userEmail, userContact);
                                } else if (roleChoice.equals("Pharmacist")) {
                                    System.out.println("Please enter user ID: ");
                                    String userID = scanner.next();
                                    System.out.println("Please enter user name: ");
                                    String userName = scanner.next();
                                    System.out.println("Please enter user gender: ");
                                    String userGender = scanner.next();
                                    System.out.println("Please enter user age: ");
                                    String userAge = scanner.next();
                                    System.out.println("Please enter user email: ");
                                    String userEmail = scanner.next();
                                    System.out.println("Please enter user contact: ");
                                    String userContact = scanner.next();
                                
                                    adminControl.addPharmacist(userID, userName, roleChoice, userName, userGender, userAge, userEmail, userContact);
                                } else {
                                    System.out.println("Invalid role choice. Please enter a valid role (Admin/Doctor/Pharmacist).");
                                }
                                break;
                            case 3:
                                System.out.println("Choose staff role (Admin/Doctor/Pharmacist)");
                                roleChoice = scanner.next();
                                scanner.nextLine(); // Consume newline

                                if (roleChoice.equals("Admin")) {
                                    System.out.println("Please enter the user ID of the Admin to remove: ");
                                    String userID = scanner.next();
                                    adminControl.removeAdmin(userID);
                                    System.out.println("Admin with ID " + userID + " has been removed.");
                                } else if (roleChoice.equals("Doctor")) {
                                    System.out.println("Please enter the user ID of the Doctor to remove: ");
                                    String userID = scanner.next();
                                    adminControl.removeDoctor(userID);
                                    System.out.println("Doctor with ID " + userID + " has been removed.");
                                } else if (roleChoice.equals("Pharmacist")) {
                                    System.out.println("Please enter the user ID of the Pharmacist to remove: ");
                                    String userID = scanner.next();
                                    adminControl.removePharmacist(userID);
                                    System.out.println("Pharmacist with ID " + userID + " has been removed.");
                                } else {
                                    System.out.println("Invalid role choice. Please enter a valid role (Admin/Doctor/Pharmacist).");
                                }
                                break;
                            case 4:
                                System.out.println("Updating staff...");
                                break;
                            case 5:
                                System.out.println("Returning...");
                                break;
                        }

                        System.out.println();
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|         View and Manage Hospital Staff         |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("| 1. View Staff                                  |");
                        System.out.println("| 2. Add Staff                                   |");
                        System.out.println("| 3. Remove Staff                                |");
                        System.out.println("| 4. Update Staff                                |");
                        System.out.println("| 5. Return to Admin Menu                        |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println();

                        staffChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                    }
                    break;
                case 2:

                    System.out.println();
                    System.out.println("+------------------------------------------------+");
                    System.out.println("|            View Appointment Details            |");
                    System.out.println("+------------------------------------------------+");
                    System.out.println("| 1. View Appointments                           |");
                    System.out.println("| 2. View Confirmed Appointments                 |");
                    System.out.println("| 3. View Appointment Outcome Records            |");
                    System.out.println("| 4. Return to Admin Menu                        |");
                    System.out.println("+------------------------------------------------+");
                    System.out.println();

                    int appointmentChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    while (appointmentChoice != 4) {
                        switch (appointmentChoice) {
                            case 1:
                                System.out.println("Viewing appointments...");
                                List<Appointment> appointments = adminControl.appointmentList();

                                for (Appointment appointment : appointments) {
                                    System.out.println("Appointment ID: " + appointment.getAppointmentId() +
                                            ", Patient ID: " + appointment.getPatientId() +
                                            ", Doctor ID: " + appointment.getDoctorId() +
                                            ", Date: " + appointment.getAppointmentDate() +
                                            ", Start Time: " + appointment.getStartTime() +
                                            ", End Time: " + appointment.getEndTime() +
                                            ", Status: " + appointment.getStatus());
                                }
                                break;
                            case 2:
                                System.out.println("Enter doctor id: ");
                                String doctorID = scanner.next();
                                scanner.nextLine(); // Consume newline

                                System.out.println("Viewing confirmed appointments for doctor...");
                                appControl.listConfirmedAppointments(doctorID);

                                break;
                            case 3:
                                System.out.println("Viewing appointment outcome records...");
                                outcomeControl.viewAppointmentOutcomes();
                                break;
                            case 4:
                                System.out.println("Returning to admin menu...");
                                break;
                        }

                        System.out.println();
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|            View Appointment Details            |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("| 1. View Appointments                           |");
                        System.out.println("| 2. View Confirmed Appointments                 |");
                        System.out.println("| 3. View Appointment Outcome Records            |");
                        System.out.println("| 4. Return to Admin Menu                        |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println();

                        appointmentChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                    }

                    break;
                case 3:

                    System.out.println();
                    System.out.println("+------------------------------------------------+");
                    System.out.println("|           View and Manage Inventory            |");
                    System.out.println("+------------------------------------------------+");
                    System.out.println("| 1. View Inventory                              |");
                    System.out.println("| 2. View Replenish Requests                     |");
                    System.out.println("| 3. Approve Replenish Request                   |");
                    System.out.println("| 4. Return to Admin Menu                        |");
                    System.out.println("+------------------------------------------------+");
                    System.out.println();

                    int inventoryChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    while (inventoryChoice != 4) {
                        switch (inventoryChoice) {
                            case 1:
                                System.out.println("Viewing medication inventory...");
                                List<MedicationInventory> inventory = adminControl.viewInventory();

                                for (MedicationInventory medication : inventory) {
                                    System.out.println("Medication: " + medication.getMedicationName() +
                                            ", Stock Level: " + medication.getStockLevel() +
                                            ", Alert Level: " + medication.getStockAlertLevel());
                                }
                                break;
                            case 2:
                                System.out.println("Viewing pending replenish requests...");

                                List<ReplenishmentRequests> pendingRequests = adminControl.viewRequests();

                                for (ReplenishmentRequests request : pendingRequests) {
                                    System.out.println("Medication: " + request.getMedicationName() +
                                            ", Request ID: " + request.getRequestId() +
                                            ", Status: " + request.getStatus());
                                }
                                break;
                            case 3:
                                System.out.println("Request ID to be approved: ");
                                int requestID = scanner.nextInt();

                                adminControl.replenishStock(requestID);
                                System.out.println("Approving replenishment request...");
                                break;

                            case 4:
                                System.out.println("Returning to admin menu...");
                                break;
                        }

                        System.out.println();
                        System.out.println("+------------------------------------------------+");
                        System.out.println("|           View and Manage Inventory            |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println("| 1. View Inventory                              |");
                        System.out.println("| 2. View Replenish Requests                     |");
                        System.out.println("| 3. Approve Replenish Request                   |");
                        System.out.println("| 4. Return to Admin Menu                        |");
                        System.out.println("+------------------------------------------------+");
                        System.out.println();

                        inventoryChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                    }

                    break;
                case 4:
                    SecurityQuestionsController sqc = new SecurityQuestionsController();
                    System.out.println("Please enter a security question");
                    String question = scanner.nextLine();
                    System.out.println("Please enter the answer");
                    String answer = scanner.nextLine();
                    if (!sqc.changeSecurityQuestionControl(user.getUserId(), question, answer)) {
                        System.out.println(
                                "Sorry your security questions were not able to be set, contact an administrator");
                    } else {
                        System.out.println("Security Questions successfully set");
                    }
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
