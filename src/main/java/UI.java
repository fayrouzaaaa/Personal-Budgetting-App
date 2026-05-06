import java.util.*;
import java.lang.*;

public class UI {

	public void welcomeUI(){
		System.out.println("Welcome to Personal Budget App!");
		System.out.println();
		System.out.println("Don't have an account?");
		System.out.println("1.Sign Up");
		System.out.println();
		System.out.println("Already have an account?");
		System.out.println("2.Log In");
		System.out.println();
		System.out.println("3.Exit");

		Scanner input = new Scanner(System.in);
		int choice = input.nextInt();

		switch (choice){
			case 1:
				signupUI();
				break;
			case 2:
				loginUI();
				break;
			case 3:
				exitApp();
				break;
			default:
				System.out.println("Error: Invalid Choice.");
		}
	}

	public void exitApp() {
		System.out.println("Thank you for using Personal Budget App!");
		System.exit(0);
	}

	public void signupUI(){
		Scanner input = new Scanner(System.in);

		System.out.println("Sign Up");
		System.out.println();
		System.out.println("Enter your name: ");
		String name = input.nextLine();
		System.out.println();
		System.out.println("Enter your email: ");
		String email = input.nextLine();
		System.out.println();
		System.out.println("Enter your password: ");
		String password = input.nextLine();
		System.out.println();
		System.out.println("Confirm your password: ");
		String passConfirm = input.nextLine();
		System.out.println();

		while (!(password.equals(passConfirm))){
			System.out.println("Error: Password and Confirmation do not match. Please try again.");
			System.out.println();
			System.out.println("Enter your password: ");
			password = input.nextLine();
			System.out.println();
			System.out.println("Confirm your password: ");
			passConfirm = input.nextLine();
		}
	}

	public void loginUI(){
		Scanner input = new Scanner(System.in);

		System.out.println("Log In");
		System.out.println();
		System.out.println("Enter your email: ");
		String email = input.nextLine();
		System.out.println();
		System.out.println("Enter your password: ");
		String password = input.nextLine();
		System.out.println();
	}


 	public UI _unnamed_UI_;
 	public UI _unnamed_UI_2;
 	//public User _unnamed_User_3;
	public void addGoal() {
		  throw new UnsupportedOperationException();
	}
 	public void displayMessage() {
 		throw new UnsupportedOperationException();
 	}

 	public void viewReportScreen() {
 		throw new UnsupportedOperationException();
 	}
     public void showCharts() {
 		throw new UnsupportedOperationException();
 	}

 	public void addTransaction() {
 		throw new UnsupportedOperationException();
 	}

 	public void displayNewSummary() {
 		throw new UnsupportedOperationException();
 	}


 	public void changeCurrency() {
 		throw new UnsupportedOperationException();
 	}

 	public void viewProfile() {
 		throw new UnsupportedOperationException();
 	}
 }