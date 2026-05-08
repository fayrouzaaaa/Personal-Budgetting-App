import java.util.*;
import java.time.*;

public class Transaction {
    private int transactionId;
    protected double balance;
    private LocalDate date;
    protected Database db = new Database();
    protected int user_ID;
    protected Account account;
    public Category category;

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

    public void getTransactionByCategory(Category category) {
        String sql = "SELECT * FROM Transactions WHERE Category = ?";
        String[] p = {category.getName()};
        displayTransaction(sql, p);
    }

    public void getTransactionByDate(int userID, LocalDate aStart, LocalDate aEnd) {
        String sql = "SELECT * FROM Transactions  WHERE Date BETWEEN ? AND ? AND User_ID = ?";
        String[] p = {aStart.toString(), aEnd.toString(), Integer.toString(userID)};
        displayTransaction(sql, p);
    }

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
            System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n", "Transaction ID", "Transaction Name", "Transaction Amount", "Transaction Type", "Transaction Date", "Category");
            for (int i = 0; i < IDResults.size(); i++) {
                System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n", IDResults.get(i), NameResults.get(i), AmountResults.get(i), TypeResults.get(i), DateResults.get(i), CategoryResults.get(i));
            }
        }
    }

    public void showTransaction(int userID) {
        String sql = "SELECT * FROM Transactions WHERE User_ID = ?";
        String[] p = {Integer.toString(userID)};
        displayTransaction(sql, p);
    }

    public void displayRecentTransactions(int userID) {
        String sqlForLatestTransactions = "select distinct Date from Transactions where User_ID = ? order by Date desc";
        String[] p = {Integer.toString(userID)};
        ArrayList<String> DateResults = db.selectQuery(sqlForLatestTransactions, p, "Date");

        if (DateResults.isEmpty()) {
            System.out.println("No recent Transactions!");
        } else {
            LocalDate todayDate = LocalDate.now();
            LocalDate yesterdayDate = todayDate.minusDays(1);

            if (DateResults.getFirst().equals(todayDate.toString())) {
                System.out.println("Today:");
            } else {
                System.out.println(DateResults.getFirst() + ":");
            }
            getTransactionByDate(userID, LocalDate.parse(DateResults.getFirst()), LocalDate.parse(DateResults.getFirst()));

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