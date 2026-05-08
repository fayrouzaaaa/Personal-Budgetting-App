import java.util.ArrayList;
import java.util.List;

/**
 * Represents a monthly financial budget for a user, containing multiple budget items.
 * * <p>The Budget class manages a collection of {@link Budget_Item} objects and provides
 * methods to calculate total limits, tracked spent amounts, and synchronize
 * data with the persistent database.</p>
 *
 */
public class Budget {
    private int budgetId;
    private String month;
    private List<Budget_Item> budgetItems;
    private Database db = new Database();

    /**
     * Default constructor for the Budget class.
     */
    public Budget() {}

    /**
     * Constructs a new Budget with a specific ID and month.
     * * @param budgetId The unique identifier for this budget.
     * @param month    The month associated with this budget (e.g., "JAN", "FEB").
     * @param userID   The ID of the user owning this budget.
     */
    public Budget(int budgetId, String month, int userID) {
        this.budgetId = budgetId;
        this.month = month;
        this.budgetItems = new ArrayList<>();
    }

    /**
     * Retrieves the primary budget ID for a specific user from the database.
     * * @param userID The unique identifier of the user.
     * @return The budget_id found in the database.
     */
    public int getBudgetId(int userID) {
        String sql = "SELECT budget_id FROM Budgets WHERE user_id = ?";
        String[] p = {Integer.toString(userID)};
        ArrayList<String> result = db.selectQuery(sql, p, "budget_id");
        return Integer.parseInt(result.getFirst());
    }

    /**
     * Retrieves the month associated with a specific budget from the database.
     * * @param userID   The user's ID.
     * @param budgetID The budget's ID.
     * @return The month string stored in the database.
     */
    public String getMonth(int userID, int budgetID) {
        String sql = "SELECT month FROM Budgets WHERE user_id = ? AND budget_id = ?";
        String[] p = {Integer.toString(userID), Integer.toString(budgetID)};
        ArrayList<String> result = db.selectQuery(sql, p, "month");
        return result.getFirst();
    }

    /**
     * Gets the list of budget items associated with this budget.
     * * @return A list of {@link Budget_Item} objects.
     */
    public List<Budget_Item> getBudgetItems() {
        return budgetItems;
    }

    /**
     * Adds a new item to the budget and sets the back-reference to this budget.
     * * @param item The {@link Budget_Item} to add.
     */
    public void addBudgetItem(Budget_Item item) {
        budgetItems.add(item);
        item.setBudget(this);
    }

    /**
     * Removes a budget item based on its category.
     * * @param category The category of the item to be removed.
     */
    public void removeBudgetItem(Category category) {
        budgetItems.removeIf(item -> item.getCategory().getCategoryId() == category.getCategoryId());
    }

    /**
     * Searches for a specific budget item within this budget by category.
     * * @param category The category to search for.
     * @return The matching {@link Budget_Item}, or {@code null} if not found.
     */
    public Budget_Item getBudgetItemByCategory(Category category) {
        for (Budget_Item item : budgetItems) {
            if (item.getCategory().getCategoryId() == category.getCategoryId()) {
                return item;
            }
        }
        return null;
    }

    /**
     * Prints a summary of the current budget and its items to the console.
     */
    public void createBudget() {
        System.out.println("Budget created for " + month + " with " + budgetItems.size() + " items");
        for (Budget_Item item : budgetItems) {
            System.out.println("  - " + item);
        }
    }

    /**
     * Checks if adding a specific amount would exceed the limit for a given category.
     * * @param category The category to check.
     * @param amount   The amount to simulate adding.
     * @return {@code true} if the limit is exceeded; {@code false} otherwise.
     */
    public boolean checkLimit(Category category, double amount) {
        Budget_Item item = getBudgetItemByCategory(category);
        if (item == null) {
            return false;
        }
        return (item.getSpentAmount() + amount) > item.getLimitAmount();
    }

    /**
     * Checks if a category has already exceeded its defined limit.
     * * @param category The category to evaluate.
     * @return {@code true} if spent amount > limit amount; {@code false} otherwise.
     */
    public boolean isCategoryOverLimit(Category category) {
        Budget_Item item = getBudgetItemByCategory(category);
        if (item != null) {
            return item.isOverLimit();
        }
        return false;
    }

    /**
     * Calculates the total sum of all budget limits for a specific user.
     * * @param userID The user's ID.
     * @return The sum of all {@code limit_amount} values.
     */
    public double getBudgetSum(int userID){
        String sql = "SELECT SUM(limit_amount) as SUM from Budget_Items, Budgets " +
                "where Budgets.user_id = ? and Budgets.budget_id = Budget_Items.budget_id";
        String[] p = {String.valueOf(userID)};
        ArrayList<String> fetch = db.selectQuery(sql, p, "SUM");

        return Double.valueOf(fetch.get(0));
    }

    /**
     * Calculates the total sum of spent amounts across all budget items for a user.
     * * @param userID The user's ID.
     * @return The sum of all {@code spent_amount} values.
     */
    public double getSpentSum(int userID){
        String sql = "SELECT SUM(spent_amount) as SUM from Budget_Items, Budgets " +
                "where Budgets.user_id = ? and Budgets.budget_id = Budget_Items.budget_id";
        String[] p = {String.valueOf(userID)};
        ArrayList<String> fetch = db.selectQuery(sql, p, "SUM");

        return Double.valueOf(fetch.get(0));
    }

    /**
     * Saves the current budget header (month and user_id) to the database.
     * * @param userId The ID of the user owning this budget.
     */
    public void saveToDatabase(int userId) {
        String budgetSql = "INSERT INTO Budgets (month, user_id) VALUES (?, ?)";
        String[] budgetParams = { this.month, String.valueOf(userId) };
        db.updateQuery(budgetSql, budgetParams);
        System.out.println("Budget saved: " + this.month);
    }

    /**
     * Updates the spent amount for a specific category within this budget in the database.
     * * @param categoryId The ID of the category.
     * @param amount     The amount to add to the existing spent value.
     */
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