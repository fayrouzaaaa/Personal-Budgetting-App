import java.util.*;
import java.time.*;
public abstract class Transaction {
	private int transactionId ;
	private double amount ;
	private LocalDate date ;
	protected Database db = new Database();
	public Report report;
	public Regular_User regularUser ;
	public Category category;

	public abstract void save(String Name , LocalDate Date ,double aAmount, Category aCategory);


	public void getTransactions(LocalDate aStart, LocalDate aEnd)
	{
		String sql = "SELECT * FROM Transactions , Users WHERE Date BETWEEN ? AND ? AND Users.ID = Transactions.User_ID"; // SQL query
		String[] p ={ aStart.toString() , aEnd.toString()}; // convert dates to string
		displayTransaction(sql , p);

	}
	public void displayTransaction(String sql ,String[] p){
		ArrayList<String> IDResults =db.selectQuery(sql , p , "ID"); // the array list contain all transactions ID that retrieved from the SQL query
		ArrayList<String> NameResults =db.selectQuery(sql , p , "Name");
		ArrayList<String> AmountResults =db.selectQuery(sql , p , "Amount");
		ArrayList<String> TypeResults =db.selectQuery(sql , p , "Type");
		ArrayList<String>  DateResults=db.selectQuery(sql , p , "Date"); // the array list contain all transactions that retrieved from the SQL query
		ArrayList<String> CategoryResults =db.selectQuery(sql , p , "Category"); // the array list contain all transactions that retrieved from the SQL query

		System.out.println("Transaction ID\tTransaction Name\tTransaction Amount\tTransaction Type\tTransaction Date\tCategory");
		for(int i = 0 ; i < IDResults.size(); i++){
			System.out.println(IDResults.get(i)+"\t\t\t\t\t"+NameResults.get(i)+ "\t\t\t\t"+AmountResults.get(i)+"\t\t\t"+TypeResults.get(i)+"\t\t\t"+DateResults.get(i)+"\t\t\t"+CategoryResults.get(i));
		}
	}
}
