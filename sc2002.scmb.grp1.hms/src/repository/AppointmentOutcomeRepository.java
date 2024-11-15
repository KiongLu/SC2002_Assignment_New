package repository;

import entity.AppointmentOutcome;
import util.CSVUtil;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppointmentOutcomeRepository {
    private static final String FILE_PATH_APPOINTMENT_OUTCOME = "sc2002.scmb.grp1.hms\\resource\\AppointmentOutcome.csv";
    private static final CSVUtil csvutil = new CSVUtil();

    public List<AppointmentOutcome> loadAllAppointmentOutcomes() throws IOException {
        List<AppointmentOutcome> appointmentOutcomes = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(FILE_PATH_APPOINTMENT_OUTCOME));
            String line;

            // Skip the header line if there's one
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // Assuming CSV format

                if (data.length == 7) {
                    String OutcomeId = data[0];
                    String appointmentId = data[1];
                    String date = data[2];
                    String serviceType = data[3];
                    String prescribedMedication = data[4];
                    String medicationStatus = data[5];
                    String consultationNotes = data[6];

                    // Create an AppointmentOutcome object
                    AppointmentOutcome appointmentOutcome = new AppointmentOutcome(OutcomeId, appointmentId,date,
                            serviceType, prescribedMedication, medicationStatus, consultationNotes);

                    // Add it to the list
                    appointmentOutcomes.add(appointmentOutcome);
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading appointment outcome data: " + e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return appointmentOutcomes;
    }



    public void createNewAppointmentOutcome(AppointmentOutcome appointmentOutcome) throws IOException {
        File file = new File(FILE_PATH_APPOINTMENT_OUTCOME);


        // Open the file in append mode
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            // If the file is not empty, write a newline first
            if (file.length() > 0) {
                writer.newLine();
            }

            // Format the appointment outcome data as CSV
            String appointmentOutcomeData = String.join(",",
                    appointmentOutcome.getOutcomeId(),
                    appointmentOutcome.getAppointmentId(),
                    appointmentOutcome.getDate(),
                    appointmentOutcome.getServiceType(),
                    appointmentOutcome.getPrescribedMedication(),
                    appointmentOutcome.getMedicationStatus(),
                    appointmentOutcome.getConsultationNotes()
            );


            // Write the new appointment outcome data to the file
            writer.write(appointmentOutcomeData);
            writer.flush();
        }

        // Clean up empty rows from the CSV file (if necessary)
        CSVUtil.removeEmptyRows(FILE_PATH_APPOINTMENT_OUTCOME);
    }

    public AppointmentOutcome getAppointmentOutcomeById(String appointmentOutcomeId) throws IOException {
        List<AppointmentOutcome> allAppointmentOutcomes = loadAllAppointmentOutcomes();

        for (AppointmentOutcome appointmentOutcome : allAppointmentOutcomes) {
            if (appointmentOutcome.getOutcomeId().equals(appointmentOutcomeId)) {
                return appointmentOutcome; // Return the appointment outcome that matches the appointmentOutcomeId
            }
        }

        return null; // If no match found, return null
    }

    public String getLastAppointmentOutcomeId() throws IOException {
        List<AppointmentOutcome> appointmentOutcomes = loadAllAppointmentOutcomes();
        if (appointmentOutcomes.isEmpty()) {
            return "AO000"; // Return a default ID if no appointment outcomes exist
        }
        String lastAvailableId = appointmentOutcomes.get(appointmentOutcomes.size() - 1).getOutcomeId();
        return lastAvailableId;
    }

    // Method to update an appointment outcome in the CSV file
    public void updateAppointmentOutcome(AppointmentOutcome updatedOutcome) throws IOException {
        List<AppointmentOutcome> allOutcomes = loadAllAppointmentOutcomes();
        boolean updated = false;

        // Update the appointment outcome
        for (int i = 0; i < allOutcomes.size(); i++) {
            AppointmentOutcome outcome = allOutcomes.get(i);
            if (outcome.getOutcomeId().equals(updatedOutcome.getOutcomeId())) {
                allOutcomes.set(i, updatedOutcome); // Replace with updated outcome
                updated = true;
                break;
            }
        }

        // If outcome was updated, write changes back to the CSV
        if (updated) {
            writeAppointmentOutcomesToFile(allOutcomes);
        }
    }

    // Method to write the list of appointment outcomes back to the CSV file
    private void writeAppointmentOutcomesToFile(List<AppointmentOutcome> allOutcomes) throws IOException {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(FILE_PATH_APPOINTMENT_OUTCOME));
            writer.write("OutcomeID,AppointmentID,Date,ServiceType,PrescribedMedication,MedicationStatus,ConsultationNotes\n");

            for (AppointmentOutcome outcome : allOutcomes) {
                writer.write(outcome.getOutcomeId() + "," +
                        outcome.getAppointmentId() + "," +
                        outcome.getDate() + "," +
                        outcome.getServiceType() + "," +
                        outcome.getPrescribedMedication() + "," +
                        outcome.getMedicationStatus() + "," +
                        outcome.getConsultationNotes() + "\n");
            }
        } catch (IOException e) {
            throw new IOException("Error writing appointment outcomes: " + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}

