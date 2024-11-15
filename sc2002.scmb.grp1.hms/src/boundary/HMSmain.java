package boundary;

import controller.AuthenticationController;



public class HMSmain {
	public static void main(String[] args) {
		AuthenticationController authenticationController = new AuthenticationController();
		authenticationController.startSignIn(); 
    }

}