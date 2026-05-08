import java.time.LocalDate;
import java.util.ArrayList;

public class income extends Transaction {
    private String source;


    public  income(double b){
        this.balance = b;
    }

    public void save(int userID ,String Name, String source,LocalDate Date, double aAmount) {
        this.balance += aAmount;
        this.account = new Account(userID);
        account.deposit(userID , aAmount);
        this.source = source;
        String sql ="INSERT INTO Transactions(Name, Amount , User_ID ,Type , Date , Category) VALUES (? , ?, ?, ? ,?, ?) ";
        String[] p = {Name , Double.toString(aAmount) , Integer.toString(userID), "Income" , Date.toString() ,source };
        db.updateQuery(sql , p);
    }
    @Override
    public void getTransactionByCategory(Category category){
        String sql = "SELECT * FROM Transactions WHERE Category = ? and Type = ?";
        String[] p = {category.getName() , "Income"};
        displayTransaction(sql , p);
    }
    @Override
    public void getTransactionByDate(int userID ,LocalDate aStart, LocalDate aEnd)
    {
        String sql = "SELECT * FROM Transactions  WHERE Date BETWEEN ? AND ? AND User_ID = ? AND Type = ?"; // SQL query
        String[] p ={ aStart.toString() , aEnd.toString(),Integer.toString(userID) , "income"}; // convert dates to string
        displayTransaction(sql , p);
    }
    @Override
    public void displayTransaction(String sql , String[] p){
        ArrayList<String> IDResults =db.selectQuery(sql , p , "ID"); // the array list contains all transactions ID that retrieved from the SQL query
        ArrayList<String> NameResults =db.selectQuery(sql , p , "Name");
        ArrayList<String> AmountResults =db.selectQuery(sql , p , "Amount");
        ArrayList<String>  DateResults=db.selectQuery(sql , p , "Date");
        ArrayList<String> CategoryResults =db.selectQuery(sql , p , "Category");

        System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n","Transaction ID","Transaction Name","Transaction Amount","Transaction Type","Transaction Date","Category");
        for(int i = 0 ; i < IDResults.size(); i++){
            System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n",IDResults.get(i),NameResults.get(i),AmountResults.get(i),"Income",DateResults.get(i),CategoryResults.get(i));
        }
    }

}