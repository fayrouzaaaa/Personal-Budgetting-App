
	import java.time.LocalDate;
    import java.util.ArrayList;

    public class Expense extends Transaction {
        private String notes;
        private double expenseAmount;
        public Category category;
        Expense(double e){
            this.expenseAmount = e;
        }

        public void save( int userID ,String Name, LocalDate Date, double aAmount, Category category) {
            this.expenseAmount += aAmount;
            this.balance -=aAmount;
            this.account = new Account(userID);
            account.withdraw(userID , aAmount);
            this.category = category;
            String sql ="INSERT INTO Transactions (Name , Amount , User_ID , Type , Date , Category ) VALUES (? , ?, ?, ? ,?, ? ) ";
            String[] p = {Name , Double.toString(aAmount) , Integer.toString(userID), "Expense" , Date.toString() , category.getName()};
            db.updateQuery(sql , p);
        }
        @Override
        public void getTransactionByCategory(Category category){
            String sql = "SELECT * FROM Transactions WHERE Category = ? AND Type =?";
            String[] p = {category.getName() , "Expense"};
            displayTransaction(sql , p);
        }

        @Override
       public void getTransactionByDate(int userID ,LocalDate aStart, LocalDate aEnd)
        {
            String sql = "SELECT * FROM Transactions WHERE Date BETWEEN ? AND ? AND User_ID =? AND Type = ?"; // SQL query
            String[] p ={ aStart.toString() , aEnd.toString() ,Integer.toString(userID), "Expense"}; // convert all values to String
            displayTransaction(sql , p);
        }



    }
