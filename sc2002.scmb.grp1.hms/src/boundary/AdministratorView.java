package boundary;

import java.io.IOException;
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

                    while (staffChoice != 5){
                        
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

                    while (appointmentChoice != 4)
                    {
                        switch(appointmentChoice)
                        {
                            case 1:
                                System.out.println("Viewing appointments...");
                                List<Appointment> appointments = adminControl.appointmentList();
            
                                for (Appointment appointment : appointments)
                                {
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

                   while (inventoryChoice != 4)
                   {
                    switch(inventoryChoice)
                    {
                        case 1:
                            System.out.println("Viewing medication inventory...");
                            List<MedicationInventory> inventory = adminControl.viewInventory();
        
                            for (MedicationInventory medication : inventory)
                            {
                                System.out.println("Medication: " + medication.getMedicationName() +
                                ", Stock Level: " + medication.getStockLevel() +
                                ", Alert Level: " + medication.getStockAlertLevel());
                            }
                            break;
                        case 2:
                            System.out.println("Viewing pending replenish requests...");

                            List<ReplenishmentRequests> pendingRequests = adminControl.viewRequests();
                    
                            for (ReplenishmentRequests request : pendingRequests)
                            {
                                System.out.println("Medication: " + request.getMedicationName() +
                                ", Request ID: " + request.getRequestId() +
                                ", Status: " + request.getStatus());
                            }
                            break;
                        case 3:
                            System.out.println("Medicine to be restocked: ");
                            String medicine = scanner.next();
        
                            System.out.println("Quantity to be increased by: ");
                            int quantity = scanner.nextInt();
        
                            adminControl.replenishStock(medicine, quantity);
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
                    if(!sqc.changeSecurityQuestionControl(user.getUserId(), question, answer)){
                        System.out.println("Sorry your security questions were not able to be set, contact an administrator");
                        }
                    else{
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
