package controller;

import entity.Appointment;
import entity.AppointmentOutcome;
import entity.Availability;
import entity.MedicationInventory;
import repository.AppointmentOutcomeRepository;
import repository.AppointmentRepository;
import repository.MedicationInventoryRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppointmentOutcomeController implements AppointmentOutcomeService {
    private AppointmentOutcomeRepository outcomeRepository = new AppointmentOutcomeRepository();
    private AppointmentRepository appointmentRepository = new AppointmentRepository();
    private MedicationInventoryRepository medicationInventoryRepository = new MedicationInventoryRepository();
    Scanner scanner = new Scanner(System.in);

    public void createAppointmentOutcome(String ApptID) throws IOException {
        Appointment appt = appointmentRepository.getAppointmentById(ApptID);

        String outcomeID = generateNextAppointmentOutcomeId();
        String date = appt.getAppointmentDate();

        System.out.print("Please enter Service type: ");
        String servicetype = scanner.nextLine();

        StringBuilder medicationNamesBuilder = new StringBuilder();
        String medicationName;
        String exactMedicationName;

        System.out.println(
                "Please enter Medication names one by one (type 'done' when finished or 'nil' if no medication is needed): ");
        while (true) {
            System.out.print("Medication name: ");
            medicationName = scanner.nextLine();

            if ("nil".equalsIgnoreCase(medicationName)) {
                medicationNamesBuilder.setLength(0); // Clear any entered medications
                medicationNamesBuilder.append("nil");
                break;
            } else if ("done".equalsIgnoreCase(medicationName)) {
                break;
            }

            exactMedicationName = isValidMedication(medicationName);
            if (exactMedicationName == null) {
                System.out.println("Invalid medication name. Please enter again.");
            } else {
                if (medicationNamesBuilder.length() > 0) {
                    medicationNamesBuilder.append(", ");
                }
                medicationNamesBuilder.append(exactMedicationName);
            }
        }

        String medicationNames = "\"" + medicationNamesBuilder.toString() + "\"";
        String medicationStatus = "nil".equalsIgnoreCase(medicationNames) ? "nil" : "Pending";

        System.out.print("Please enter Consultation Notes: ");
        String consultationNotes = scanner.nextLine();

        AppointmentOutcome newOutcome = new AppointmentOutcome(outcomeID, ApptID, date, servicetype, medicationNames,
                medicationStatus, consultationNotes);

        outcomeRepository.createNewAppointmentOutcome(newOutcome);

        System.out.println("Appointment Outcome has been created.");
    }

    @Override
    // Generate next AppointmentOutcome ID
    public String generateNextAppointmentOutcomeId() throws IOException {
        String lastOutcomeId = outcomeRepository.getLastAppointmentOutcomeId();
        if (lastOutcomeId.equals("AO000")) {
            return "AO000";
        }
        String numberPart = lastOutcomeId.substring(2);
        int nextNumber = Integer.parseInt(numberPart) + 1;
        return "AO" + String.format("%03d", nextNumber);
    }

    @Override
    public void getAllAppointmentOutcomesForPatient(String patientId) throws IOException {
        List<AppointmentOutcome> patientOutcomes = new ArrayList<>();
        List<AppointmentOutcome> allAppointmentOutcomes = outcomeRepository.loadAllAppointmentOutcomes();

        // Check if there are no appointment outcomes
        if (allAppointmentOutcomes == null || allAppointmentOutcomes.isEmpty()) {
            System.out.println("No appointment outcomes found.");
            return;
        }

        boolean found = false;

        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|                                               Appointment Outcomes for Patient                                                     |");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-10s | %-15s | %-12s | %-15s | %-30s | %-10s | %-20s |\n",
                "Outcome ID", "Appointment ID", "Date", "Service Type", "Prescribed Medication", "Med Status",
                "Consultation Notes");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------+");

        // Loop through all appointment outcomes
        for (AppointmentOutcome outcome : allAppointmentOutcomes) {
            String appointmentId = outcome.getAppointmentId();

            // Retrieve the appointment associated with this outcome
            Appointment appointment = appointmentRepository.getAppointmentById(appointmentId);

            // Check if appointment is valid and if patientId matches
            if (appointment != null && appointment.getPatientId().equals(patientId)) {
                // If the patientId matches, add this outcome to the patient's list
                patientOutcomes.add(outcome);
                found = true;

                // Display the outcome details
                System.out.printf("| %-10s | %-15s | %-12s | %-15s | %-30s | %-10s | %-20s |\n",
                outcome.getOutcomeId(),
                outcome.getAppointmentId(),
                outcome.getDate(),
                outcome.getServiceType(),
                outcome.getPrescribedMedication(),
                outcome.getMedicationStatus(),
                outcome.getConsultationNotes());
            }
        }
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------+");

        // If no outcomes are found for the patient, print a message
        if (!found) {
            System.out.println("No appointment outcomes found for patient ID: " + patientId);
        }
    }

    @Override
    // get all appointmentoutcome
    public void viewAppointmentOutcomes() throws IOException {
        List<AppointmentOutcome> allAppointmentOutcomes = outcomeRepository.loadAllAppointmentOutcomes();

        if (allAppointmentOutcomes.isEmpty()) {
            System.out.println("No appointment outcomes found.");
            return;
        }

        System.out.println("Appointment Outcomes for Pharmacist to Process:");

        for (AppointmentOutcome outcome : allAppointmentOutcomes) {
            System.out.println("Outcome ID: " + outcome.getOutcomeId());
            System.out.println("Appointment ID: " + outcome.getAppointmentId());
            System.out.println("Date: " + outcome.getDate());
            System.out.println("Prescribed Medication: " + outcome.getPrescribedMedication());
            System.out.println("Medication Status: " + outcome.getMedicationStatus());
            System.out.println("Consultation Notes: " + outcome.getConsultationNotes());
            System.out.println();
        }
    }

    @Override
    // Method to display all Appointment Outcomes with a "Pending" medication status
    public void displayPendingAppointmentOutcomes() throws IOException {
        List<AppointmentOutcome> allOutcomes = outcomeRepository.loadAllAppointmentOutcomes();

        // Check if there are any pending outcomes
        boolean hasPendingOutcomes = false;

        // Loop through all outcomes and check for "Pending" medication status
        for (AppointmentOutcome outcome : allOutcomes) {
            if ("Pending".equalsIgnoreCase(outcome.getMedicationStatus())) {
                hasPendingOutcomes = true;
                // Display outcome details
                System.out.println("Outcome ID: " + outcome.getOutcomeId());
                System.out.println("Appointment ID: " + outcome.getAppointmentId());
                System.out.println("Date: " + outcome.getDate());
                System.out.println("Service Type: " + outcome.getServiceType());
                System.out.println("Prescribed Medication: " + outcome.getPrescribedMedication());
                System.out.println("Medication Status: " + outcome.getMedicationStatus());
                System.out.println("Consultation Notes: " + outcome.getConsultationNotes());
                System.out.println();
            }
        }

        if (!hasPendingOutcomes) {
            System.out.println("No pending appointment outcomes found.");
        }
    }

    @Override
    public void updatePrescriptionStatus(String outcomeId) throws IOException {
        // Retrieve the appointment outcome by ID
        AppointmentOutcome outcome = outcomeRepository.getAppointmentOutcomeById(outcomeId);

        if (outcome == null) {
            System.out.println("No appointment outcome found with ID: " + outcomeId);
            return;
        }

        outcome.setMedicationStatus("Dispensed");

        outcomeRepository.updateAppointmentOutcome(outcome);
    }

    @Override
    public void changePrescriptionStatusToDispensed(String outcomeId) throws IOException {
        List<AppointmentOutcome> allOutcomes = outcomeRepository.loadAllAppointmentOutcomes();

        // Validate and update the outcome status
        AppointmentOutcome selectedOutcome = null;
        for (AppointmentOutcome outcome : allOutcomes) {
            if (outcome.getOutcomeId().equalsIgnoreCase(outcomeId)) {
                selectedOutcome = outcome;
                break;
            }
        }

        // If the Outcome ID is valid, update its status to "Dispensed"
        if (selectedOutcome != null && "Pending".equalsIgnoreCase(selectedOutcome.getMedicationStatus())) {
            selectedOutcome.setMedicationStatus("Dispensed");
            outcomeRepository.updateAppointmentOutcome(selectedOutcome);
            System.out.println(
                    "Prescription status updated to 'Dispensed' for Outcome ID: " + selectedOutcome.getOutcomeId());
        } else {
            System.out.println("Invalid Outcome ID or the prescription is already dispensed.");
        }
    }

    public String isValidMedication(String medicationName) throws IOException {
        List<MedicationInventory> allMedications = medicationInventoryRepository.loadAllMedications();

        for (MedicationInventory medication : allMedications) {
            if (medication.getMedicationName().equalsIgnoreCase(medicationName)) {
                // Return the exact name from the inventory
                return medication.getMedicationName();
            }
        }
        return null;
    }
}
