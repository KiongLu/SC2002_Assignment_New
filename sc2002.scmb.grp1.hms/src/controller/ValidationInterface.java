package controller;

import entity.User;

public interface ValidationInterface {

    public User validateCredentials(String id, String password);
}
