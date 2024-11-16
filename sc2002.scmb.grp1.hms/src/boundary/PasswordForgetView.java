package boundary;

import controller.SecurityQuestionsController;
import controller.checkHaveQuestionsInterface;
import entity.User;
import java.util.Scanner;

public class PasswordForgetView {

    public boolean Menu(String hospitalID, checkHaveQuestionsInterface repository) {
        String answer;
        Scanner scanner = new Scanner(System.in);
        System.out.println(repository.returnQuestion(hospitalID));
        System.out.println("Please enter your answer");
        answer = scanner.nextLine();
        return repository.questionVerification(hospitalID,answer);
    }
}

