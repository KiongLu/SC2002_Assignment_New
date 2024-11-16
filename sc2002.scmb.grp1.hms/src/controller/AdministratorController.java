package controller;

import java.io.IOException;
import java.util.*;
import java.util.Scanner;

import entity.Administrator;
import repository.AdministratorRepository;
import controller.MedicationInventoryController;
import controller.AppointmentController;


public class AdministratorController {
    private final Scanner scanner = new Scanner(System.in);
    private MedicationInventoryController inventoryController = new MedicationInventoryController();
    private AppointmentController appointmentController = new AppointmentController();

    public void replenishStock(String medicine, int amount) throws IOException
    {
        inventoryController.replenishInventory(medicine, amount);
    }

    public void viewAppointment() throws IOException
    {
        
    }

    
}
