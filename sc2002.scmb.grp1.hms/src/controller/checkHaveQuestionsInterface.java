package controller;

import entity.User;

public interface checkHaveQuestionsInterface {

    public boolean checkHaveQuestions(String hospitalID);

    public boolean questionVerification(String hospitalID, String answer);
    public String returnQuestion(String hospitalID);

}
