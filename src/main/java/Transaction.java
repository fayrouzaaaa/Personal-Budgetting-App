import java.util.Date;
import java.util.*;
import java.time.*;
public class Transaction {
    private int transactionId ;
    private double amount ;
    private LocalDate date ;

    protected Database db = new Database();
    public Report report;
    public Regular_User regularUser ;
    public Category category;


    public void save(String Name , LocalDate Date ,double aAmount, Category aCategory){};


    public void getTransactions(LocalDate aStart, LocalDate aEnd)
    {
        String sql = "SELECT * FROM Transactions , Users WHERE Date BETWEEN ? AND ? AND Users.ID = Transactions.User_ID"; // SQL query
        String[] p ={ aStart.toString() , aEnd.toString()}; // convert dates to string
        displayTransaction(sql , p);

    }
    public void displayTransaction(String sql ,String[] p){
        ArrayList<String> IDResults =db.selectQuery(sql , p , "ID"); // the array list contains all transactions ID that retrieved from the SQL query
        ArrayList<String> NameResults =db.selectQuery(sql , p , "Name");
        ArrayList<String> AmountResults =db.selectQuery(sql , p , "Amount");
        ArrayList<String> TypeResults =db.selectQuery(sql , p , "Type");
        ArrayList<String>  DateResults=db.selectQuery(sql , p , "Date");
        ArrayList<String> CategoryResults =db.selectQuery(sql , p , "Category");

        System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n","Transaction ID","Transaction Name","Transaction Amount","Transaction Type","Transaction Date","Category");
        for(int i = 0 ; i < IDResults.size(); i++){
            System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n",IDResults.get(i),NameResults.get(i),AmountResults.get(i),TypeResults.get(i),DateResults.get(i),CategoryResults.get(i));
        }
    }
}
