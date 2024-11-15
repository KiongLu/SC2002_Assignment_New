package repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class ReplenishmentRequestRepository {
    private static final String FILE_PATH_REPLENISHMENT_REQUESTS = "sc2002.scmb.grp1.hms/resource/ReplenishmentRequests.csv";

    public void saveReplenishmentRequest(String medicationName) throws IOException {
        String requestId = UUID.randomUUID().toString();
        String status = "pending";

        try (FileWriter writer = new FileWriter(FILE_PATH_REPLENISHMENT_REQUESTS, true)) {
            writer.append(medicationName).append(",")
                    .append(requestId).append(",")
                    .append(status).append("\n");
        } catch (IOException e) {
            throw new IOException("Error saving replenishment request: " + e.getMessage(), e);
        }
    }
}
