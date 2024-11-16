package controller;

import boundary.PasswordForgetView;

public class SecurityQuestionsController {

    public boolean checkHaveQuestions(String hospitalID) {
        System.out.println("+------------------------------------------------+");
        System.out.println("|         Checking Security Questions Setup      |");
        System.out.println("+------------------------------------------------+");

        String role = extractPrefix(hospitalID);
        RepositoryController repositoryController = new RepositoryController();
        checkHaveQuestionsInterface repository = (checkHaveQuestionsInterface) repositoryController.getRepository(role);

        if (repository == null) {
            System.out.println("| Invalid ID or password. Returning to main menu.|");
            System.out.println("+------------------------------------------------+\n");
            return false;
        }

        boolean hasQuestions = repository.checkHaveQuestions(hospitalID);

        if (hasQuestions) {
            System.out.println("| Security questions are already set up.         |");
        } else {
            System.out.println("| No security questions are set up for this user.|");
        }
        System.out.println("+------------------------------------------------+\n");
        return hasQuestions;
    }

    public boolean enableQuestions(String hospitalID) {
        System.out.println("+------------------------------------------------+");
        System.out.println("|             Enabling Security Questions        |");
        System.out.println("+------------------------------------------------+");

        String role = extractPrefix(hospitalID);
        RepositoryController repositoryController = new RepositoryController();
        checkHaveQuestionsInterface repository = (checkHaveQuestionsInterface) repositoryController.getRepository(role);

        if (repository == null) {
            System.out.println("| Invalid ID or role. Returning to main menu.    |");
            System.out.println("+------------------------------------------------+\n");
            return false;
        }

        PasswordForgetView passwordForget = new PasswordForgetView();
        boolean isEnabled = passwordForget.Menu(hospitalID, repository);

        if (isEnabled) {
            System.out.println("| Security questions enabled successfully.       |");
        } else {
            System.out.println("| Failed to enable security questions.           |");
        }
        System.out.println("+------------------------------------------------+\n");
        return isEnabled;
    }

    private static String extractPrefix(String hospitalID) {
        System.out.println("+------------------------------------------------+");
        System.out.println("|              Extracting ID Prefix              |");
        System.out.println("+------------------------------------------------+");

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

        System.out.printf("| Hospital ID: %-35s |\n", hospitalID);
        System.out.printf("| Extracted Prefix: %-30s |\n", prefix);
        System.out.println("+------------------------------------------------+\n");
        return prefix;
    }

    public boolean changeSecurityQuestionControl(String hospitalID, String question, String answer) {
        System.out.println("+------------------------------------------------+");
        System.out.println("|         Changing Security Question Setup       |");
        System.out.println("+------------------------------------------------+");

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