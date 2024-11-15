package entity;

public class Availability {
	private String availabilityId;  
    private String doctorId;       
    private String date;             
    private String startTime;       
    private String endTime;         

    public Availability(String availabilityId, String doctorId, String date, String startTime, String endTime) {
        this.availabilityId = availabilityId;
        this.doctorId = doctorId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public String getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(String availabilityId) {
        this.availabilityId = availabilityId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}