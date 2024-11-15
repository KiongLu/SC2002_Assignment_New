package boundary;

import java.io.IOException;
import java.util.Scanner;

import controller.AppointmentOutcomeController;
import controller.MenuInterface;
import entity.User;

public class PharmacistView implements MenuInterface {
	private final Scanner scanner = new Scanner(System.in);
    private final AppointmentOutcomeController outcomecontroller = new AppointmentOutcomeController();
    public void Menu(User user) {
        while (true) {
        	System.out.println();
            System.out.println("Pharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.println();

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 5) {
                System.out.println("Logging out...");
                break;
            }
            // Add functionality for each menu option here

            if (choice == 1){
                try {
                    outcomecontroller.viewAppointmentOutcomes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if (choice == 2){
                try {
                    displayAndUpdatePendingOutcomes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void displayAndUpdatePendingOutcomes() throws IOException {
        outcomecontroller.displayPendingAppointmentOutcomes();

        System.out.print("Enter the Outcome ID to change the prescription status to 'Dispensed': ");
        String outcomeId = scanner.nextLine();

        outcomecontroller.changePrescriptionStatusToDispensed(outcomeId);
    }

    
}
