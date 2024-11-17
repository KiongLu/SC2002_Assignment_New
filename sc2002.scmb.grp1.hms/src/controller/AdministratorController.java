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

    public void addStock(String medicine, int amount, int level) throws IOException {
        inventoryController.addMedicine(medicine, amount, level);
    }

    public void removeStock(String medicine) throws IOException {
        inventoryController.removeMedicine(medicine);
    }

    public void replenishStock(int requestID) throws IOException {
        ReplenishmentRequests request = requestRepository.getRequestById(requestID);
        inventoryController.replenishInventory(request.getMedicationName(), request.getQuantity());
        requestRepository.updateRequestStatus(requestID, "Completed");
    }

    public void changeAlert(String medicine, int amount) throws IOException {
        inventoryController.updateAlert(medicine, amount);
    }

    public List<MedicationInventory> viewInventory() throws IOException {
        return inventoryController.inventory();
    }

    public List<ReplenishmentRequests> viewRequests() throws IOException {
        return requestRepository.pendingRequests();
    }

    public List<Appointment> appointmentList() throws IOException {
        return appointmentController.viewAppointments();
    }

    public void displayStaffList(List<User> staffList) {
        if (staffList.isEmpty()) {
            System.out.println("No staff members found for the selected filter.");
            return;
        }
    
        // Define column widths for each field
        int idWidth = 6;
        int nameWidth = 10;
        int roleWidth = 15;
        int genderWidth = 8;
        int ageWidth = 4;
        int emailWidth = 25;
        int contactWidth = 12;
        int specializationWidth = 15;
    
        // Print table header
        System.out.printf("%-" + idWidth + "s %-"+ nameWidth +"s %-"+ roleWidth +"s %-"+ genderWidth +"s %-"+ ageWidth +"s %-"+ emailWidth +"s %-"+ contactWidth +"s %-"+ specializationWidth +"s%n",
                "ID", "Name", "Role", "Gender", "Age", "Email", "Contact", "Specialization");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    
        // Print each user's information in the table format
        for (User user : staffList) {
            String specialization = "-"; // Default for non-doctors
    
            // Check if the user is an Administrator, Doctor, or Pharmacist, and get appropriate fields
            if (user instanceof Administrator) {
                Administrator admin = (Administrator) user;
                System.out.printf("%-" + idWidth + "s %-"+ nameWidth +"s %-"+ roleWidth +"s %-"+ genderWidth +"s %-"+ ageWidth +"s %-"+ emailWidth +"s %-"+ contactWidth +"s %-"+ specializationWidth +"s%n",
                        admin.getUserId(), admin.getName(), admin.getRole(), admin.getGender(), admin.getAge(),
                        admin.getStaffEmail(), admin.getStaffContact(), specialization);
            } else if (user instanceof Doctor) {
                Doctor doctor = (Doctor) user;
                specialization = doctor.getSpecialization(); // Get specialization for doctors
                System.out.printf("%-" + idWidth + "s %-"+ nameWidth +"s %-"+ roleWidth +"s %-"+ genderWidth +"s %-"+ ageWidth +"s %-"+ emailWidth +"s %-"+ contactWidth +"s %-"+ specializationWidth +"s%n",
                        doctor.getUserId(), doctor.getName(), doctor.getRole(), doctor.getGender(), doctor.getAge(),
                        doctor.getStaffEmail(), doctor.getStaffContact(), specialization);
            } else if (user instanceof Pharmacist) {
                Pharmacist pharmacist = (Pharmacist) user;
                System.out.printf("%-" + idWidth + "s %-"+ nameWidth +"s %-"+ roleWidth +"s %-"+ genderWidth +"s %-"+ ageWidth +"s %-"+ emailWidth +"s %-"+ contactWidth +"s %-"+ specializationWidth +"s%n",
                        pharmacist.getUserId(), pharmacist.getName(), pharmacist.getRole(), pharmacist.getGender(), pharmacist.getAge(),
                        pharmacist.getStaffEmail(), pharmacist.getStaffContact(), specialization);
            }
        }
    }
    

    public List<User> viewStaff(String filter) throws IOException {
        List<Administrator> administrators = administratorRepository.loadAdministrators();
        List<Doctor> doctors = doctorRepository.loadDoctors();
        List<Pharmacist> pharmacists = pharmacistRepository.loadPharmacists();

        List<User> combined = new ArrayList<>();
        List<User> filtered = new ArrayList<>();

        combined.addAll(administrators);
        combined.addAll(doctors);
        combined.addAll(pharmacists);

        switch (filter) {
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
            case "20":
                for (User user : combined) {
                    String ageString = user.getAge().trim(); // Remove any leading/trailing whitespace

                    // Check if age is numeric before parsing
                    if (ageString.matches("\\d+")) { // Ensures the age string is fully numeric
                        int age = Integer.parseInt(ageString);
                        if (age >= 20 && age < 30) {
                            filtered.add(user);
                        }
                    }
                    // If age is not numeric, skip to the next user
                }
                break;

            case "30":
                for (User user : combined) {
                    String ageString = user.getAge().trim();

                    if (ageString.matches("\\d+")) {
                        int age = Integer.parseInt(ageString);
                        if (age >= 30 && age < 40) {
                            filtered.add(user);
                        }
                    }
                    // Skip if age is not numeric
                }
                break;

            case "40":
                for (User user : combined) {
                    String ageString = user.getAge().trim();

                    if (ageString.matches("\\d+")) {
                        int age = Integer.parseInt(ageString);
                        if (age >= 40 && age <= 50) {
                            filtered.add(user);
                        }
                    }
                    // Skip if age is not numeric
                }
                break;
            default:
                System.err.println("Unrecognized filter: " + filter + ". Returning all staff.");
                return combined; // Default to returning the full list
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
            String staffcontact) throws IOException {
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

    public void removeAdmin(String userID) throws IOException {
        administratorRepository.removeAdministratorById(userID);
    }

    public void addDoctor(String userid,
            String name,
            String role,
            String password,
            String gender,
            String age,
            String specialization,
            String staffemail,
            String staffcontact) throws IOException {

        Doctor newDoctor = new Doctor(userid,
                name,
                role,
                password,
                gender,
                age,
                specialization,
                staffemail,
                staffcontact);
        doctorRepository.writeDoctor(newDoctor);
        return;
    }

    public void removeDoctor(String userID) throws IOException {
        doctorRepository.removeDoctorById(userID);
        return;
    }

    public void addPharmacist(String userid,
            String name,
            String role,
            String password,
            String gender,
            String age,
            String staffemail,
            String staffcontact) throws IOException {
        Pharmacist newPharmacist = new Pharmacist(userid,
                name,
                role,
                password,
                gender,
                age,
                staffemail,
                staffcontact);
        pharmacistRepository.writePharmacist(newPharmacist);
        return;
    }

    public void removePharmacist(String userID) throws IOException {
        pharmacistRepository.removePharmacistById(userID);
        return;
    }
}
