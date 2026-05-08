import javax.swing.*;

public class Footer extends JPanel {
    private JFrame current;
    private Regular_User user;
    Footer(JFrame currentFrame, Regular_User user){
        this.current = currentFrame;
        this.user = user;
        this.setBounds(-40, 720, 800, 100);

        JButton homeButton = new JButton("Home");
        this.add(homeButton);
        homeButton.addActionListener(e->homeTab());

        JButton transactionsButton = new JButton("Transactions");
        this.add(transactionsButton);
        transactionsButton.addActionListener(e->transactionsTab());

        JButton budgetButton = new JButton ("Budgets");
        this.add(budgetButton);
        budgetButton.addActionListener(e->budgetTab());

        JButton goalButton = new JButton ("Goals");
        this.add(goalButton);
        goalButton.addActionListener(e->goalTab());

        JButton reportButton = new JButton("Report");
        this.add(reportButton);
        reportButton.addActionListener(e->reportTab());

        JButton logoutButton = new JButton("Logout");
        this.add(logoutButton);
        logoutButton.addActionListener(e->logoutTab());
    }
    public void homeTab(){
        if (!(current instanceof DashboardScreen)){
            current.dispose();
            DashboardScreen dashboardScreen = new DashboardScreen(user);
        }
    }
    public void transactionsTab(){}
    public void budgetTab(){
        if (!(current instanceof BudgetScreen)){
            current.dispose();
            BudgetScreen budgetScreen = new BudgetScreen(user);
        }
    }
    public void goalTab(){}
    public void reportTab(){}
    public void logoutTab(){
        current.dispose();
        SignupScreen signupScreen = new SignupScreen();
    }
}
