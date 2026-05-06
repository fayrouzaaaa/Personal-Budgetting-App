import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Regular_User extends User {

    private String currency = "EGP";
    private final List<String> availableCurrencies =
            new ArrayList<>(List.of("EGP", "USD", "EUR", "SAR"));

    private final List<Transaction> transactions = new ArrayList<>();  //not sure
    private final Account account;
    public enum TransactionType {
        INCOME,
        EXPENSE
    }
    public Regular_User(int id, String name, String email, String password) {
        super(id, name, email, password);
        this.account = new Account();
    }

    //  ADD TRANSACTION

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

        Transaction t;
        LocalDate now = LocalDate.now();

        if (type == TransactionType.INCOME) {
            t = new income(amount);
            account.deposit(amount);
        }
        else
        {
            if (type == TransactionType.EXPENSE && amount > account.getBalance()) {
                System.out.println("Not enough balance!");
                return;
            }
            t = new Expense(amount);
            account.withdraw(amount);
        }


        t.save(name, now, amount, category);
        transactions.add(t); // not sure
        System.out.println(" Transaction added: " + name + " | " + amount + " " + currency);
    }

    // SET CURRENCY

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


    //  GET BALANCE
    public double getBalance() {
        return account.getBalance();
    }



//    suggestion:
//  in dashboard
//    Balance: getBalance()
//   Transactions count: "

}