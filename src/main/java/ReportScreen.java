import javax.swing.*;
import java.awt.*;
import java.text.DateFormatSymbols;
import java.util.*;

/**
 * The ReportScreen class provides a graphical user interface for viewing detailed
 * monthly financial reports.
 * * <p>It displays key financial metrics including the current balance, total income,
 * total expenses, and total savings for the current month. The screen utilizes
 * the {@link Report} class to fetch data and the {@link Footer} for navigation.</p>

 */
public class ReportScreen extends JFrame {
    private Regular_User user;
    private int userID;
    private int current;
    private String currentName;
    private Report report;
    private Footer footer;

    /**
     * Constructs a new ReportScreen for the specified user.
     * * <p>Initializes the frame, identifies the current month name using
     * {@link DateFormatSymbols}, and triggers the report display logic.</p>
     * * @param user The authenticated {@link Regular_User} whose report is being displayed.
     */
    ReportScreen(Regular_User user) {
        this.setTitle("Report");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Navigation footer initialization
        footer = new Footer(this, user);
        this.add(footer);

        this.user = user;
        this.userID = user.getUserId();
        this.report = new Report();

        // Determine the current month name
        current = Calendar.getInstance().get(Calendar.MONTH);
        String[] months = new DateFormatSymbols().getMonths();
        currentName = months[current];

        displayReport();
    }

    /**
     * Constructs and arranges the visual components of the monthly report.
     * * <p>Fetches calculated values (Balance, Income, Expense, Savings) from the
     * database via the {@link Report#generateReport} method and adds them to the
     * frame as styled labels.</p>
     */
    public void displayReport(){
        // Fetch report data from the database/logic layer
        ArrayList<String> reportValues = report.generateReport(currentName, userID);

        // Header Label
        JLabel monthlyReport = new JLabel (currentName + " Report");
        monthlyReport.setBounds(100, -30, 300,300);
        monthlyReport.setFont(new Font("Segoe UI", Font.BOLD, 40));
        this.add(monthlyReport);

        // Descriptive static labels
        JLabel balance = new JLabel("Balance:");
        balance.setBounds(100, 50, 300, 300);
        balance.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        this.add(balance);

        JLabel income = new JLabel("Total Income:");
        income.setBounds(100, 150, 300, 300);
        income.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        this.add(income);

        JLabel expense = new JLabel ("Total Expense:");
        expense.setBounds(100, 260, 300,300);
        expense.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        this.add(expense);

        JLabel savings = new JLabel("Savings:");
        savings.setBounds(100, 380, 300,300);
        savings.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        this.add(savings);

        // Dynamic value labels
        int yOfLabels = 80;
        for (int i = 0; i < reportValues.size(); i++) {
            JLabel reportValue = new JLabel (reportValues.get(i));
            reportValue.setBounds(100, yOfLabels, 300, 300);
            reportValue.setFont(new Font("Segoe UI", Font.BOLD, 40));
            reportValue.setForeground(new Color(0x93A7F3)); // Theme color

            this.add(reportValue);
            yOfLabels += 115; // Vertical spacing between values
        }
    }
}