package entity;


public class Pharmacist extends User {
	private String staffemail;
	private String staffcontact;
	
    public Pharmacist(String userid, String name, String Role, String password, String gender, String age, String staffemail,String staffcontact) {
        super(userid, name, "Pharmacist", password, gender, age);
        this.staffemail = staffemail;
        this.staffcontact = staffcontact;
        
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
