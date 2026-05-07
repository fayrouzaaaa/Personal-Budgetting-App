public class Account {

    private double balance;
    private final Database db = new Database();

        Account(int userID){
            String sql = "SELECT BALANCE FROM USERS WHERE ID = ?";
            String[] params = {String.valueOf(userID)};
            String balanceValue = (db.selectQuery(sql, params, "Balance")).get(0);
            balance = Double.valueOf(balanceValue);
        }
        public void setInitialBalance(int userId, double balance) {

            if (balance < 0) {
                System.out.println("Invalid balance");
                return;
            }

            this.balance = balance;

            String sql =
                    "UPDATE Users SET Balance = ? WHERE ID = ?";

            String[] params = {
                    String.valueOf(balance),
                    String.valueOf(userId)
            };

            db.updateQuery(sql, params);

            System.out.println("Initial balance saved!");
        }

        public double getBalance() {
            return balance;
        }

    public void deposit(int userId, double amount) {

        balance += amount;

        String sql =
                "UPDATE Users SET Balance = ? WHERE ID = ?";

        String[] params = {
                String.valueOf(balance),
                String.valueOf(userId)
        };

        db.updateQuery(sql, params);
    }

    public void withdraw(int userId, double amount) {

        balance -= amount;

        String sql =
                "UPDATE Users SET Balance = ? WHERE ID= ?";

        String[] params = {
                String.valueOf(balance),
                String.valueOf(userId)
        };

        db.updateQuery(sql, params);
    }


}