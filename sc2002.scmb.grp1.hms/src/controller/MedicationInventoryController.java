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

    public void addMedicine(String medicineName, int stockQuantity, int stockLevel) throws IOException
    {
        medicationinventoryRepository.addMedication(medicineName, stockLevel, stockLevel);
    }

    public void removeMedicine(String medicineName) throws IOException
    {
        medicationinventoryRepository.removeMedication(medicineName);
    }

    //Method to update stock level
    public void replenishInventory(String medication, int quantity) throws IOException
    {
        medicationinventoryRepository.updateStockLevel(medication, quantity);
    }

    public void updateAlert(String medication, int level) throws IOException
    {
        medicationinventoryRepository.updateStockAlert(medication, level);
    }

    public List<MedicationInventory> inventory() throws IOException
    {
        return medicationinventoryRepository.loadAllMedications();
    }

}
