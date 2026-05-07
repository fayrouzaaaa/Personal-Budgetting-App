import java.util.*;
import java.time.*;
public class Transaction {
    private int transactionId ;
    protected double balance;
    private LocalDate date ;
    protected Database db = new Database();
    protected int user_ID;
    protected Account account  ;
    public Category category;


    public void save(int userId ,String Name , LocalDate Date ,double aAmount){};
    public void getTransactionByCategory(Category category){
        String sql = "SELECT * FROM Transactions WHERE Category = ?";
        String[] p = {category.getName()};
        displayTransaction(sql , p);
    }

    public void getTransactionByDate( int userID,LocalDate aStart, LocalDate aEnd)
    {
        String sql = "SELECT * FROM Transactions  WHERE Date BETWEEN ? AND ? AND Users.ID = ?"; // SQL query
        String[] p ={ aStart.toString() , aEnd.toString(),Integer.toString(userID)}; // convert dates to string
        displayTransaction(sql , p);

    }
    public void displayTransaction(String sql ,String[] p){
        ArrayList<String> IDResults =db.selectQuery(sql , p , "ID"); // the array list contains all transactions ID that retrieved from the SQL query
        ArrayList<String> NameResults =db.selectQuery(sql , p , "Name");
        ArrayList<String> AmountResults =db.selectQuery(sql , p , "Amount");
        ArrayList<String> TypeResults =db.selectQuery(sql , p , "Type");
        ArrayList<String>  DateResults=db.selectQuery(sql , p , "Date");
        ArrayList<String> CategoryResults =db.selectQuery(sql , p , "Category");
        ArrayList<String>  NoteResults = db.selectQuery(sql , p , "Notes");
        System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s %10s%n","Transaction ID","Transaction Name","Transaction Amount","Transaction Type","Transaction Date","Category" , "Notes");
        for(int i = 0 ; i < IDResults.size(); i++){
            System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s %-10s%n",IDResults.get(i),NameResults.get(i),AmountResults.get(i),TypeResults.get(i),DateResults.get(i),CategoryResults.get(i) , NoteResults.get(i));
        }
    }
}
