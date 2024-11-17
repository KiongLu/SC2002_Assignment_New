package controller;



public interface PasswordChangerInterface {
    public boolean changePassword(String hospitalID, String hashedPassword);
}
