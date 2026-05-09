import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    // UI Colors
    private final Color NAV_BG       = new Color(255, 255, 255);
    private final Color MAIN_PURPLE  = new Color(108, 92, 231);
    private final Color TEXT_GRAY    = new Color(130, 130, 130);
    private final Color BORDER_COLOR = new Color(235, 235, 235);

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

        this.setLayout(new GridLayout(1, 6, 0, 0));
        this.setBackground(NAV_BG);
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));
        this.setBounds(0, 700, 800, 70);

        addStyledButton("Home", e -> homeTab(), current instanceof DashboardScreen);
        addStyledButton("Transactions", e -> transactionsTab(), current instanceof TransactionPage);
        addStyledButton("Budgets", e -> budgetTab(), current instanceof BudgetScreen);
        addStyledButton("Goals", e -> goalTab(), false);
        addStyledButton("Report", e -> reportTab(), current instanceof ReportScreen);
        addStyledButton("Logout", e -> logoutTab(), false);
    }

    private void addStyledButton(String text, java.awt.event.ActionListener action, boolean isActive) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", isActive ? Font.BOLD : Font.PLAIN, 13));
        btn.setForeground(isActive ? MAIN_PURPLE : TEXT_GRAY);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setForeground(MAIN_PURPLE); }
            public void mouseExited(MouseEvent e) { if (!isActive) btn.setForeground(TEXT_GRAY); }
        });

        btn.addActionListener(action);
        this.add(btn);
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