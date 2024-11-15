package entity;


public class Administrator extends User {
	private String staffcontact;
	private String staffemail;
    public Administrator(String userid, String name, String role, String password, String gender, String age, String staffemail, String staffcontact) {
        super(userid, name, "Administrator", password, gender, age);
        this.staffcontact = staffcontact;
        this.staffemail = staffemail;
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