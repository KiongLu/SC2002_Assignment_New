package entity;

public class Doctor extends User {
    private String specialization;
    private String staffcontact;
    private String staffemail;

    public Doctor(String userid, String name, String role, String password, String gender, String age, String specialization, String staffemail, String staffcontact) {
        super(userid, name, "Doctor", password, gender, age);
        this.specialization = specialization;
        this.staffemail = staffemail;
        this.staffcontact = staffcontact;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getStaffEmail() {
        return staffemail;
    }

    public void setStaffEmail(String staffemail) {
        this.staffemail = staffemail;
    }

    public String getStaffContact() {
        return staffcontact;
    }

    public void setStaffContact(String staffcontact) {
        this.staffcontact = staffcontact;
    }
}
