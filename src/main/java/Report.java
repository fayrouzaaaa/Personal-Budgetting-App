import java.time.*;
import java.util.ArrayList;


public class Report {
 	private String month;
 	private int reportId;
	 private Database db = new Database();
 	public Transaction t = new Transaction();

     public double getBalance(int userID){
		 Account account = new Account(userID);
         return account.getBalance();
	 }
	 public String getTotalIncome(int userID , LocalDate startDate , LocalDate endDate){
		 String sqlForTotalIncome = "SELECT SUM(Amount) AS TotalIncome FROM Transactions WHERE Type = ? AND User_ID=? AND Date BETWEEN ? and ?";
		 String[] pForTotalIncome = {"Income" , Integer.toString(userID) , startDate.toString() , endDate.toString()};
		 ArrayList<String> TotalIncome =db.selectQuery(sqlForTotalIncome , pForTotalIncome, "TotalIncome");
		 return TotalIncome.getFirst();
	 }
	 public String getTotalExpenses(int userID , LocalDate startDate , LocalDate endDate){
		 String sqlForTotalExpense= "SELECT SUM(Amount) AS TotalExpense FROM Transactions WHERE Type =? AND User_ID=? AND Date BETWEEN ? and ?";
		 String[] pForTotalExpense = {"Expense" , Integer.toString(userID) , startDate.toString() , endDate.toString()};
		 ArrayList<String> TotalExpense =db.selectQuery(sqlForTotalExpense ,pForTotalExpense , "TotalExpense");
		 return TotalExpense.getFirst();
	 }
	public int getTransactionCount(int userID) {
		String sql = "Select Count(ID) AS COUNT FROM TRANSACTIONS WHERE User_ID=?";
		String[] p = {String.valueOf(userID)};
		ArrayList<String> fetch = db.selectQuery(sql, p, "COUNT");

		return Integer.valueOf(fetch.get(0));
	}

	public int getBudgetsCount(int userID){
		String sql = "Select Count(Budget_Items.id) AS COUNT FROM Budget_Items, Budgets " +
				"WHERE Budgets.user_id=? AND Budgets.budget_id=Budget_Items.budget_id";
		String[] p = {String.valueOf(userID)};
		ArrayList<String> fetch = db.selectQuery(sql, p, "COUNT");

		return Integer.valueOf(fetch.get(0));
	}

	public int getGoalsCount(int userID){
		String sql = "Select Count(goal_id) AS COUNT FROM Goals where user_id = ?";
		String[] p = {String.valueOf(userID)};
		ArrayList<String> fetch = db.selectQuery(sql, p, "COUNT");

		return Integer.valueOf(fetch.get(0));
	}
 	public ArrayList<String> generateReport(String m , int userID) {
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
		 t.getTransactionByDate(userID,startDate , endDate); //display transactions

		//get current balance
		String balance = String.valueOf(getBalance(userID));
		//calculate total income
		 String totalIncome = getTotalIncome(userID , startDate , endDate);
        //calculate expense amounts
		String totalExpense = getTotalExpenses(userID , startDate , endDate);

		double savings = Double.valueOf(totalIncome)-Double.valueOf(totalExpense);
		String savingAmount;
		if (savings>=0)
			savingAmount = String.valueOf(savings);
		else
			savingAmount = "0.0";
		System.out.println("your Balance: "+ balance);
		System.out.println("Total income this month: " + totalIncome);
		System.out.println("Total Expense in this month: "+totalExpense);
		System.out.println("Saving amounts: " + (Double.valueOf(totalIncome) > Double.valueOf(totalExpense) ? savings : 0));

		ArrayList<String> returnArray = new ArrayList<>();
		returnArray.add(balance);
		returnArray.add(totalIncome);
		returnArray.add(totalExpense);
		returnArray.add(savingAmount);
		return returnArray;

 	}

 	public void generateCharts(int userID , LocalDate startDate , LocalDate endDate) {
 		double balance = getBalance(userID);
		 String totalIncome = getTotalIncome(userID , startDate , endDate);
		 String totalExpense = getTotalExpenses(userID , startDate, endDate);
 	}
 }