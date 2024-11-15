package controller;

import java.io.IOException;
import java.util.*;

import entity.Administrator;
import repository.AdministratorRepository;
import controller.MedicationInventoryController;


public class AdministratorController {
	private AdministratorRepository administratorRepository = new AdministratorRepository();

    public AdministratorController(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public void replenishStock(String medicine, int amount) throws IOException
    {
        MedicationInventoryController inventoryController = new MedicationInventoryController();
        inventoryController.replenishInventory(medicine, amount);
    }

    
}
