package controller;

import java.io.IOException;

public interface PasswordChangerInterface {
    public boolean changePassword(String hospitalID, String hashedPassword);
}
