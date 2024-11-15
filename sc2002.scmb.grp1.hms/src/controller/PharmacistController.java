package controller;


import java.io.IOException;
import entity.MedicationInventory;
import entity.AppointmentOutcome;
import repository.MedicationInventoryRepository;
import repository.AppointmentOutcomeRepository;
import repository.ReplenishmentRequestRepository;
import java.util.List;
import java.util.UUID;


public class PharmacistController {
    private MedicationInventoryRepository inventoryRepository;
    private AppointmentOutcomeRepository outcomeRepository;
    private ReplenishmentRequestRepository replenishmentRequestRepository;

    public PharmacistController(MedicationInventoryRepository inventoryRepository, AppointmentOutcomeRepository outcomeRepository, ReplenishmentRequestRepository replenishmentRequestRepository) {
        this.inventoryRepository = inventoryRepository;
        this.outcomeRepository = outcomeRepository;
        this.replenishmentRequestRepository = replenishmentRequestRepository;
    }

    public List<MedicationInventory> checkInventory(String medicationName) {
        try{
            return inventoryRepository.getMedicationByName(medicationName);
        } catch(IOException e) {
            System.err.println("Error finding the Medication. Medication does not exist");
            return null;
        }
    }
    public void submitReplenishmentRequest(String medicationName) throws IOException {
        if (!inventoryRepository.medicationExists(medicationName)) {
            System.out.println("Error: The medication '" + medicationName + "' does not exist in the inventory.");
            return;
        }
        replenishmentRequestRepository.saveReplenishmentRequest(medicationName);
        System.out.println("Replenishment request submitted for " + medicationName + ".");
    }

    public List<MedicationInventory> getAllInventory() throws IOException {
        return inventoryRepository.loadAllMedications();
    }
}
