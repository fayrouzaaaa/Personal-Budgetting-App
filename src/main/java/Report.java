import java.time.LocalDate;
import java.time.Month;

public class Report {
 	private String month;
 	private int _reportId;
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
		 t.getTransactions(startDate , endDate);


 	}

 	public void generateCharts() {
 		throw new UnsupportedOperationException();
 	}
 }