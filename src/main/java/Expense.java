
	import java.time.LocalDate;
    import java.util.ArrayList;

    public class Expense extends Transaction {
        private String notes;
        private double expenseAmount;
        public Category category;
        Expense(double e){
            this.expenseAmount = e;
        }
        @Override
        public void save(String Name, LocalDate Date, double aAmount, Category aCategory) {
            this.expenseAmount += aAmount;
            String sql ="INSERT INTO Transactions VALUES (? , ?, ?, ? ,?, ?) ";
            String[] p = {Name , Double.toString(aAmount) , Integer.toString(1) /*replace this with user-id*/, "Expense" , Date.toString() , aCategory.getName()};
            db.updateQuery(sql , p);
        }
        @Override
public void getTransactions(LocalDate aStart, LocalDate aEnd)
{
    String sql = "SELECT * FROM Transactions , Users WHERE Date BETWEEN ? AND ? AND Users.ID = Transactions.User_ID AND Type = ?"; // SQL query
    String[] p ={ aStart.toString() , aEnd.toString() , "Expense"}; // convert dates to string
    displayTransaction(sql , p);

}
@Override
public void displayTransaction(String sql ,String[] p){
    ArrayList<String> IDResults =db.selectQuery(sql , p , "ID"); // the array list contains all transactions ID that retrieved from the SQL query
    ArrayList<String> NameResults =db.selectQuery(sql , p , "Name");
    ArrayList<String> AmountResults =db.selectQuery(sql , p , "Amount");
    ArrayList<String>  DateResults=db.selectQuery(sql , p , "Date");
    ArrayList<String> CategoryResults =db.selectQuery(sql , p , "Category");

    System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n","Transaction ID","Transaction Name","Transaction Amount","Transaction Type","Transaction Date","Category");
    for(int i = 0 ; i < IDResults.size(); i++){
        System.out.printf("%-15s %-25s %-20s %-20s %-20s %-20s%n",IDResults.get(i),NameResults.get(i),AmountResults.get(i),"Expense",DateResults.get(i),CategoryResults.get(i));
    }
}
    }
