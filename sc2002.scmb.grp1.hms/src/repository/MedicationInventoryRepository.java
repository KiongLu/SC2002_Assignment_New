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
        System.out.println("+------------------------------------------------+");
        System.out.println("|           Loading Medication Inventory         |");
        System.out.println("+------------------------------------------------+");

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
            System.out.println("|   Inventory loaded successfully.               |");
        } catch (IOException e) {
            System.out.printf("| Error: %-40s |\n", e.getMessage());
            System.out.println("+------------------------------------------------+");
            throw new IOException("Error reading medication inventory data: " + e.getMessage());
        }
        System.out.println("+------------------------------------------------+\n");
        return medications;
    }

    // Method to filter medication inventory by medication name
    public List<MedicationInventory> getMedicationByName(String medicationName) throws IOException {
        System.out.println("+------------------------------------------------+");
        System.out.println("|            Searching Medication by Name        |");
        System.out.println("+------------------------------------------------+");

        List<MedicationInventory> allMedications = loadAllMedications();
        List<MedicationInventory> filteredMedications = new ArrayList<>();

        for (MedicationInventory medication : allMedications) {
            if (medication.getMedicationName().equalsIgnoreCase(medicationName)) {
                filteredMedications.add(medication);
            }
        }

        if (filteredMedications.isEmpty()) {
            System.out.println("| Error: Medication not found in the inventory.  |");
        } else {
            System.out.println("| Medication found:                              |");
            for (MedicationInventory medication : filteredMedications) {
                System.out.printf("| Name: %-35s | Stock Level: %3d |\n",
                        medication.getMedicationName(), medication.getStockLevel());
            }
        }
        System.out.println("+------------------------------------------------+\n");
        return filteredMedications;
    }

    public void addMedication(String name, int stockLevel, int alertLevel) throws IOException {
        System.out.println("+------------------------------------------------+");
        System.out.println("|               Adding New Medication            |");
        System.out.println("+------------------------------------------------+");

        MedicationInventory newMedicine = new MedicationInventory(name, stockLevel, alertLevel);
        try (FileWriter writer = new FileWriter(FILE_PATH_MEDICATION_INVENTORY, true)) {
            writer.append(newMedicine.getMedicationName()).append(",");
            writer.append(String.valueOf(newMedicine.getStockLevel())).append(",");
            writer.append(String.valueOf(newMedicine.getStockAlertLevel())).append("\n");
            System.out.println("| New medication added successfully!             |");
        } catch (IOException e) {
            System.out.printf("| Error: %-40s |\n", e.getMessage());
            throw new IOException("Error writing new medication to file: " + e.getMessage());
        }
        System.out.println("+------------------------------------------------+\n");
    }

    public void removeMedication(String name) throws IOException {
        System.out.println("+------------------------------------------------+");
        System.out.println("|               Removing Medication              |");
        System.out.println("+------------------------------------------------+");

        List<MedicationInventory> medications = loadAllMedications();
        medications.removeIf(medication -> medication.getMedicationName().equalsIgnoreCase(name));

        saveAllMedication(medications);
        System.out.println("| Medication removed successfully!               |");
        System.out.println("+------------------------------------------------+\n");
    }

    public void updateStockLevel(String name, int level) throws IOException {
        System.out.println("+------------------------------------------------+");
        System.out.println("|             Updating Stock Level               |");
        System.out.println("+------------------------------------------------+");

        List<MedicationInventory> medications = loadAllMedications();
        for (MedicationInventory medication : medications) {
            if (medication.getMedicationName().equalsIgnoreCase(name)) {
                medication.setStockLevel(level);
                break;
            }
        }
        saveAllMedication(medications);
        System.out.println("| Stock level updated successfully!              |");
        System.out.println("+------------------------------------------------+\n");
    }

    public void updateStockAlert(String name, int level) throws IOException {
        System.out.println("+------------------------------------------------+");
        System.out.println("|           Updating Stock Alert Level           |");
        System.out.println("+------------------------------------------------+");

        List<MedicationInventory> medications = loadAllMedications();
        for (MedicationInventory medication : medications) {
            if (medication.getMedicationName().equalsIgnoreCase(name)) {
                medication.setStockAlertLevel(level);
                break;
            }
        }
        saveAllMedication(medications);
        System.out.println("| Stock alert level updated successfully!        |");
        System.out.println("+------------------------------------------------+\n");
    }

    // Update CSV file
    private void saveAllMedication(List<MedicationInventory> medications) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_MEDICATION_INVENTORY))) {
            writer.write("MedicationName,StockLevel,StockAlertLevel\n");
            for (MedicationInventory medication : medications) {
                writer.write(medication.getMedicationName() + ","
                        + medication.getStockLevel() + ","
                        + medication.getStockAlertLevel() + "\n");
            }
        }
    }

    public void saveReplenishmentRequest(String medicationName, String requestId, String status) {
        System.out.println("+------------------------------------------------+");
        System.out.println("|           Saving Replenishment Request         |");
        System.out.println("+------------------------------------------------+");
        try (FileWriter writer = new FileWriter(FILE_PATH_REPLENISHMENT_REQUESTS, true)) {
            writer.append(medicationName).append(",");
            writer.append(requestId).append(",");
            writer.append(status).append("\n");
            System.out.println("| Replenishment request saved successfully!      |");
        } catch (IOException e) {
            System.out.printf("| Error: %-40s |\n", e.getMessage());
        }
        System.out.println("+------------------------------------------------+\n");
    }

    public boolean medicationExists(String medicationName) throws IOException {
        System.out.println("+------------------------------------------------+");
        System.out.println("|          Checking Medication Existence         |");
        System.out.println("+------------------------------------------------+");

        List<MedicationInventory> medications = loadAllMedications();
        for (MedicationInventory medication : medications) {
            if (medication.getMedicationName().equalsIgnoreCase(medicationName)) {
                System.out.println("| Medication exists in inventory.                |");
                System.out.println("+------------------------------------------------+\n");
                return true;
            }
        }
        System.out.println("| Medication does not exist in inventory.        |");
        System.out.println("+------------------------------------------------+\n");
        return false;
    }
}