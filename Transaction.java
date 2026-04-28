import java.util.ArrayList;
import java.util.Date;
public class Transaction {
	private int transactionId = 0 ;
	private double amount = 0;
	private Date date = new Date();
	private static int count = 0;
	public Report report;
	public Regular_User regularUser ;
	public Category category;

	public void save(Transaction aType, double aAmount, Category aCategory)
	{
		this.category = aCategory;
		this.amount = aAmount;
		this.date = new Date();
		this.transactionId = count ++;

	}

	public void getTransactions(Date aStart, Date aEnd)
	{
		while(this.date.after(aStart) && this.date.before(aEnd)){
            System.out.println("");
		}

	}
	public void displayTransaction(){
		System.out.println("Transaction ID: "+ this.transactionId);
		System.out.println("Transaction Amount: "+this.amount);
		System.out.println("Transaction to the category: "+this.category.getName());
		System.out.println("Transaction date: "+this.date);
	}
}
