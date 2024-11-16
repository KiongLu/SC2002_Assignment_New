package controller;

import entity.MedicationInventory;
import repository.MedicationInventoryRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedicationInventoryController {
    private MedicationInventoryRepository medicationinventoryRepository = new MedicationInventoryRepository();

    // Method to get all unique medication names
    public List<String> getAllMedicationNames() throws IOException {
        List<MedicationInventory> medications = medicationinventoryRepository.loadAllMedications();
        List<String> medicationNames = new ArrayList<>();

        for (MedicationInventory medication : medications) {
            medicationNames.add(medication.getMedicationName());
        }

        return medicationNames;
    }

    //Method to view all inventory
    public void viewStock() throws IOException
    {
        medicationinventoryRepository.viewInventory();
    }

    //Method to update stock level
    public void replenishInventory(String medication, int quantity) throws IOException
    {
        MedicationInventoryRepository inventoryRepo = new MedicationInventoryRepository();
        inventoryRepo.updateStockLevel(medication, quantity);
    }

}
