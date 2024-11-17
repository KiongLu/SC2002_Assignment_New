package controller;


import java.io.IOException;


public interface AppointmentOutcomeService {

    void createAppointmentOutcome(String appointmentId) throws IOException;


    void viewAppointmentOutcomes() throws IOException;


    void getAllAppointmentOutcomesForPatient(String patientId) throws IOException;


    void displayPendingAppointmentOutcomes() throws IOException;


    void changePrescriptionStatusToDispensed(String outcomeId) throws IOException;

    String isValidMedication(String medicationName) throws IOException;

    String generateNextAppointmentOutcomeId() throws IOException;
}
