import java.time.LocalDate;
import java.util.ArrayList;

public class Expense extends Transaction {
    private String notes;
    private double expenseAmount;
    public Category category;

    public Expense(double e) {
        this.expenseAmount = e;
    }

    @Override
    public void save(int userId, String Name, LocalDate Date, double aAmount, String type, String categoryName) {
        this.account = new Account(userId);
        super.save(userId, Name, Date, aAmount, type, categoryName);
    }

    @Override
    public void getTransactionByCategory(Category category) {
        String sql = "SELECT * FROM Transactions WHERE Category = ? AND Type = ?";
        String[] p = {category.getName(), "Expense"};
        displayTransaction(sql, p);
    }

    @Override
    public void getTransactionByDate(int userID, LocalDate aStart, LocalDate aEnd) {
        String sql = "SELECT * FROM Transactions WHERE Date BETWEEN ? AND ? AND User_ID = ? AND Type = ?";
        String[] p = {aStart.toString(), aEnd.toString(), Integer.toString(userID), "Expense"};
        displayTransaction(sql, p);
    }
}