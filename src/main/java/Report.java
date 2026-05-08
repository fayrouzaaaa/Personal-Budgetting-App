import java.time.*;
import java.util.ArrayList;


public class Report {
 	private String month;
 	private int reportId;
	 private Database db = new Database();
 	public Transaction t = new Transaction();

 	public void generateReport(String m , int userID) {

		 int month;
		 if(m.length() <= 2){ //if user inputs a number instead of the name of the month
             month= Integer.parseInt(m);
		 }
		 else { //if it  is string convert it to integer by enum called Month
			 month = Month.valueOf(m.toUpperCase()).getValue(); // it is a must to convert the string the upper case to be recognizable in the Month
		 }
		 LocalDate startDate = LocalDate.of(2026 , month , 1);
		 int lastDayOfTheMonth = startDate.lengthOfMonth();
		 LocalDate endDate= LocalDate.of(2026 , month , lastDayOfTheMonth);
		 t.getTransactionByDate(userID,startDate , endDate);

		// get current balance which means saving amount
		String sqlForBalance = "SELECT Balance FROM Users WHERE ID = ?";
		String[] pForBalance = {Integer.toString(userID)};
		ArrayList<String> BalanceResults = db.selectQuery(sqlForBalance , pForBalance , "Balance");
		//calculate total income
		String sqlForTotalIncome = "SELECT SUM(Amount) AS TotalIncome FROM Transactions WHERE Type = ? AND User_ID=? AND Date BETWEEN ? and ?";
		String[] pForTotalIncome = {"Income" , Integer.toString(userID) , startDate.toString() , endDate.toString()};
		ArrayList<String> TotalIncome =db.selectQuery(sqlForTotalIncome , pForTotalIncome, "TotalIncome");
        //calculate expense amounts
		String sqlForTotalExpense= "SELECT SUM(Amount) AS TotalExpense FROM Transactions WHERE Type =? AND User_ID=? AND Date BETWEEN ? and ?";
		String[] pForTotalExpense = {"Expense" , Integer.toString(userID) , startDate.toString() , endDate.toString()};
		ArrayList<String> TotalExpense =db.selectQuery(sqlForTotalExpense ,pForTotalExpense , "TotalExpense");
		System.out.println("your saving amounts: "+BalanceResults.getFirst());
		System.out.println("Total income this month: " + TotalIncome.getFirst());
		System.out.println("Total Expense in this month: "+TotalExpense.getFirst() );

 	}

 	public void generateCharts() {
 		throw new UnsupportedOperationException();
 	}
 }