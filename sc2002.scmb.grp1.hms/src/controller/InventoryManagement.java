package controller;

import entity.MedicationInventory;
import java.io.IOException;
import java.util.List;

public interface InventoryManagement {
    List<MedicationInventory> checkInventory(String medicationName) throws IOException;
    List<MedicationInventory> getAllInventory() throws IOException;
    List<MedicationInventory> lowStockAlert() throws IOException;
}
