package controller;

import java.io.IOException;
import java.util.*;

import entity.User;
import entity.Administrator;
import entity.Pharmacist;
import entity.Doctor;
import entity.MedicationInventory;
import entity.Patient;
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

    public void replenishStock(String medicine, int amount) throws IOException {
        inventoryController.replenishInventory(medicine, amount);
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

    public void removeAdmin() throws IOException {

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

    }

    public void removeDoctor() throws IOException {

    }

    public void addPharmacist(String userid,
            String name,
            String Role,
            String password,
            String gender,
            String age,
            String staffemail,
            String staffcontact) throws IOException {

    }

    public void removePharmacist() throws IOException {

    }

    public void updateStaffInfo(String role, String staffId) throws IOException {
        User staff = null;

        // Retrieve the appropriate user based on their role
        if (role.equalsIgnoreCase("Admin")) {
            staff = administratorRepository.findAdminById(staffId);
        } else if (role.equalsIgnoreCase("Doctor")) {
            staff = doctorRepository.findDoctorById(staffId);
        } else if (role.equalsIgnoreCase("Pharmacist")) {
            staff = pharmacistRepository.findPharmacistById(staffId);
        }

        if (staff == null) {
            System.out.println("Staff member not found!");
            return;
        }

        // Display current contact information based on the type of user
        if (staff instanceof Administrator) {
            System.out.println("Current Email: " + ((Administrator) staff).getStaffEmail());
            System.out.println("Current Phone Number: " + ((Administrator) staff).getStaffContact());
        } else if (staff instanceof Doctor) {
            System.out.println("Current Email: " + ((Doctor) staff).getStaffEmail());
            System.out.println("Current Phone Number: " + ((Doctor) staff).getStaffContact());
        } else if (staff instanceof Pharmacist) {
            System.out.println("Current Email: " + ((Pharmacist) staff).getStaffEmail());
            System.out.println("Current Phone Number: " + ((Pharmacist) staff).getStaffContact());
        }

        System.out.print("Enter new email (leave blank to keep current): ");
        String newEmail = scanner.nextLine();

        if (!newEmail.isEmpty()) {
            if (staff instanceof Administrator) {
                ((Administrator) staff).setStaffEmail(newEmail);
            } else if (staff instanceof Doctor) {
                ((Doctor) staff).setStaffEmail(newEmail);
            } else if (staff instanceof Pharmacist) {
                ((Pharmacist) staff).setStaffEmail(newEmail);
            }
        }

        System.out.print("Enter new phone number (leave blank to keep current): ");
        String newPhoneNumber = scanner.nextLine();

        if (!newPhoneNumber.isEmpty()) {
            if (staff instanceof Administrator) {
                ((Administrator) staff).setStaffContact(newPhoneNumber);
            } else if (staff instanceof Doctor) {
                ((Doctor) staff).setStaffContact(newPhoneNumber);
            } else if (staff instanceof Pharmacist) {
                ((Pharmacist) staff).setStaffContact(newPhoneNumber);
            }
        }

        // Save the updated information
        boolean success = false;
        if (role.equalsIgnoreCase("Admin")) {
            success = administratorRepository.updateAdministrator((Administrator) staff);
        } else if (role.equalsIgnoreCase("Doctor")) {
            success = doctorRepository.updateDoctor((Doctor) staff);
        } else if (role.equalsIgnoreCase("Pharmacist")) {
            success = pharmacistRepository.updatePharmacist((Pharmacist) staff);
        }

        if (success) {
            System.out.println("Contact information updated successfully.");
        } else {
            System.out.println("Failed to update contact information.");
        }
    }

}
