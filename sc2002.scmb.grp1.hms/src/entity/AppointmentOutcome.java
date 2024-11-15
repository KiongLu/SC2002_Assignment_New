package entity;

public class AppointmentOutcome {
    private String outcomeId;
    private String appointmentId;
    private String date;
    private String serviceType;
    private String prescribedMedication;
    private String medicationStatus; // e.g., "Pending", "Dispensed"
    private String consultationNotes;

    public AppointmentOutcome(String outcomeId, String appointmentId, String date, String serviceType, String prescribedMedication, String medicationStatus, String consultationNotes) {
        this.outcomeId = outcomeId;
        this.appointmentId = appointmentId;
        this.date = date;
        this.serviceType = serviceType;
        this.prescribedMedication = prescribedMedication;
        this.medicationStatus = medicationStatus;
        this.consultationNotes = consultationNotes;
    }

    // Getters and Setters
    public String getOutcomeId() {
        return outcomeId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getDate() {
        return date;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getPrescribedMedication() {
        return prescribedMedication;
    }

    public String getMedicationStatus() {
        return medicationStatus;
    }

    public void setMedicationStatus(String medicationStatus) {
        this.medicationStatus = medicationStatus;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

	public void setConsultationNotes(String consultationNotes) {
		this.consultationNotes = consultationNotes;
		
	}
}
