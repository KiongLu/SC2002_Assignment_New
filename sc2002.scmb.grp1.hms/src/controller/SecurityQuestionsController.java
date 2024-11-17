package controller;

import boundary.PasswordForgetView;

public class SecurityQuestionsController {

    public boolean checkHaveQuestions(String hospitalID) {
        
        String role = extractPrefix(hospitalID);
        RepositoryController repositoryController = new RepositoryController();
        checkHaveQuestionsInterface repository = (checkHaveQuestionsInterface) repositoryController.getRepository(role);

        if (repository == null) {
            return false;
        }

        boolean hasQuestions = repository.checkHaveQuestions(hospitalID);
        return hasQuestions;
    }

    public boolean enableQuestions(String hospitalID) {
    

        String role = extractPrefix(hospitalID);
        RepositoryController repositoryController = new RepositoryController();
        checkHaveQuestionsInterface repository = (checkHaveQuestionsInterface) repositoryController.getRepository(role);

        if (repository == null) {
            return false;
        }

        PasswordForgetView passwordForget = new PasswordForgetView();
        boolean isEnabled = passwordForget.Menu(hospitalID, repository);
        return isEnabled;
    }

    private static String extractPrefix(String hospitalID) {

        String prefix;
        if (hospitalID.startsWith("PH")) {
            prefix = "PH";
        } else if (hospitalID.startsWith("P")) {
            prefix = "P";
        } else if (hospitalID.startsWith("A")) {
            prefix = "A";
        } else if (hospitalID.startsWith("D")) {
            prefix = "D";
        } else {
            prefix = "NULL";
        }
        return prefix;
    }

    public boolean changeSecurityQuestionControl(String hospitalID, String question, String answer) {

        String role = extractPrefix(hospitalID);
        RepositoryController rc = new RepositoryController();
        ChangeSecurityQuestionInterface repository = (ChangeSecurityQuestionInterface) rc.getRepository(role);

        if (repository == null) {
            System.out.println("| Invalid ID or role. Cannot change questions.   |");
            System.out.println("+------------------------------------------------+\n");
            return false;
        }

        boolean isChanged = repository.changeSecurityQuestion(hospitalID, question, answer);

        if (isChanged) {
            System.out.println("| Security question changed successfully.        |");
        } else {
            System.out.println("| Failed to change security question.            |");
        }
        System.out.println("+------------------------------------------------+\n");
        return isChanged;
    }
}