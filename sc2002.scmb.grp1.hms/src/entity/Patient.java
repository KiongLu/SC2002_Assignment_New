package entity;

/**
 * Represents a patient in the hospital.
 */
public class Patient extends User {
    private String phoneNumber;
    private String email;
    private String dob;
    private String bloodtype;

    public Patient(String userid, String name, String Role, String password, String gender, String age, String phoneNumber, String email, String dob, String bloodtype) {
        super(userid, name, "Patient", password, gender, age);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dob = dob;
        this.bloodtype = bloodtype;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String address) {
        this.email = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }
}
