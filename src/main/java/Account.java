/**
 * Represents a bank account for a user, providing methods to manage balance,
 * deposit funds, and withdraw funds with database synchronization.
 */
public class Account {

    private double balance;
    private final Database db = new Database();

    /**
     * Constructs an Account object by fetching the current balance from the database.
     *
     * @param userID The unique identifier of the user whose account is being accessed.
     */
    Account(int userID) {
        String sql = "SELECT BALANCE FROM USERS WHERE ID = ?";
        String[] params = {String.valueOf(userID)};
        String balanceValue = (db.selectQuery(sql, params, "Balance")).get(0);
        balance = Double.valueOf(balanceValue);
    }

    /**
     * Sets the initial balance for a user and updates it in the database.
     *
     * @param userId  The ID of the user.
     * @param balance The initial balance amount to set (must be non-negative).
     */
    public void setInitialBalance(int userId, double balance) {
        if (balance < 0) {
            System.out.println("Invalid balance");
            return;
        }

        this.balance = balance;

        String sql = "UPDATE Users SET Balance = ? WHERE ID = ?";
        String[] params = {
                String.valueOf(balance),
                String.valueOf(userId)
        };

        db.updateQuery(sql, params);
        System.out.println("Initial balance saved!");
    }

    /**
     * Retrieves the current local balance of the account.
     *
     * @return The current balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Deposits a specific amount into the account and synchronizes with the database.
     *
     * @param userId The ID of the user performing the deposit.
     * @param amount The amount to be added to the current balance.
     */
    public void deposit(int userId, double amount) {
        balance += amount;

        String sql = "UPDATE Users SET Balance = ? WHERE ID = ?";
        String[] params = {
                String.valueOf(balance),
                String.valueOf(userId)
        };

        db.updateQuery(sql, params);
    }

    /**
     * Withdraws a specific amount from the account and synchronizes with the database.
     *
     * @param userId The ID of the user performing the withdrawal.
     * @param amount The amount to be subtracted from the current balance.
     */
    public void withdraw(int userId, double amount) {
        balance -= amount;

        String sql = "UPDATE Users SET Balance = ? WHERE ID= ?";
        String[] params = {
                String.valueOf(balance),
                String.valueOf(userId)
        };

        db.updateQuery(sql, params);
    }
}