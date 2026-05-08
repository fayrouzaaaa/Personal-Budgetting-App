import java.util.*;
import java.time.*;

/**
 * Represents a generic financial transaction within the system.
 * * <p>The Transaction class serves as the base class for specific transaction types
 * (such as Income and Expense). It provides the core functionality for saving
 * transactions to the database, retrieving history based on categories or dates,
 * and displaying formatted transaction logs to the console.</p>

 */
public class Transaction {
    private int transactionId;
    protected double balance;
    private LocalDate date;

    /** Database helper instance for executing SQL queries. */
    protected Database db = new Database();

    protected int user_ID;

    /** The account associated with the user performing the transaction. */
    protected Account account;

    /** The category classification for this transaction. */
    public Category category;

    /**
     * Persists a new transaction record into the database.
     * * @param userId       The unique ID of the user.
     * @param Name         The descriptive name of the transaction.
     * @param Date         The date the transaction occurred.
     * @param aAmount      The monetary value of the transaction.
     * @param type         The type of transaction (e.g., "Income", "Expense").
     * @param categoryName The name of the category this transaction belongs to.
     */
    public void save(int userId, String Name, LocalDate Date, double aAmount, String type, String categoryName) {
        String sql = "INSERT INTO Transactions (Name, Amount, User_ID, Type, Date, Category) VALUES (?, ?, ?, ?, ?, ?)";
        String[] p = {
                Name,
                Double.toString(aAmount),
                Integer.toString(userId),
                type,
                Date.toString(),
                categoryName
        };
        db.updateQuery(sql, p);
    }

    /**
     * Retrieves and displays all transactions belonging to a specific category.
     * * @param category The {@link Category} object used for filtering.
     */
    public void getTransactionByCategory(Category category) {
        String sql = "SELECT * FROM Transactions WHERE Category = ?";
        String[] p = {category.getName()};
        displayTransaction(sql, p);
    }

    /**
     * Retrieves and displays transactions for a specific user within a date range.
     * * @param userID The unique ID of the user.
     * @param aStart The start date of the range.
     * @param aEnd   The end date of the range.
     */
    public void getTransactionByDate(int userID, LocalDate aStart, LocalDate aEnd) {
        String sql = "SELECT * FROM Transactions  WHERE Date BETWEEN ? AND ? AND User_ID = ?";
        String[] p = {aStart.toString(), aEnd.toString(), Integer.toString(userID)};
        displayTransaction(sql, p);
    }

    /**
     * Executes a SELECT query and prints the resulting transactions in a formatted table.
     * * @param sql The SQL query string.
     * @param p   The parameters to be bound to the query.
     */
    public void displayTransaction(String sql, String[] p) {
        ArrayList<String> IDResults = db.selectQuery(sql, p, "ID");
        ArrayList<String> NameResults = db.selectQuery(sql, p, "Name");
        ArrayList<String> AmountResults = db.selectQuery(sql, p, "Amount");
        ArrayList<String> TypeResults = db.selectQuery(sql, p, "Type");
        ArrayList<String> DateResults = db.selectQuery(sql, p, "Date");
        ArrayList<String> CategoryResults = db.selectQuery(sql, p, "Category");

        if (IDResults.isEmpty()) {
            System.out.println("No Transactions!");
        } else {
            System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n",
                    "Transaction ID", "Transaction Name", "Transaction Amount",
                    "Transaction Type", "Transaction Date", "Category");
            for (int i = 0; i < IDResults.size(); i++) {
                System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n",
                        IDResults.get(i), NameResults.get(i), AmountResults.get(i),
                        TypeResults.get(i), DateResults.get(i), CategoryResults.get(i));
            }
        }
    }

    /**
     * Displays all transaction records associated with a specific user.
     * * @param userID The unique ID of the user.
     */
    public void showTransaction(int userID) {
        String sql = "SELECT * FROM Transactions WHERE User_ID = ?";
        String[] p = {Integer.toString(userID)};
        displayTransaction(sql, p);
    }

    /**
     * Fetches and displays the most recent transactions grouped by date (Today, Yesterday, or specific dates).
     * * @param userID The unique ID of the user.
     */
    public void displayRecentTransactions(int userID) {
        String sqlForLatestTransactions = "select distinct Date from Transactions where User_ID = ? order by Date desc";
        String[] p = {Integer.toString(userID)};
        ArrayList<String> DateResults = db.selectQuery(sqlForLatestTransactions, p, "Date");

        if (DateResults.isEmpty()) {
            System.out.println("No recent Transactions!");
        } else {
            LocalDate todayDate = LocalDate.now();
            LocalDate yesterdayDate = todayDate.minusDays(1);

            // Display most recent date group
            if (DateResults.getFirst().equals(todayDate.toString())) {
                System.out.println("Today:");
            } else {
                System.out.println(DateResults.getFirst() + ":");
            }
            getTransactionByDate(userID, LocalDate.parse(DateResults.getFirst()), LocalDate.parse(DateResults.getFirst()));

            // Display second most recent date group if it exists
            if (DateResults.size() != 1) {
                if (DateResults.get(1).equals(yesterdayDate.toString())) {
                    System.out.println("Yesterday:");
                } else {
                    System.out.println(DateResults.get(1) + ":");
                }
                getTransactionByDate(userID, LocalDate.parse(DateResults.get(1)), LocalDate.parse(DateResults.get(1)));
            }
        }
    }

    /**
     * Retrieves the distinct transaction types (e.g., Income, Expense) found in the user's history.
     * * @param userID The unique ID of the user.
     * @return An array of strings containing unique transaction types.
     */
    public String[] getTransactionsType(int userID) {
        String sql = "SELECT DISTINCT Type FROM Transactions WHERE User_ID = ?";
        String[] p = {Integer.toString(userID)};
        ArrayList<String> result = db.selectQuery(sql, p, "Type");
        if (result.isEmpty()) {
            return new String[]{"Expense", "Income"};
        }
        return result.toArray(new String[0]);
    }
}