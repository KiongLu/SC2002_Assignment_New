package boundary;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import controller.AdministratorController;
import controller.MenuInterface;
import controller.SecurityQuestionsController;
import entity.User;
import entity.MedicationInventory;
import entity.ReplenishmentRequests;

public class AdministratorView implements MenuInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final AdministratorController adminControl = new AdministratorController();

    public void Menu(User user) throws IOException {
        while (true) {
        	System.out.println();
            System.out.println("+------------------------------------------------+");
            System.out.println("|               Administrator Menu               |");
            System.out.println("+------------------------------------------------+");
            System.out.println("| 1. View and Manage Hospital Staff              |");
            System.out.println("| 2. View Appointment Details                    |");
            System.out.println("| 3. View Medication Inventory                   |");
            System.out.println("| 4. View Pending Replenish Requests             |");
            System.out.println("| 5. Approve Replenish Requests                  |");
            System.out.println("| 6. Set Security Question for Recovery          |");
            System.out.println("| 7. Logout                                      |");
            System.out.println("+------------------------------------------------+");
            System.out.println();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Viewing and managing hospital staff...");
                    // Implement functionality here
                    break;
                case 2:
                    System.out.println("Viewing appointment details...");
                    // Implement functionality here
                    break;
                case 3:
                    System.out.println("Viewing medication inventory...");
                    List<MedicationInventory> inventory = adminControl.viewInventory();

                    for (MedicationInventory medication : inventory)
                    {
                        System.out.println("Medication: " + medication.getMedicationName() +
                        ", Stock Level: " + medication.getStockLevel() +
                        ", Alert Level: " + medication.getStockAlertLevel());
                    }
                    break;
                case 4:
                    System.out.println("Viewing pending replenish requests...");
                    List<ReplenishmentRequests> pendingRequests = adminControl.viewRequests();
                    
                    for (ReplenishmentRequests request : pendingRequests)
                    {
                        System.out.println("Medication: " + request.getMedicationName() +
                        ", Request ID: " + request.getRequestId() +
                        ", Status: " + request.getStatus());
                    }
                    break;
                case 5:
                    System.out.println("Medicine to be restocked: ");
                    String medicine = scanner.next();

                    System.out.println("Quantity to be increased by: ");
                    int quantity = scanner.nextInt();

                    adminControl.replenishStock(medicine, quantity);
                    System.out.println("Approving replenishment requests...");
                    break;
                case 6:
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

                    
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
