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
        String sql = "SELECT * FROM Transactions  WHERE Date BETWEEN ? AND ? AND User_ID = ?"; // SQL query
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

        if(IDResults.isEmpty()){
            System.out.println("No Transactions!");
        }
        else {
            System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n", "Transaction ID", "Transaction Name", "Transaction Amount", "Transaction Type", "Transaction Date", "Category");
            for (int i = 0; i < IDResults.size(); i++) {
                System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n", IDResults.get(i), NameResults.get(i), AmountResults.get(i), TypeResults.get(i), DateResults.get(i), CategoryResults.get(i));
            }
        }
    }
    public void showTransaction(int userID){
        String sql = "SELECT * FROM Transactions WHERE User_ID = ?";
        String[] p = {Integer.toString(userID)};
        displayTransaction(sql , p);
    }
    public  void displayRecentTransactions(int userID){ // use it in transaction page
        String sqlForLatestTransactions = "select distinct Date from Transactions where User_ID = ? order by Date desc";
        String[] p = {Integer.toString(userID)};
        ArrayList<String> DateResults =db.selectQuery(sqlForLatestTransactions , p , "Date"); // first two cells is the most recent

        if(DateResults.isEmpty())
        {
            System.out.println("No recent Transactions!");
        }
        else
        {
            //get today date and yesterday date to display "Today" or "Yesterday" if the recent transactions was today or yesterday
        LocalDate todayDate = LocalDate.now();
        int yesterday =todayDate.getDayOfMonth() -1 ;
        int month = todayDate.getMonthValue();
        int year = todayDate.getYear();
        LocalDate yesterdayDate = LocalDate.of(year , month , yesterday);

        if(DateResults.getFirst().equals(todayDate.toString())){
            System.out.println("Today:");
        }
        else{
            System.out.println(DateResults.getFirst()+":");
        }
        getTransactionByDate(userID , LocalDate.parse(DateResults.getFirst()) , LocalDate.parse(DateResults.getFirst()));

        if(DateResults.size() != 1) {

            if (DateResults.get(1).equals(yesterdayDate.toString())) {
                System.out.println("Yesterday:");
            } else {
                System.out.println(DateResults.get(1) + ":");
            }
            getTransactionByDate(userID, LocalDate.parse(DateResults.get(1)), LocalDate.parse(DateResults.get(1)));
        }
        }
    }


}
