package controller;

import entity.AppointmentOutcome;
import java.io.IOException;
import java.util.List;

public interface AppointmentOutcomeService {

    void createAppointmentOutcome(String appointmentId) throws IOException;


    void viewAppointmentOutcomes() throws IOException;


    void getAllAppointmentOutcomesForPatient(String patientId) throws IOException;


    void displayPendingAppointmentOutcomes() throws IOException;


    void updatePrescriptionStatus(String outcomeId) throws IOException;


    void changePrescriptionStatusToDispensed(String outcomeId) throws IOException;

    String isValidMedication(String medicationName) throws IOException;

    String generateNextAppointmentOutcomeId() throws IOException;
}
