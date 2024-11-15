package boundary;

import java.util.Scanner;

import controller.MenuInterface;
import entity.User;

public class AdministratorView implements MenuInterface {
	private final Scanner scanner = new Scanner(System.in);
	public void Menu(User user) {
        while (true) {
        	System.out.println();
            System.out.println("Administrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.println();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 5) {
                System.out.println("Logging out...");
                break;
            }
            // Add functionality for each menu option here
        }
    }
}
