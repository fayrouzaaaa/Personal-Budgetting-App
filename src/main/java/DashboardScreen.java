import javax.swing.*;
import java.awt.*;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * The DashboardScreen serves as the primary home interface for the application.
 * * <p>It provides a high-level overview of the user's financial status, including
 * the total balance, monthly income/expense summaries, counts for active budgets
 * and goals, and a list of the most recent transactions.</p>

 */
public class DashboardScreen extends JFrame{
    private Footer footer;
    private Regular_User user;
    private int userID;
    private int current;
    private LocalDate startDate, endDate;
    private Report report;

    /**
     * Constructs the DashboardScreen and initializes the user session.
     * * <p>Calculates the current month's date range (start and end date) to filter
     * the monthly report summary and initializes the UI components.</p>
     * * @param user The currently logged-in {@link Regular_User}.
     */
    DashboardScreen(Regular_User user){
        this.user = user;
        userID = user.getUserId();
        this.setTitle("Home");
        this.setSize(800,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Determine current month and year for reporting
        current = Calendar.getInstance().get(Calendar.MONTH);
        current++; // Adjusting from 0-indexed to 1-indexed

        // Defaulting to 2026 as per system context
        startDate = LocalDate.of(2026, current, 1);
        int lastDay = startDate.lengthOfMonth();
        endDate = LocalDate.of(2026, current, lastDay);

        report = new Report();

        // Greeting Header
        JLabel hello = new JLabel("Hello, " + user.getName());
        hello.setBounds(80,-60,700,300);
        hello.setFont(new Font("SansSerif", Font.BOLD, 30));
        this.add(hello);

        footer = new Footer (this, user);
        this.add(footer);

        showSummary();
        showRecentTransactions();
        this.setVisible(true);
    }

    /**
     * Creates and displays the summary section of the dashboard.
     * * <p>Includes:
     * <ul>
     * <li>A main blue card showing Total Balance and monthly Income/Expense.</li>
     * <li>Three smaller cards showing the count of Transactions, Active Budgets, and Goals.</li>
     * </ul>
     * </p>
     */
    public void showSummary() {
        // Main Financial Card
        JPanel summary = new JPanel();
        summary.setLayout(null);
        summary.setBounds(100, 130, 540, 200);
        summary.setBackground(new Color(0x93A7F3));
        this.add(summary);

        JLabel totalBalance = new JLabel("Total Balance:");
        totalBalance.setBounds(20, -120, 400, 300);
        totalBalance.setFont(new Font("SansSerif", Font.PLAIN, 20));
        totalBalance.setForeground(Color.white);
        summary.add(totalBalance);

        JLabel balanceAmount = new JLabel(user.getCurrency() + " " + user.getBalance());
        balanceAmount.setBounds(20, -70, 400, 300);
        balanceAmount.setFont(new Font("SansSerif", Font.BOLD, 50));
        balanceAmount.setForeground(Color.white);
        summary.add(balanceAmount);

        // Monthly Income Section
        JLabel income = new JLabel("Income:");
        income.setBounds(20, 0, 400, 300);
        income.setFont(new Font("SansSerif", Font.PLAIN, 15));
        income.setForeground(Color.white);
        summary.add(income);

        String totalIncome = report.getTotalIncome(userID, startDate, endDate);
        JLabel incomeAmount = new JLabel(user.getCurrency() + " " + totalIncome);
        incomeAmount.setBounds(20, 20, 400, 300);
        incomeAmount.setFont(new Font("SansSerif", Font.BOLD, 20));
        incomeAmount.setForeground(Color.white);
        summary.add(incomeAmount);

        // Monthly Expense Section
        JLabel expense = new JLabel("Expense:");
        expense.setBounds(400, 0, 400, 300);
        expense.setFont(new Font("SansSerif", Font.PLAIN, 15));
        expense.setForeground(Color.white);
        summary.add(expense);

        String totalExpense = report.getTotalExpenses(userID, startDate, endDate);
        JLabel expenseAmount = new JLabel(user.getCurrency() + " " + totalExpense);
        expenseAmount.setBounds(400, 20, 400, 300);
        expenseAmount.setFont(new Font("SansSerif", Font.BOLD, 20));
        expenseAmount.setForeground(Color.white);
        summary.add(expenseAmount);

        // Small Summary Card: Transactions
        JPanel transactionsSummary = new JPanel();
        transactionsSummary.setLayout(null);
        transactionsSummary.setBounds(100, 350, 160, 100);
        transactionsSummary.setBackground(Color.white);
        this.add(transactionsSummary);

        JLabel transactions = new JLabel("Transactions");
        transactions.setBounds(20, 0, 300, 50);
        transactions.setFont(new Font("SansSerif", Font.PLAIN, 20));
        transactionsSummary.add(transactions);

        String countTransactions = String.valueOf(report.getTransactionCount(userID));
        JLabel transactionCount = new JLabel(countTransactions);
        transactionCount.setBounds(65, 40, 300, 50);
        transactionCount.setFont(new Font("SansSerif", Font.BOLD, 50));
        transactionsSummary.add(transactionCount);

        // Small Summary Card: Active Budgets
        JPanel budgetSummary = new JPanel();
        budgetSummary.setLayout(null);
        budgetSummary.setBounds(290, 350, 160, 100);
        budgetSummary.setBackground(Color.white);
        this.add(budgetSummary);

        JLabel budgets = new JLabel("Active Budgets");
        budgets.setBounds(13, 0, 300, 50);
        budgets.setFont(new Font("SansSerif", Font.PLAIN, 20));
        budgetSummary.add(budgets);

        String countBudgets = String.valueOf(report.getBudgetsCount(userID));
        JLabel activeBudgets = new JLabel(countBudgets);
        activeBudgets.setBounds(65, 40, 300, 50);
        activeBudgets.setFont(new Font("SansSerif", Font.BOLD, 50));
        budgetSummary.add(activeBudgets);

        // Small Summary Card: Goals
        JPanel goalSummary = new JPanel();
        goalSummary.setLayout(null);
        goalSummary.setBounds(480, 350, 160, 100);
        goalSummary.setBackground(Color.white);
        this.add(goalSummary);

        JLabel goals = new JLabel("Goals");
        goals.setBounds(50, 0, 300, 50);
        goals.setFont(new Font("SansSerif", Font.PLAIN, 20));
        goalSummary.add(goals);

        String countGoals = String.valueOf(report.getGoalsCount(userID));
        JLabel goalCount = new JLabel(countGoals);
        goalCount.setBounds(65, 40, 300, 50);
        goalCount.setFont(new Font("SansSerif", Font.BOLD, 50));
        goalSummary.add(goalCount);
    }

    /**
     * Fetches and displays the three most recent transactions from the database.
     * * <p>Data retrieved includes the Transaction Name, Category, Date, and Amount.
     * Income amounts are displayed in green with a plus sign, while expenses
     * are displayed in red with a minus sign.</p>
     */
    public void showRecentTransactions(){
        Database db = new Database();
        ArrayList<String> fetch = new ArrayList<>();

        JLabel recent = new JLabel("Recent Transactions");
        recent.setBounds(100,340, 400, 300);
        recent.setFont(new Font("SansSerif", Font.BOLD, 30));
        this.add(recent);

        // Fetch and display Transaction Names
        fetch.addAll(db.selectQuery("select Name from Transactions where User_ID=? order by Date desc", new String[] {String.valueOf(userID)}, "Name"));
        int y = 390;
        for (int i=0; i<fetch.size() && i<3; i++){
            JLabel name = new JLabel(fetch.get(i));
            name.setBounds(100, y, 400,300);
            name.setFont(new Font("SansSerif", Font.BOLD, 20));
            this.add(name);
            y += 60;
        }
        fetch.clear();

        // Fetch and display Categories
        fetch.addAll(db.selectQuery("select Category from Transactions where User_ID=? order by Date desc", new String[] {String.valueOf(userID)}, "Category"));
        y = 420;
        for (int i=0; i<fetch.size() && i<3; i++){
            JLabel category = new JLabel(fetch.get(i));
            category.setBounds(100, y, 400,300);
            category.setFont(new Font("SansSerif", Font.PLAIN, 15));
            this.add(category);
            y += 60;
        }
        fetch.clear();

        // Fetch and display Dates
        fetch.addAll(db.selectQuery("select Date from Transactions where User_ID=? order by Date desc", new String[] {String.valueOf(userID)}, "Date"));
        y = 420;
        for (int i=0; i<fetch.size() && i<3; i++){
            JLabel date = new JLabel(fetch.get(i));
            date.setBounds(220, y, 400,300);
            date.setFont(new Font("SansSerif", Font.PLAIN, 15));
            this.add(date);
            y += 60;
        }
        fetch.clear();

        // Fetch Types and Amounts for color coding and display
        ArrayList<String> type = db.selectQuery("select Type from Transactions where User_ID=? order by Date desc", new String[] {String.valueOf(userID)}, "Type");
        fetch.addAll(db.selectQuery("select Amount from Transactions where User_ID=? order by Date desc", new String[] {String.valueOf(userID)}, "Amount"));
        y = 420;
        for (int i=0; i<fetch.size() && i<3; i++){
            String symbol = "";
            Color color;
            if ((type.get(i)).equals("Income")){
                color = new Color(0x81DE5E); // Green for Income
                symbol = "+ ";
            }
            else {
                color = new Color(0xC23636); // Red for Expense
                symbol = "- ";
            }
            JLabel amount = new JLabel(symbol + fetch.get(i));
            amount.setBounds(550, y, 400,300);
            amount.setFont(new Font("SansSerif", Font.BOLD, 20));
            amount.setForeground(color);
            this.add(amount);
            y += 60;
        }
        fetch.clear();
    }
}