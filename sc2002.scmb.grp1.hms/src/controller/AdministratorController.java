package controller;

import java.io.IOException;
import java.util.*;

import entity.Administrator;
import repository.AdministratorRepository;
import controller.MedicationInventoryController;


public class AdministratorController {
    private MedicationInventoryController inventoryController = new MedicationInventoryController();

    public void replenishStock(String medicine, int amount) throws IOException
    {
        inventoryController.replenishInventory(medicine, amount);
    }

    public void viewInventory() throws IOException
    {
        inventoryController.viewStock();
    }

    
}
