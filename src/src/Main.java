import java.sql.*;
import java.util.Scanner;


        public class Main {
            public static void main(String[] args){

                SignupScreen signupScreen = new SignupScreen();
                /*Authentication auth = new Authentication();
                Scanner sc = new Scanner(System.in);

                System.out.println("=== Welcome to Your Personal_Budgetting System ===");

                while (true) {

                    System.out.println("\n1. Register");
                    System.out.println("2. Login");
                    System.out.println("3. Logout");
                    System.out.println("4. Exit");
                    System.out.print("Choose option: ");

                    int choice = sc.nextInt();
                    sc.nextLine();

                    if (choice == 1) {
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter Password: ");
                        String password = sc.nextLine();

                        auth.register(name,email, password);
                    }

                    else if (choice == 2) {
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter Password: ");
                        String password = sc.nextLine();

                        boolean success = auth.login(email, password);

                        if (success) {
                            System.out.println(" Login Success");
                        } else {
                            System.out.println(" Login Failed");
                        }
                    }

                    else if (choice == 3) {
                        auth.logout();
                    }

                    else if (choice == 4) {
                        System.out.println("Goodbye ");
                        break;
                    }

                    else {
                        System.out.println("Invalid choice!");
                    }
                }


                sc.close();*/
            }
        }

