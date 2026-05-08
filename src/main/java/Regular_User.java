import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Regular_User extends User {

    private String currency = "EGP";
    private final List<String> availableCurrencies =
            new ArrayList<>(List.of("EGP", "USD", "EUR", "SAR"));

    private final List<Transaction> transactions = new ArrayList<>();
    private final Account account;

    public enum TransactionType {
        INCOME,
        EXPENSE
    }

    public Regular_User(int id, String name, String email, String password) {
        super(id, name, email, password);
        this.account = new Account(id);
    }

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

    public void setInitialBalance(double amount) {
        account.setInitialBalance(getUserId(), amount);
    }

    public double getBalance() {
        return account.getBalance();
    }

    public String getCurrency() {
        return currency;
    }
}