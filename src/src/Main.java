import java.time.LocalDate;
import java.util.Scanner;


        public class Main {
            public static void main(String[] args){

                Authentication auth = new Authentication();
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


                sc.close();
                /* TEST INCOME CLASS
                income in = new income(1000);
                Category c = new Category(1 ,"Study" ,true);
                in.save("fees" , LocalDate.of(2026,5,6), 200 , c);*/
                String[] p= {};
               // in.displayTransaction("SELECT * FROM Transactions" , p );
              /* TEST EXPENSE CLASS
               Expense e = new Expense(400);
                Category c2 = new Category(2 ,"Drinks" , true);
                e.save("Coffee" ,LocalDate.of(2026,5,6) ,100 , c2);
                e.displayTransaction("SELECT * FROM Transactions WHERE ID > 4" , p );*/

            }

        }

