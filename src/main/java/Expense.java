import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents a financial outgoing (Expense) transaction.
 * * <p>The Expense class extends the {@link Transaction} base class to provide
 * specialized logic for handling money leaving an account. It overrides
 * transaction retrieval methods to ensure results are filtered specifically
 * for "Expense" types when querying by category or date range.</p>

 */
public class Expense extends Transaction {
    private String notes;
    private double expenseAmount;
    public Category category;

    /**
     * Constructs an Expense object with a specific amount.
     * * @param e The numerical value of the expense.
     */
    public Expense(double e) {
        this.expenseAmount = e;
    }

    /**
     * Saves the expense transaction to the database.
     * * <p>Initializes the associated {@link Account} for the user and invokes
     * the superclass save method to record the transaction details.</p>
     * * @param userId       The unique ID of the user performing the transaction.
     * @param Name         The descriptive name of the expense.
     * @param Date         The date the expense occurred.
     * @param aAmount      The total amount of the transaction.
     * @param type         The type of transaction (e.g., "Expense").
     * @param categoryName The name of the category this expense belongs to.
     */
    @Override
    public void save(int userId, String Name, LocalDate Date, double aAmount, String type, String categoryName) {
        this.account = new Account(userId);
        super.save(userId, Name, Date, aAmount, type, categoryName);
    }

    /**
     * Retrieves and displays all expense-type transactions for a specific category.
     * * @param category The {@link Category} object used to filter the transactions.
     */
    @Override
    public void getTransactionByCategory(Category category) {
        String sql = "SELECT * FROM Transactions WHERE Category = ? AND Type = ?";
        String[] p = {category.getName(), "Expense"};
        displayTransaction(sql, p);
    }

    /**
     * Retrieves and displays all expense-type transactions within a specific date range
     * for a particular user.
     * * @param userID The unique ID of the user.
     * @param aStart The start date of the reporting period.
     * @param aEnd   The end date of the reporting period.
     */
    @Override
    public void getTransactionByDate(int userID, LocalDate aStart, LocalDate aEnd) {
        String sql = "SELECT * FROM Transactions WHERE Date BETWEEN ? AND ? AND User_ID = ? AND Type = ?";
        String[] p = {aStart.toString(), aEnd.toString(), Integer.toString(userID), "Expense"};
        displayTransaction(sql, p);
    }
}