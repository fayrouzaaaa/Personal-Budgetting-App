import java.util.ArrayList;
import java.util.List;

public class Budget {
    private int budgetId;
    private String month;
    private List<Budget_Item> budgetItems;
    private Database db= new Database();  // non-static instance

    public Budget(int budgetId, String month , int userID) {
        this.budgetId = budgetId;
        this.month = month;
        this.budgetItems = new ArrayList<>();
    }

    public int getBudgetId(int userID) {
        String sql = "SELECT budget_id FROM Budgets WHERE user_id = ?";
        String[] p = {Integer.toString(userID)};
        ArrayList<String> result = db.selectQuery(sql , p , "budget_id");
        return Integer.parseInt(result.getFirst());
    }

    public String getMonth(int userID , int budgetID) {
        String sql = "SELECT month FROM Budgets WHERE user_id = ? AND budget_id = ?";
        String[] p = {Integer.toString(userID), Integer.toString(budgetID)};
        ArrayList<String> result = db.selectQuery(sql , p , "month");
        return result.getFirst();
    }

    public List<Budget_Item> getBudgetItems() {
        return budgetItems;
    }

    public void addBudgetItem(Budget_Item item) {
        budgetItems.add(item);
        item.setBudget(this);
    }

    public void removeBudgetItem(Category category) {
        budgetItems.removeIf(item -> item.getCategory().getCategoryId() == category.getCategoryId());
    }

    public Budget_Item getBudgetItemByCategory(Category category) {
        for (Budget_Item item : budgetItems) {
            if (item.getCategory().getCategoryId() == category.getCategoryId()) {
                return item;
            }
        }
        return null;
    }

    public void createBudget() {
        System.out.println("Budget created for " + month + " with " + budgetItems.size() + " items");
        for (Budget_Item item : budgetItems) {
            System.out.println("  - " + item);
        }
    }

    public boolean checkLimit(Category category, double amount) {
        Budget_Item item = getBudgetItemByCategory(category);
        if (item == null) {
            return false;
        }
        return (item.getSpentAmount() + amount) > item.getLimitAmount();
    }

    public boolean isCategoryOverLimit(Category category) {
        Budget_Item item = getBudgetItemByCategory(category);
        if (item != null) {
            return item.isOverLimit();
        }
        return false;
    }


    // Database methods (non-static)
    public void saveToDatabase(int userId) {
        String budgetSql = "INSERT INTO Budgets (month, user_id) VALUES (?, ?)";
        String[] budgetParams = { this.month, String.valueOf(userId) };
        db.updateQuery(budgetSql, budgetParams);
        System.out.println("Budget saved: " + this.month);
    }

    public void updateSpentInDatabase(int categoryId, double amount) {
        String sql = "UPDATE Budget_Items SET spent_amount = spent_amount + ? WHERE budget_id = ? AND category_id = ?";
        String[] params = {
                String.valueOf(amount),
                String.valueOf(this.budgetId),
                String.valueOf(categoryId)
        };
        db.updateQuery(sql, params);
        System.out.println("Spent updated: +" + amount);
    }
}
