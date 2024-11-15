package entity;

public abstract class User {
    protected String userid;
    protected String name;
    protected String role;
    protected String password;
    protected String gender;
    protected String age;

    public User(String userid, String name, String role, String password, String gender, String age) {
        this.userid = userid;
        this.name = name;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.age = age;
    }

    public String getUserId() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
