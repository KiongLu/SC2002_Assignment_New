package controller;

import java.io.IOException;
import java.util.*;

import entity.User;
import entity.Administrator;
import entity.Pharmacist;
import entity.Doctor;
import entity.MedicationInventory;
import entity.Appointment;
import entity.ReplenishmentRequests;
import controller.MedicationInventoryController;
import controller.AppointmentController;
import repository.AdministratorRepository;
import repository.ReplenishmentRequestRepository;
import repository.DoctorRepository;
import repository.PharmacistRepository;

@SuppressWarnings("unused")
public class AdministratorController {
    private final Scanner scanner = new Scanner(System.in);
    private AdministratorRepository administratorRepository = new AdministratorRepository();
    private DoctorRepository doctorRepository = new DoctorRepository();
    private PharmacistRepository pharmacistRepository = new PharmacistRepository();
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

    public List<Appointment> appointmentList() throws IOException
    {
        return appointmentController.viewAppointments();
    }

    public List<User> viewStaff(String filter) throws IOException
    {
        List<Administrator> administrators = administratorRepository.loadAdministrators();
        List<Doctor> doctors = doctorRepository.loadDoctors();
        List<Pharmacist> pharmacists = pharmacistRepository.loadPharmacists();

        List<User> combined = new ArrayList<>();
        List<User> filtered = new ArrayList<>();

        combined.addAll(administrators);
        combined.addAll(doctors);
        combined.addAll(pharmacists);

        switch(filter)
        {
            case "All":
                return combined;
            case "Doctor":
                for (User user : combined) {
                if (user instanceof Doctor) {
                    filtered.add(user);
                    }
                }
                break;
            case "Pharmacist":
                for (User user : combined) {
                    if (user instanceof Pharmacist) {
                        filtered.add(user);
                        }
                    }
                break;
            case "Admin":
                for (User user : combined) {
                    if (user instanceof Administrator) {
                        filtered.add(user);
                        }
                    }
                break;
            case "Male":
                for (User user : combined) {
                    if ("Male".equalsIgnoreCase(user.getGender())) {
                        filtered.add(user);
                    }
                }
                break;
            case "Female":
                for (User user : combined) {
                    if ("Female".equalsIgnoreCase(user.getGender())) {
                        filtered.add(user);
                    }
                }
                break;
            case "20 - 30":
                for (User user : combined) {
                    try {
                        int age = Integer.parseInt(user.getAge());
                        if (age >= 20 && age <= 30) {
                            filtered.add(user);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid age format for user: " + user);
                    }
                }
                break;
            case "30 - 40":
                for (User user : combined) {
                    try {
                        int age = Integer.parseInt(user.getAge());
                        if (age >= 30 && age <= 40) {
                            filtered.add(user);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid age format for user: " + user);
                    }
                }
                break;
            case "40 - 50":
                for (User user : combined) {
                    try {
                        int age = Integer.parseInt(user.getAge());
                        if (age >= 40 && age <= 50) {
                            filtered.add(user);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid age format for user: " + user);
                    }
                }
                break;
            default:
                System.err.println("Unrecognized filter: " + filter + ". Returning all staff.");
                return combined;  // Default to returning the full list
        }

        return filtered;

    }

    public void addAdmin(String userid, 
    String name, 
    String role, 
    String password, 
    String gender, 
    String age, 
    String staffemail, 
    String staffcontact) throws IOException
    {
        Administrator newAdmin = new Administrator(userid, 
        name, 
        role, 
        password, 
        gender, 
        age, 
        staffemail, 
        staffcontact);

        administratorRepository.writeAdmin(newAdmin);
    }

    public void removeAdmin() throws IOException
    {

    }

    public void addDoctor(String userid, 
    String name, 
    String role, 
    String password, 
    String gender, 
    String age, 
    String specialization, 
    String staffemail, 
    String staffcontact) throws IOException
    {

    }

    public void removeDoctor() throws IOException
    {
        
    }

    public void addPharmacist(String userid, 
    String name, 
    String Role, 
    String password, 
    String gender, 
    String age, 
    String staffemail,
    String staffcontact) throws IOException
    {

    }

    public void removePharmacist() throws IOException
    {
        
    }
}
