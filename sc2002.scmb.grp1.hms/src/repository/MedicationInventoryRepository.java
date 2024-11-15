package repository;

import entity.MedicationInventory;
import util.CSVUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MedicationInventoryRepository {
    private static final String FILE_PATH_MEDICATION_INVENTORY = "/data/MedicationInventory.csv";
    private static final CSVUtil csvUtil = new CSVUtil();

    // Method to load all medications from the CSV file
    public List<MedicationInventory> loadAllMedications() throws IOException {
        List<MedicationInventory> medications = new ArrayList<>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(getClass().getResourceAsStream(FILE_PATH_MEDICATION_INVENTORY))));
            String line;

            // Skip the header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 3) {
                    String medicationName = fields[0];
                    int stockLevel = Integer.parseInt(fields[1]);
                    int stockAlertLevel = Integer.parseInt(fields[2]);

                    MedicationInventory medication = new MedicationInventory(medicationName, stockLevel, stockAlertLevel);
                    medications.add(medication);
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading medication inventory data: " + e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return medications;
    }

    // Method to filter medication inventory by medication name
    public List<MedicationInventory> getMedicationByName(String medicationName) throws IOException {
        List<MedicationInventory> allMedications = loadAllMedications();
        List<MedicationInventory> filteredMedications = new ArrayList<>();

        for (MedicationInventory medication : allMedications) {
            if (medication.getMedicationName().equalsIgnoreCase(medicationName)) {
                filteredMedications.add(medication);
            }
        }

        return filteredMedications;
    }
}

