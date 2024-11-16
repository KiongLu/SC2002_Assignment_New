package boundary;

import java.io.IOException;
import java.util.Scanner;

import controller.AdministratorController;
import controller.MenuInterface;
import entity.User;

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
            System.out.println("| 4. Replenish Medication Inventory              |");
            System.out.println("| 5. Logout                                      |");
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
                    //adminControl.viewInventory();
                    break;
                case 4:
                    System.out.println("Medicine to be restocked: ");
                    String medicine = scanner.next();

                    System.out.println("Quantity to be increased by: ");
                    int quantity = scanner.nextInt();

                    adminControl.replenishStock(medicine, quantity);
                    System.out.println("Approving replenishment requests...");
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;  // Exit the menu loop
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
