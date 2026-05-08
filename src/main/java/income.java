import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents a financial inflow (Income) transaction.
 * * <p>The Income class extends the {@link Transaction} base class to handle
 * money added to an account. It provides specialized logic for filtering
 * transaction history by "Income" type and overrides the display logic
 * to provide a formatted table of results.</p>

 */
public class income extends Transaction {

    /** The source of the income (e.g., Salary, Gift, Investment). */
    private String source;

    /**
     * Constructs an Income object with a specific initial balance or amount.
     * * @param b The numerical value associated with this income instance.
     */
    public income(double b) {
        this.balance = b;
    }

    /**
     * Saves the income transaction record to the database.
     * * <p>Utilizes the parent {@link Transaction#save} implementation to
     * persist common transaction details.</p>
     * * @param userId       The unique ID of the user receiving the income.
     * @param Name         The descriptive name of the income transaction.
     * @param Date         The date the income was received.
     * @param aAmount      The monetary value of the income.
     * @param type         The transaction type (expected to be "Income").
     * @param categoryName The name of the category associated with this income.
     */
    @Override
    public void save(int userId, String Name, LocalDate Date, double aAmount, String type, String categoryName) {
        super.save(userId, Name, Date, aAmount, type, categoryName);
    }

    /**
     * Retrieves and displays all income-type transactions belonging to a specific category.
     * * @param category The {@link Category} object used to filter results.
     */
    @Override
    public void getTransactionByCategory(Category category) {
        String sql = "SELECT * FROM Transactions WHERE Category = ? and Type = ?";
        String[] p = {category.getName(), "Income"};
        displayTransaction(sql, p);
    }

    /**
     * Retrieves and displays all income-type transactions within a specific date range for a user.
     * * @param userID The unique ID of the user.
     * @param aStart The start of the date range.
     * @param aEnd   The end of the date range.
     */
    @Override
    public void getTransactionByDate(int userID, LocalDate aStart, LocalDate aEnd) {
        String sql = "SELECT * FROM Transactions WHERE Date BETWEEN ? AND ? AND User_ID = ? AND Type = ?";
        String[] p = {aStart.toString(), aEnd.toString(), Integer.toString(userID), "Income"};
        displayTransaction(sql, p);
    }

    /**
     * Executes the provided SQL query and prints the resulting income transactions
     * to the console in a formatted table.
     * * <p>This method fetches data for Transaction ID, Name, Amount, Date, and Category,
     * then iterates through the results to print aligned columns.</p>
     * * @param sql The SQL SELECT statement to execute.
     * @param p   The parameters to bind to the SQL statement.
     */
    @Override
    public void displayTransaction(String sql, String[] p) {
        ArrayList<String> IDResults = db.selectQuery(sql, p, "ID");
        ArrayList<String> NameResults = db.selectQuery(sql, p, "Name");
        ArrayList<String> AmountResults = db.selectQuery(sql, p, "Amount");
        ArrayList<String> DateResults = db.selectQuery(sql, p, "Date");
        ArrayList<String> CategoryResults = db.selectQuery(sql, p, "Category");

        // Print header
        System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n",
                "Transaction ID", "Transaction Name", "Transaction Amount",
                "Transaction Type", "Transaction Date", "Category");

        // Print rows
        for (int i = 0; i < IDResults.size(); i++) {
            System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n",
                    IDResults.get(i),
                    NameResults.get(i),
                    AmountResults.get(i),
                    "Income",
                    DateResults.get(i),
                    CategoryResults.get(i));
        }
    }
}