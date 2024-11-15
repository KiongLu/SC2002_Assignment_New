package controller;

import entity.MedicationInventory;
import java.io.IOException;
import java.util.List;

public interface ReplenishmentRequestService {
    void submitReplenishmentRequest(String medicationName) throws IOException;
    List<MedicationInventory> checkInventory(String medicationName) throws IOException;
}