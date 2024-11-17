package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import entity.ReplenishmentRequests;

public class ReplenishmentRequestRepository {
    private static final String FILE_PATH_REPLENISHMENT_REQUESTS = "sc2002.scmb.grp1.hms/resource/ReplenishmentRequests.csv";
    private static int nextRequestId = -1; // Uninitialized marker

    // Lazy initialization for request ID
    private void initializeRequestId() throws IOException {
        int highestId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_REPLENISHMENT_REQUESTS))) {
            String line;
            reader.readLine(); // Skip the header row if present

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length > 0) {
                    try {
                        int currentId = Integer.parseInt(fields[0]); // Read the first field as ID
                        highestId = Math.max(highestId, currentId);
                    } catch (NumberFormatException e) {
                        // Ignore invalid IDs
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File not found means this is the first request
        }

        nextRequestId = highestId + 1; // Start with the next ID
    }

    // Save a replenishment request to the CSV file
    public void saveReplenishmentRequest(String medicationName, int quantity) throws IOException {
        if (nextRequestId == -1) { // Initialize only if not already initialized
            initializeRequestId();
        }

        String status = "Pending";

        try (FileWriter writer = new FileWriter(FILE_PATH_REPLENISHMENT_REQUESTS, true)) {
            // Convert int `nextRequestId` to String when writing to the file
            writer.append(String.valueOf(nextRequestId)).append(",") // Use String.valueOf for conversion
                    .append(medicationName).append(",")
                    .append(String.valueOf(quantity)).append(",")
                    .append(status).append("\n");

            System.out.println("+------------------------------------------------+");
            System.out.printf("| Replenishment request ID %-20d saved. |\n", nextRequestId);
            System.out.println("+------------------------------------------------+\n");

            nextRequestId++; // Increment the static counter after saving
        } catch (IOException e) {
            throw new IOException("Error saving replenishment request: " + e.getMessage(), e);
        }
    }

    // Load all replenishment requests from the CSV file
    public List<ReplenishmentRequests> loadAllRequests() throws IOException {
        List<ReplenishmentRequests> requests = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_REPLENISHMENT_REQUESTS))) {
            String line;
            reader.readLine(); // Skip the header row if there is one

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length == 4) { // Ensure all fields are present
                    try {
                        int requestId = Integer.parseInt(fields[0]); // Parse ID as int
                        String medicationName = fields[1];
                        int quantity = Integer.parseInt(fields[2]); // Parse quantity as int
                        String status = fields[3].trim();

                        requests.add(new ReplenishmentRequests(requestId, medicationName, quantity, status));
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing row: " + line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // If the file does not exist, return an empty list
            System.err.println("Replenishment requests file not found. Returning an empty list.");
        } catch (IOException e) {
            throw new IOException("Error reading replenishment requests: " + e.getMessage(), e);
        }

        return requests;
    }

    // Get all pending requests
    public List<ReplenishmentRequests> pendingRequests() throws IOException {
        List<ReplenishmentRequests> allRequests = loadAllRequests();
        List<ReplenishmentRequests> pending = new ArrayList<>();

        for (ReplenishmentRequests request : allRequests) {
            if ("Pending".equalsIgnoreCase(request.getStatus())) {
                pending.add(request);
            }
        }

        return pending;
    }

    public ReplenishmentRequests getRequestById(int requestId) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_REPLENISHMENT_REQUESTS))) {
            String line;
            reader.readLine(); // Skip the header row if there is one

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length == 4) { // Ensure all fields are present
                    try {
                        int currentRequestId = Integer.parseInt(fields[0]); // Parse ID as int
                        if (currentRequestId == requestId) {
                            String medicationName = fields[1];
                            int quantity = Integer.parseInt(fields[2]); // Parse quantity as int
                            String status = fields[3].trim();

                            // Return the matching request
                            return new ReplenishmentRequests(currentRequestId, medicationName, quantity, status);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing row: " + line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Replenishment requests file not found.");
            return null; // Return null if the file does not exist
        } catch (IOException e) {
            throw new IOException("Error reading replenishment requests: " + e.getMessage(), e);
        }

        // Return null if no matching request was found
        return null;
    }

    public void updateRequestStatus(int requestId, String newStatus) throws IOException {
        List<ReplenishmentRequests> allRequests = loadAllRequests(); // Load all requests
    
        // Find and update the status of the matching request
        for (ReplenishmentRequests request : allRequests) {
            if (request.getRequestId() == requestId) {
                // Update the status of the found request
                request = new ReplenishmentRequests(
                    request.getRequestId(),
                    request.getMedicationName(),
                    request.getQuantity(),
                    newStatus
                );
                break; // Exit loop after finding and updating the request
            }
        }
    
        // Overwrite the CSV file with the updated list of requests
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_REPLENISHMENT_REQUESTS))) {
            // Write the header row
            writer.write("RequestId,MedicationName,Quantity,Status\n");
    
            // Write all requests back to the file, including the updated status
            for (ReplenishmentRequests request : allRequests) {
                writer.write(request.getRequestId() + "," +
                             request.getMedicationName() + "," +
                             request.getQuantity() + "," +
                             request.getStatus() + "\n");
            }
        } catch (IOException e) {
            throw new IOException("Error updating request status: " + e.getMessage(), e);
        }
    
        System.out.println("Request ID " + requestId + " status updated to " + newStatus);
    }
}