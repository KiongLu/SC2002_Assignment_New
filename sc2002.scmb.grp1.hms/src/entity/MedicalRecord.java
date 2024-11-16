package entity;

public class MedicalRecord {
    private String recordId;
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private String treatment;
    private String prescription;

    public MedicalRecord(String recordId, String patientId, String doctorId, String diagnosis, String treatment, String prescription) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.prescription = prescription;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
		
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
		
	}
	
	public String getPrescription() {
        return prescription;
    }

	public void setPrescription(String prescription) {
		this.prescription = prescription;
		
	}
	
	// toString() method to format the output
    @Override
    public String toString() {
        return "Record ID:    " + recordId + "\n" +
               "Diagnosis:    " + diagnosis + "\n" +
               "Treatment:    " + treatment + "\n" +
               "Prescription: " + prescription;
    }

    public String patientMRToString(){

        return String.format("| %-9s | %-10s | %-9s | %-12s |\n",
        recordId,
        diagnosis,
        treatment,
        prescription);
    }
}
