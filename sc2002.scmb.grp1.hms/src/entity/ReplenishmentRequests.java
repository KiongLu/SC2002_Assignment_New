package entity;

public class ReplenishmentRequests {
    private String medicationName;
    private String requestId;
    private String status;

    // Constructor to initialize the fields
    public ReplenishmentRequests(String medicationName, String requestId, String status) {
        this.medicationName = medicationName;
        this.requestId = requestId;
        this.status = status;
    }

    // Getters (optional if you need them for other code)
    public String getMedicationName() {
        return medicationName;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getStatus() {
        return status;
    }

}
