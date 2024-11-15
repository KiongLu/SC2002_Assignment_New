package controller;


import java.io.IOException;
import entity.MedicationInventory;
import entity.AppointmentOutcome;
import repository.MedicationInventoryRepository;
import repository.AppointmentOutcomeRepository;
import repository.ReplenishmentRequestRepository;
import java.util.List;
import java.util.UUID;


public class PharmacistController implements InventoryManagement,ReplenishmentRequestService {
    private MedicationInventoryRepository inventoryRepository;
    private AppointmentOutcomeRepository outcomeRepository;
    private ReplenishmentRequestRepository replenishmentRequestRepository;

    public PharmacistController(MedicationInventoryRepository inventoryRepository, AppointmentOutcomeRepository outcomeRepository, ReplenishmentRequestRepository replenishmentRequestRepository) {
        this.inventoryRepository = inventoryRepository;
        this.outcomeRepository = outcomeRepository;
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

        // If the medication exists, proceed with submitting the replenishment request
        replenishmentRequestRepository.saveReplenishmentRequest(medicationName);
        System.out.println("Replenishment request submitted for " + medicationName + ".");
    }


}
