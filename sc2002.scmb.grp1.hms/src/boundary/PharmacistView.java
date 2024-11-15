package boundary;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;

import controller.AppointmentOutcomeController;
import controller.PharmacistController;
import controller.MenuInterface;
import entity.User;
import entity.MedicationInventory;
import repository.MedicationInventoryRepository;
import repository.AppointmentOutcomeRepository;
import repository.ReplenishmentRequestRepository;

public class PharmacistView implements MenuInterface {
	private final Scanner scanner = new Scanner(System.in);
    private final AppointmentOutcomeController outcomecontroller = new AppointmentOutcomeController();
    private final MedicationInventoryRepository inventoryRepository = new MedicationInventoryRepository();
    private final AppointmentOutcomeRepository outcomeRepository = new AppointmentOutcomeRepository();
    private final ReplenishmentRequestRepository replenishmentRequestRepository = new ReplenishmentRequestRepository();
    private final PharmacistController pharmacistController =  new PharmacistController(inventoryRepository, outcomeRepository,replenishmentRequestRepository);// Create an instance
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

            if (choice == 3) {
                viewMedicationInventory(); // Call method to view inventory
            }

            if(choice == 4){
                submitReplenishmentRequest();
            }
        }
    }

    public void displayAndUpdatePendingOutcomes() throws IOException {
        outcomecontroller.displayPendingAppointmentOutcomes();

        System.out.print("Enter the Outcome ID to change the prescription status to 'Dispensed': ");
        String outcomeId = scanner.nextLine();

        outcomecontroller.changePrescriptionStatusToDispensed(outcomeId);
    }

    public List<MedicationInventory> getAllInventory() throws IOException {
        return inventoryRepository.loadAllMedications();
    }

    public void viewMedicationInventory() {
        System.out.println("\nMedication Inventory:");
        try {
            List<MedicationInventory> medications = pharmacistController.getAllInventory();
            if (medications.isEmpty()) {
                System.out.println("No medications found in the inventory.");
            } else {
                for (MedicationInventory medication : medications) {
                    System.out.printf("Name: %s, Stock Level: %d, Stock Alert Level: %d\n",
                            medication.getMedicationName(), medication.getStockLevel(), medication.getStockAlertLevel());
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load medication inventory: " + e.getMessage());
        }
    }

    private void submitReplenishmentRequest() {
        System.out.print("Enter the medication name: ");
        String medicationName = scanner.nextLine();
        try {
            pharmacistController.submitReplenishmentRequest(medicationName);
        } catch (IOException e) {
            System.err.println("Error submitting replenishment request: " + e.getMessage());
        }
    }



    
}
