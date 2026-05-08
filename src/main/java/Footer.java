import javax.swing.*;

/**
 * Footer acts as a persistent navigation bar present across different screens
 * of the application.
 * * <p>It extends {@link JPanel} and contains buttons that allow the user to
 * switch between the Dashboard, Transactions, Budgets, Goals, and Reports.
 * It also handles the logout logic to return the user to the signup/login screen.</p>

 */
public class Footer extends JPanel {
    private JFrame current;
    private Regular_User user;

    /**
     * Constructs a navigation footer for the specified frame.
     * * @param currentFrame The currently active {@link JFrame} that will be disposed
     * when navigating to a new screen.
     * @param user         The authenticated {@link Regular_User} whose session
     * data is passed between screens.
     */
    Footer(JFrame currentFrame, Regular_User user){
        this.current = currentFrame;
        this.user = user;
        // Positioned at the bottom of the 800x800 standard frame size
        this.setBounds(-40, 720, 800, 100);

        JButton homeButton = new JButton("Home");
        this.add(homeButton);
        homeButton.addActionListener(e -> homeTab());

        JButton transactionsButton = new JButton("Transactions");
        this.add(transactionsButton);
        transactionsButton.addActionListener(e -> transactionsTab());

        JButton budgetButton = new JButton ("Budgets");
        this.add(budgetButton);
        budgetButton.addActionListener(e -> budgetTab());

        JButton goalButton = new JButton ("Goals");
        this.add(goalButton);
        goalButton.addActionListener(e -> goalTab());

        JButton reportButton = new JButton("Report");
        this.add(reportButton);
        reportButton.addActionListener(e -> reportTab());

        JButton logoutButton = new JButton("Logout");
        this.add(logoutButton);
        logoutButton.addActionListener(e -> logoutTab());
    }

    /**
     * Navigates to the Home dashboard.
     * Checks if the current screen is already the dashboard to avoid redundant object creation.
     */
    public void homeTab(){
        if (!(current instanceof DashboardScreen)){
            current.dispose();
            DashboardScreen dashboardScreen = new DashboardScreen(user);
        }
    }

    /**
     * Navigates to the Transactions management page.
     */
    public void transactionsTab(){
        if (!(current instanceof TransactionPage)){
            current.dispose();
            TransactionPage transactionPage = new TransactionPage(user);
        }
    }

    /**
     * Navigates to the Monthly Budget overview screen.
     */
    public void budgetTab(){
        if (!(current instanceof BudgetScreen)){
            current.dispose();
            BudgetScreen budgetScreen = new BudgetScreen(user);
        }
    }

    /**
     * Navigates to the Savings Goals screen.
     * Note: Current implementation is a placeholder.
     */
    public void goalTab(){}

    /**
     * Navigates to the financial Report generation screen.
     */
    public void reportTab(){
        if (!(current instanceof ReportScreen)){
            current.dispose();
            ReportScreen reportScreen = new ReportScreen(user);
        }
    }

    /**
     * Terminates the current user session, disposes of the current frame,
     * and redirects the user to the {@link SignupScreen}.
     */
    public void logoutTab(){
        current.dispose();
        SignupScreen signupScreen = new SignupScreen();
    }
}