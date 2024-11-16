package controller;


import java.io.IOException;
import entity.MedicationInventory;
import repository.MedicationInventoryRepository;
import repository.ReplenishmentRequestRepository;
import java.util.List;
import java.util.Scanner;


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
    public void submitReplenishmentRequest(String medicationName) throws IOException {
        // Use checkInventory to see if the medication exists in the inventory
        List<MedicationInventory> inventoryList = checkInventory(medicationName);

        // Check if the medication exists (inventoryList is non-null and non-empty)
        if (inventoryList == null || inventoryList.isEmpty()) {
            System.out.println("Error: The medication '" + medicationName + "' does not exist in the inventory.");
            return;
        }

        // Prompt the pharmacist to input the quantity for replenishment
        System.out.print("Enter the quantity to be replenished for '" + medicationName + "': ");
        int quantity;
        while (true) {
            try {
                quantity = Integer.parseInt(scanner.nextLine());
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than zero. Please try again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid positive integer.");
            }
        }

        // If the medication exists, proceed with submitting the replenishment request
        replenishmentRequestRepository.saveReplenishmentRequest(medicationName, quantity);
        System.out.println("Replenishment request submitted for '" + medicationName + "' with quantity " + quantity + ".");
    }


}
