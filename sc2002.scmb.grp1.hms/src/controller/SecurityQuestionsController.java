package controller;

import boundary.PasswordForgetView;

public class SecurityQuestionsController {


    public boolean checkHaveQuestions(String hospitalID) {
        String role = extractPrefix(hospitalID);
        RepositoryController repositoryController = new RepositoryController();
        checkHaveQuestionsInterface repository = (checkHaveQuestionsInterface) repositoryController.getRepository(role);
        if(repository == null){
            System.out.println("Invalid ID or password. Returning to main menu.");
            System.out.println();
            return false;
        }
        return repository.checkHaveQuestions(hospitalID);
    }

    public boolean enableQuestions(String hospitalID) {
        String role = extractPrefix(hospitalID);
        RepositoryController repositoryController = new RepositoryController();
        checkHaveQuestionsInterface repository = (checkHaveQuestionsInterface) repositoryController.getRepository(role);
        if (repository == null) {
            System.out.println("Invalid ID or role. Returning to main menu.");
            System.out.println();
            return false;
        }
        PasswordForgetView passwordForget = new PasswordForgetView();
        return passwordForget.Menu(hospitalID, repository);
    
    }

    private static String extractPrefix(String hospitalId) {
        if (hospitalId.startsWith("PH")) {
            return "PH"; // Return "Ph" if the ID starts with "Ph"
        } else if (hospitalId.startsWith("P")) {
            return "P"; // Return "P" if the ID starts with "P"
        } else if (hospitalId.startsWith("A")) {
            return "A";
        } else if (hospitalId.startsWith("D")) {
            return "D";
        }
        return "NULL"; // Return an empty string if there's no matching prefix
    }
    public boolean changeSecurityQuestionControl(String hospitalID, String question, String answer){
        String role = extractPrefix(hospitalID);
        RepositoryController rc = new RepositoryController();
        ChangeSecurityQuestionInterface repository = (ChangeSecurityQuestionInterface)rc.getRepository(role);
        return repository.changeSecurityQuestion(hospitalID, question, answer);


    }
}
