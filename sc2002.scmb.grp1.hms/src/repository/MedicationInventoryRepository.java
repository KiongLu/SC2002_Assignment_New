package repository;

import entity.MedicationInventory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import util.CSVUtil;

public class MedicationInventoryRepository {
    private static final String FILE_PATH_MEDICATION_INVENTORY = "sc2002.scmb.grp1.hms//resource//MedicationInventory.csv";
    private static final String FILE_PATH_REPLENISHMENT_REQUESTS = "sc2002.scmb.grp1.hms//resource//ReplenishmentRequests.csv";
    private static final CSVUtil csvUtil = new CSVUtil();

    // Method to load all medications from the CSV file
    public List<MedicationInventory> loadAllMedications() throws IOException {
        List<MedicationInventory> medications = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_MEDICATION_INVENTORY))) {
            String line;
            reader.readLine(); // Skip the header row
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 3) {
                    String medicationName = fields[0];
                    int stockLevel = Integer.parseInt(fields[1]);
                    int stockAlertLevel = Integer.parseInt(fields[2]);

                    medications.add(new MedicationInventory(medicationName, stockLevel, stockAlertLevel));
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading medication inventory data: " + e.getMessage());
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

    public void viewInventory() throws IOException
    {
        List<MedicationInventory> medications = loadAllMedications();

        for (MedicationInventory medication : medications)
        {
            System.out.println("Medication Name: " + medication.getMedicationName() + 
                       ", Stock Level: " + medication.getStockLevel());
        }
    }

    public void updateStockLevel(String name, int level) throws IOException
    {
        List<MedicationInventory> medications = loadAllMedications();

        for (MedicationInventory medication : medications)
        {
            if(medication.getMedicationName().equalsIgnoreCase(name))
            {
                medication.setStockLevel(level);
                break;
            }
        }
        saveAllMedication(medications);
    }

    //Update CSV file
    private void saveAllMedication(List<MedicationInventory> medications) throws IOException
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_MEDICATION_INVENTORY)))
        {
            // Write the header line
            writer.write("MedicationName,StockLevel,StockAlertLevel\n");

            // Write each medication as a row in the CSV
            for (MedicationInventory medication : medications)
            {
                writer.write(medication.getMedicationName() + ","
                        + medication.getStockLevel() + ","
                        + medication.getStockAlertLevel() + "\n");
            }
        }
    }

    public void saveReplenishmentRequest(String medicationName, String requestId, String status) {
        try (FileWriter writer = new FileWriter(FILE_PATH_REPLENISHMENT_REQUESTS, true)) {
            // Append the new request as a row in the CSV file
            writer.append(medicationName).append(",");
            writer.append(requestId).append(",");
            writer.append(status).append("\n");
            System.out.println("Replenishment request saved to file.");
        } catch (IOException e) {
            System.err.println("Error writing replenishment request to file: " + e.getMessage());
        }
    }

    public boolean medicationExists(String medicationName) throws IOException {
        List<MedicationInventory> medications = loadAllMedications();
        // Check if any medication in the list matches the provided name (case-insensitive)
        for (MedicationInventory medication : medications) {
            if (medication.getMedicationName().equalsIgnoreCase(medicationName)) {
                return true;
            }
        }
        return false;
    }


}

