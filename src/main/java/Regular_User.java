import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a standard user of the application with personal financial management capabilities.
 * * <p>The Regular_User class extends {@link User} and provides functionality to manage
 * a personal {@link Account}, track a list of {@link Transaction} objects, and
 * configure display preferences such as currency. It includes business logic for
 * processing incomes and expenses while maintaining account balance integrity.</p>

 */
public class Regular_User extends User {

    /** The current display currency for the user's financial data. Defaults to "EGP". */
    private String currency = "EGP";

    /** A list of ISO currency codes currently supported by the system. */
    private final List<String> availableCurrencies =
            new ArrayList<>(List.of("EGP", "USD", "EUR", "SAR"));

    /** Internal history of transactions performed during the current session. */
    private final List<Transaction> transactions = new ArrayList<>();

    /** The financial account associated with this user, used for balance tracking. */
    private final Account account;

    /**
     * Enumeration representing the possible classifications of a transaction.
     */
    public enum TransactionType {
        /** Represents funds added to the account. */
        INCOME,
        /** Represents funds deducted from the account. */
        EXPENSE
    }

    /**
     * Constructs a Regular_User and initializes their associated financial account.
     * * @param id       The unique identifier for the user.
     * @param name     The full name of the user.
     * @param email    The user's registered email address.
     * @param password The user's account password.
     */
    public Regular_User(int id, String name, String email, String password) {
        super(id, name, email, password);
        this.account = new Account(id);
    }

    /**
     * Creates, processes, and persists a new transaction for the user.
     * * <p>This method performs several validation checks:
     * <ul>
     * <li>Ensures the amount is positive.</li>
     * <li>Verifies that transaction name and category are provided.</li>
     * <li>For expenses, verifies that the account has sufficient funds.</li>
     * </ul>
     * Successful transactions result in a database update and a balance adjustment
     * within the {@link Account} object.</p>
     * * @param type     The {@link TransactionType} (INCOME or EXPENSE).
     * @param name     A descriptive name for the transaction.
     * @param amount   The monetary value of the transaction.
     * @param category The {@link Category} classification for this transaction.
     */
    public void addTransaction(TransactionType type, String name, double amount, Category category) {
        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        if (name == null || name.isBlank()) {
            System.out.println("Transaction name is required!");
            return;
        }

        if (category == null) {
            System.out.println("Category is required!");
            return;
        }

        LocalDate now = LocalDate.now();

        if (type == TransactionType.INCOME) {
            income in = new income(amount);
            account.deposit(getUserId(), amount);
            in.save(getUserId(), name, now, amount, "Income", category.getName());
            transactions.add(in);
        } else {
            if (amount > account.getBalance()) {
                System.out.println("Not enough balance!");
                return;
            }
            Expense expense = new Expense(amount);
            account.withdraw(getUserId(), amount);
            expense.save(getUserId(), name, now, amount, "Expense", category.getName());
            transactions.add(expense);
        }

        System.out.println(" Transaction added: " + name + " | " + amount + " " + currency);
    }

    /**
     * Updates the user's preferred currency for display purposes.
     * * @param newCurrency The ISO currency code (e.g., "USD", "EUR").
     */
    public void setCurrency(String newCurrency) {
        if (newCurrency == null) {
            System.out.println("Currency cannot be null");
            return;
        }

        newCurrency = newCurrency.toUpperCase();

        if (availableCurrencies.contains(newCurrency)) {
            this.currency = newCurrency;
            System.out.println(" Currency changed to: " + newCurrency);
        } else {
            System.out.println(" Currency not supported");
        }
    }

    /**
     * Sets the starting balance for the user's account in the database.
     * * @param amount The initial monetary amount.
     */
    public void setInitialBalance(double amount) {
        account.setInitialBalance(getUserId(), amount);
    }

    /**
     * Retrieves the current available balance from the user's account.
     * * @return The total current balance as a {@code double}.
     */
    public double getBalance() {
        return account.getBalance();
    }

    /**
     * @return The ISO code of the currency currently selected by the user.
     */
    public String getCurrency() {
        return currency;
    }
}