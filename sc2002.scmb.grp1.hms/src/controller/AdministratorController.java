package controller;

import java.io.IOException;
import java.util.*;
import java.util.Scanner;

import entity.Administrator;
import entity.MedicationInventory;
import entity.ReplenishmentRequests;
import repository.AdministratorRepository;
import controller.MedicationInventoryController;
import controller.AppointmentController;
import repository.ReplenishmentRequestRepository;

@SuppressWarnings("unused")
public class AdministratorController {
    private final Scanner scanner = new Scanner(System.in);
    private ReplenishmentRequestRepository requestRepository = new ReplenishmentRequestRepository();
    private MedicationInventoryController inventoryController = new MedicationInventoryController();
    private AppointmentController appointmentController = new AppointmentController();

    public void addStock(String medicine, int amount, int level) throws IOException
    {
        inventoryController.addMedicine(medicine, amount, level);
    }

    public void removeStock(String medicine) throws IOException
    {
        inventoryController.removeMedicine(medicine);
    }

    public void replenishStock(String medicine, int amount) throws IOException
    {
        inventoryController.replenishInventory(medicine, amount);
    }

    public void changeAlert(String medicine, int amount) throws IOException
    {
        inventoryController.updateAlert(medicine, amount);
    }

    public List<MedicationInventory> viewInventory() throws IOException
    {
        return inventoryController.inventory();
    }

    public List<ReplenishmentRequests> viewRequests() throws IOException
    {
        return requestRepository.pendingRequests();
    }
}
