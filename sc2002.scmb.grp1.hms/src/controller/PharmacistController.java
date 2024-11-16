package controller;


import java.io.IOException;
import entity.MedicationInventory;
import repository.MedicationInventoryRepository;
import repository.ReplenishmentRequestRepository;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class PharmacistController implements InventoryManagement,ReplenishmentRequestService {
    private MedicationInventoryRepository inventoryRepository;
    private ReplenishmentRequestRepository replenishmentRequestRepository;
    private final Scanner scanner = new Scanner(System.in);

    public PharmacistController(MedicationInventoryRepository inventoryRepository, ReplenishmentRequestRepository replenishmentRequestRepository) {
        this.inventoryRepository = inventoryRepository;
        this.replenishmentRequestRepository = replenishmentRequestRepository;


    }

    @Override
    public List<MedicationInventory> checkInventory(String medicationName) {
        try{
            return inventoryRepository.getMedicationByName(medicationName);
        } catch(IOException e) {
            System.err.println("Error finding the Medication. Medication does not exist");
            return null;
        }
    }

    @Override
    public List<MedicationInventory> getAllInventory() throws IOException {
        return inventoryRepository.loadAllMedications();
    }

    @Override
    public List<MedicationInventory> lowStockAlert() throws IOException {
        // Prepare a list to store medications below the threshold
        List<MedicationInventory> lowStockMedications = new ArrayList<>();
        List<MedicationInventory> allMedications = inventoryRepository.loadAllMedications();

        // Iterate through all medications and find those below the threshold
        for (MedicationInventory medication : allMedications) {
            if (medication.getStockLevel() < 60) {
                lowStockMedications.add(medication);
            }
        }

        // Print the low stock medications in the same format
        if (!lowStockMedications.isEmpty()) {
            System.out.println("+------------------------------------------------+");
            System.out.println("|            Low Stock Medication Alert          |");
            System.out.println("+------------------------------------------------+");
            for (MedicationInventory medication : lowStockMedications) {
                System.out.printf("| Name: %-35s | Stock Level: %3d |\n",
                        medication.getMedicationName(), medication.getStockLevel());
            }
            System.out.println("+------------------------------------------------+");
        } else {
            System.out.println("+------------------------------------------------+");
            System.out.println("|        No medications below stock alert.       |");
            System.out.println("+------------------------------------------------+");
        }

        return lowStockMedications;
    }

    @Override
    public void submitReplenishmentRequest(String medicationName) throws IOException {
        System.out.println("+------------------------------------------------+");
        System.out.println("|           Submit Replenishment Request         |");
        System.out.println("+------------------------------------------------+");

        // Use checkInventory to see if the medication exists in the inventory
        List<MedicationInventory> inventoryList = checkInventory(medicationName);

        // Check if the medication exists (inventoryList is non-null and non-empty)
        if (inventoryList == null || inventoryList.isEmpty()) {
            System.out.println("| Error: The medication '" + medicationName + "' does not exist in the inventory. |");
            System.out.println("+------------------------------------------------+\n");
            return;
        }

        // Prompt the pharmacist to input the quantity for replenishment
        System.out.printf("| Enter the quantity to be replenished for '%s': ", medicationName);
        int quantity;
        while (true) {
            try {
                quantity = Integer.parseInt(scanner.nextLine());
                if (quantity <= 0) {
                    System.out.println("| Quantity must be greater than zero. Please try again. |");
                    System.out.print("| Enter the quantity: ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("| Invalid input. Please enter a valid positive integer. |");
                System.out.print("| Enter the quantity: ");
            }
        }

        // If the medication exists, proceed with submitting the replenishment request
        replenishmentRequestRepository.saveReplenishmentRequest(medicationName, quantity);
        System.out.println("+------------------------------------------------+");
        System.out.printf("| Replenishment request submitted successfully!  |\n");
        System.out.printf("| Medication: %-35s |\n", medicationName);
        System.out.printf("| Quantity: %-36d |\n", quantity);
        System.out.println("+------------------------------------------------+\n");
    }


}
