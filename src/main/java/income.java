import java.time.LocalDate;

public class income extends Transaction {
    private String source;
    private double balance;
    income(double b){
        this.balance = b;
    }
    @Override
    public void save(String Name, LocalDate Date, double aAmount, Category aCategory) {
        this.balance -= aAmount;
        String sql ="INSERT INTO Transactions VALUES (? , ?, ?, ? ,?, ?) ";
        String[] p = {Name , Double.toString(aAmount) , Integer.toString(1) /*replace this with user-id*/, "income" , Date.toString() , aCategory.getName()};
        db.updateQuery(sql , p);

    }
}