package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import entity.ReplenishmentRequests;

public class ReplenishmentRequestRepository {
    private static final String FILE_PATH_REPLENISHMENT_REQUESTS = "sc2002.scmb.grp1.hms/resource/ReplenishmentRequests.csv";

    public void saveReplenishmentRequest(String medicationName, int quantity) throws IOException {
        String requestId = UUID.randomUUID().toString(); // Generate a unique request ID
        String status = "Pending";

        try (FileWriter writer = new FileWriter(FILE_PATH_REPLENISHMENT_REQUESTS, true)) {
            writer.append(medicationName).append(",")
                    .append(requestId).append(",")
                    .append(String.valueOf(quantity)).append(",")
                    .append(status).append("\n");
        } catch (IOException e) {
            throw new IOException("Error saving replenishment request: " + e.getMessage(), e);
        }
    }

    public List<ReplenishmentRequests> loadAllRequests() throws IOException
    {
        List<ReplenishmentRequests> requests = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_REPLENISHMENT_REQUESTS))) {
            String line;
            reader.readLine(); // Skip the header row if there is one

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                
                if (fields.length == 3) {
                    String medicationName = fields[0];
                    String requestId = fields[1];
                    String status = fields[2];

                    if ("pending".equalsIgnoreCase(status.trim())) {
                        requests.add(new ReplenishmentRequests(medicationName, requestId, status));
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading replenishment requests: " + e.getMessage(), e);
        }
        
        return requests;
    }

    public List<ReplenishmentRequests> pendingRequests() throws IOException
    {
        List<ReplenishmentRequests> requests = loadAllRequests();
        List<ReplenishmentRequests> pending = new ArrayList<>();

        for (ReplenishmentRequests request : requests)
        {
            if(request.getStatus() == "Pending")
            {
                pending.add(request);
            }
        }

        return pending;
    }

}
