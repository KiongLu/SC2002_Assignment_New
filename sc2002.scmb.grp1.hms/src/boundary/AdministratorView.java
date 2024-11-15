package boundary;

import java.util.Scanner;

import controller.AdministratorController;
import entity.User;

public class AdministratorView {
	private final Scanner scanner = new Scanner(System.in);

	public void administratorMenu(User user) {
        while (true) {
        	System.out.println();
            System.out.println("Administrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments details");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Replenish Medication Inventory");
            System.out.println("5. Logout");
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
                    // Implement functionality here
                    break;
                case 4:
                    System.out.println("Please enter the medication name: ");
                    String medicineName = scanner.next();

                    System.out.println("Please enter the quantity stock level is to be increased by: ");
                    int quantity = scanner.nextInt();



                    System.out.println("Replenishing medication inventory...");
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
