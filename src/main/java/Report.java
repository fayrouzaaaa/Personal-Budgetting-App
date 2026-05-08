import java.time.*;
import java.util.ArrayList;

/**
 * The Report class handles the aggregation and generation of financial summaries.
 * * <p>It provides methods to calculate total income, total expenses, and balance
 * for specific users over defined time periods. It also counts active transactions,
 * budget items, and goals, serving as the data-processing engine for the
 * reporting and dashboard screens.</p>
 * * @author YourName
 * @version 1.0
 */
public class Report {
    private String month;
    private int reportId;
    private Database db = new Database();

    /** Reference to a transaction object to access database retrieval methods. */
    public Transaction t = new Transaction();

    /**
     * Retrieves the current balance for a specific user.
     * * @param userID The unique ID of the user.
     * @return The current balance as a double.
     */
    public double getBalance(int userID){
        Account account = new Account(userID);
        return account.getBalance();
    }

    /**
     * Calculates the sum of all income transactions for a user within a date range.
     * * @param userID    The unique ID of the user.
     * @param startDate The start date of the period.
     * @param endDate   The end date of the period.
     * @return A string representing the total income amount.
     */
    public String getTotalIncome(int userID , LocalDate startDate , LocalDate endDate){
        String sqlForTotalIncome = "SELECT SUM(Amount) AS TotalIncome FROM Transactions WHERE Type = ? AND User_ID=? AND Date BETWEEN ? and ?";
        String[] pForTotalIncome = {"Income" , Integer.toString(userID) , startDate.toString() , endDate.toString()};
        ArrayList<String> TotalIncome = db.selectQuery(sqlForTotalIncome , pForTotalIncome, "TotalIncome");
        return TotalIncome.getFirst();
    }

    /**
     * Calculates the sum of all expense transactions for a user within a date range.
     * * @param userID    The unique ID of the user.
     * @param startDate The start date of the period.
     * @param endDate   The end date of the period.
     * @return A string representing the total expense amount.
     */
    public String getTotalExpenses(int userID , LocalDate startDate , LocalDate endDate){
        String sqlForTotalExpense= "SELECT SUM(Amount) AS TotalExpense FROM Transactions WHERE Type =? AND User_ID=? AND Date BETWEEN ? and ?";
        String[] pForTotalExpense = {"Expense" , Integer.toString(userID) , startDate.toString() , endDate.toString()};
        ArrayList<String> TotalExpense = db.selectQuery(sqlForTotalExpense ,pForTotalExpense , "TotalExpense");
        return TotalExpense.getFirst();
    }

    /**
     * Returns the total count of transactions made by a user.
     * * @param userID The unique ID of the user.
     * @return The integer count of transactions.
     */
    public int getTransactionCount(int userID) {
        String sql = "Select Count(ID) AS COUNT FROM TRANSACTIONS WHERE User_ID=?";
        String[] p = {String.valueOf(userID)};
        ArrayList<String> fetch = db.selectQuery(sql, p, "COUNT");
        return Integer.valueOf(fetch.get(0));
    }

    /**
     * Returns the total count of budget items across all budgets for a user.
     * * @param userID The unique ID of the user.
     * @return The integer count of budget items.
     */
    public int getBudgetsCount(int userID){
        String sql = "Select Count(Budget_Items.id) AS COUNT FROM Budget_Items, Budgets " +
                "WHERE Budgets.user_id=? AND Budgets.budget_id=Budget_Items.budget_id";
        String[] p = {String.valueOf(userID)};
        ArrayList<String> fetch = db.selectQuery(sql, p, "COUNT");
        return Integer.valueOf(fetch.get(0));
    }

    /**
     * Returns the total count of financial goals created by a user.
     * * @param userID The unique ID of the user.
     * @return The integer count of goals.
     */
    public int getGoalsCount(int userID){
        String sql = "Select Count(goal_id) AS COUNT FROM Goals where user_id = ?";
        String[] p = {String.valueOf(userID)};
        ArrayList<String> fetch = db.selectQuery(sql, p, "COUNT");
        return Integer.valueOf(fetch.get(0));
    }

    /**
     * Generates a monthly financial report summary.
     * * <p>This method converts the input month string/number into a date range,
     * calculates income, expenses, and savings, and prints the summary to the console.
     * It returns an array containing the balance, income, expenses, and savings.</p>
     * * @param m      The month as a string (e.g., "January") or a number (e.g., "1").
     * @param userID The unique ID of the user.
     * @return An {@link ArrayList} of strings: [balance, totalIncome, totalExpense, savingAmount].
     */
    public ArrayList<String> generateReport(String m , int userID) {
        int month;
        if(m.length() <= 2){
            month = Integer.parseInt(m);
        }
        else {
            month = Month.valueOf(m.toUpperCase()).getValue();
        }

        // Defaults to year 2026 as per system context
        LocalDate startDate = LocalDate.of(2026 , month , 1);
        int lastDayOfTheMonth = startDate.lengthOfMonth();
        LocalDate endDate = LocalDate.of(2026 , month , lastDayOfTheMonth);

        // Display detailed transactions in console
        t.getTransactionByDate(userID, startDate , endDate);

        String balance = String.valueOf(getBalance(userID));
        String totalIncome = getTotalIncome(userID , startDate , endDate);
        String totalExpense = getTotalExpenses(userID , startDate , endDate);

        double savings = Double.valueOf(totalIncome) - Double.valueOf(totalExpense);
        String savingAmount = (savings >= 0) ? String.valueOf(savings) : "0.0";

        System.out.println("your Balance: " + balance);
        System.out.println("Total income this month: " + totalIncome);
        System.out.println("Total Expense in this month: " + totalExpense);
        System.out.println("Saving amounts: " + savingAmount);

        ArrayList<String> returnArray = new ArrayList<>();
        returnArray.add(balance);
        returnArray.add(totalIncome);
        returnArray.add(totalExpense);
        returnArray.add(savingAmount);
        return returnArray;
    }

    /**
     * Placeholder method for generating visual charts for a user's financial status.
     * * @param userID    The ID of the user.
     * @param startDate The start date for chart data.
     * @param endDate   The end date for chart data.
     */
    public void generateCharts(int userID , LocalDate startDate , LocalDate endDate) {
        double balance = getBalance(userID);
        String totalIncome = getTotalIncome(userID , startDate , endDate);
        String totalExpense = getTotalExpenses(userID , startDate, endDate);
    }
}