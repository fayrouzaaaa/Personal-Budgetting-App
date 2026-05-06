import java.time.LocalDate;

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
}