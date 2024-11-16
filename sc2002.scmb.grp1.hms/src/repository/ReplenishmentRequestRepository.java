package repository;

import java.io.FileWriter;
import java.io.IOException;

public class ReplenishmentRequestRepository {
    private static final String FILE_PATH_REPLENISHMENT_REQUESTS = "sc2002.scmb.grp1.hms//resource//ReplenishmentRequests.csv";
    private static int lastRequestId = 0;

    private int generateNextRequestId() {
        return ++lastRequestId; // Increment and return the next ID
    }
    // Method to save a replenishment request to the CSV file
    public void saveReplenishmentRequest(String medicationName, int quantity) throws IOException {
        int requestId = generateNextRequestId();
        try (FileWriter writer = new FileWriter(FILE_PATH_REPLENISHMENT_REQUESTS, true)) {
            // Append the new request as a row in the CSV file
            writer.append(requestId + ",");
            writer.append(medicationName).append(",");
            writer.append(String.valueOf(quantity)).append(",");
            writer.append("pending\n"); // Default status is "pending"
            System.out.println("Replenishment request saved to file." + requestId);
        } catch (IOException e) {
            System.err.println("Error writing replenishment request to file: " + e.getMessage());
            throw e;
        }
    }
}
