package entity;

public class ReplenishmentRequests {
    private final int requestId; // Keep it as int for consistency with ID generation
    private final String medicationName;
    private final int quantity; // Added quantity field
    private final String status;

    // Constructor to initialize the fields
    public ReplenishmentRequests(int requestId, String medicationName, int quantity, String status) {
        this.requestId = requestId;
        this.medicationName = medicationName;
        this.quantity = quantity;
        this.status = status;
    }

    // Getters
    public int getRequestId() {
        return requestId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }
}