package controller;


import java.io.IOException;
import entity.MedicationInventory;
import entity.AppointmentOutcome;
import repository.MedicationInventoryRepository;
import repository.AppointmentOutcomeRepository;
import java.util.List;
import java.util.UUID;


public class PharmacistController {
    private MedicationInventoryRepository inventoryRepository;
    private AppointmentOutcomeRepository outcomeRepository;

    public PharmacistController(MedicationInventoryRepository inventoryRepository, AppointmentOutcomeRepository outcomeRepository) {
        this.inventoryRepository = inventoryRepository;
        this.outcomeRepository = outcomeRepository;
    }

//    public void fulfillPrescription(String outcomeId) {
//        AppointmentOutcome outcome = outcomeRepository.getOutcomeById(outcomeId);
//        if (outcome != null && "Pending".equals(outcome.getMedicationStatus())) {
//            outcome.setMedicationStatus("Dispensed");
//            outcomeRepository.updateOutcome(outcome);
//        }
//    }

//    public List<MedicationInventory> checkInventory(String medicationName) {
//        return inventoryRepository.getMedicationByName(medicationName);
//    }
public void submitReplenishmentRequest(String medicationName) throws IOException {
    // Check if the medication exists in the inventory
    if (!inventoryRepository.medicationExists(medicationName)) {
        System.out.println("Error: The medication '" + medicationName + "' does not exist in the inventory.");
        return; // Exit without submitting the request
    }

    // Generate a unique requestId and set status to "pending"
    String requestId = UUID.randomUUID().toString();
    String status = "pending";

    // Save the valid replenishment request
    inventoryRepository.saveReplenishmentRequest(medicationName, requestId, status);
    System.out.println("Replenishment request submitted for " + medicationName + ".");
}

    public List<MedicationInventory> getAllInventory() throws IOException {
        return inventoryRepository.loadAllMedications();
    }
}
