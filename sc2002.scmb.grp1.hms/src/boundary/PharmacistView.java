package boundary;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;

import controller.AppointmentOutcomeController;
import controller.PharmacistController;
import entity.MedicationInventory;
import repository.AppointmentOutcomeRepository;
import repository.MedicationInventoryRepository;
import entity.User;

public class PharmacistView {
	private final Scanner scanner = new Scanner(System.in);
    private final MedicationInventoryRepository inventoryRepository = new MedicationInventoryRepository();
    private final AppointmentOutcomeRepository outcomeRepository = new AppointmentOutcomeRepository();
    private final AppointmentOutcomeController outcomecontroller = new AppointmentOutcomeController();
    private final PharmacistController pharmacistController = new PharmacistController(inventoryRepository, outcomeRepository);
    public void pharmacistMenu(User user) {
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

            if (choice == 3) {
                try {
                    viewMedicationInventory();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }// Call the method to view inventory
            }

            if (choice == 4) {
                try {
                    submitReplenishmentRequest();
                } catch (IOException e) {
                    System.err.println("Error processing replenishment request: " + e.getMessage());
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

    public void viewMedicationInventory() throws IOException {
        System.out.println("\nMedication Inventory:");

        // Call getAllInventory() on the pharmacistController instance
        List<MedicationInventory> medications = pharmacistController.getAllInventory();
        if (medications.isEmpty()) {
            System.out.println("No medications found in the inventory.");
        } else {
            for (MedicationInventory medication : medications) {
                System.out.printf("Name: %s, Stock Level: %d, Stock Alert Level: %d\n",
                        medication.getMedicationName(), medication.getStockLevel(), medication.getStockAlertLevel());
            }
        }
    }

    private void submitReplenishmentRequest() throws IOException {
        System.out.print("Enter the medication name: ");
        String medicationName = scanner.nextLine();

        // Call the controller to handle the replenishment request
        pharmacistController.submitReplenishmentRequest(medicationName);
    }



    
}
