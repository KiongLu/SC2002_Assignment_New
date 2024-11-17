package boundary;


import controller.checkHaveQuestionsInterface;
import java.util.Scanner;

public class PasswordForgetView {

    public boolean Menu(String hospitalID, checkHaveQuestionsInterface repository) {
        String answer;
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.print("Question: ");
        System.out.println(repository.returnQuestion(hospitalID));
        System.out.println("Please enter your answer");
        answer = scanner.nextLine();
        
        return repository.questionVerification(hospitalID,answer);
        
    }
}

