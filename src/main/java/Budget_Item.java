import java.util.ArrayList;

/**
 * Represents a specific line item within a budget, associated with a category.
 * * <p>The Budget_Item class tracks the spending limit and the current amount spent
 * for a particular category. It provides methods to calculate remaining funds,
 * check for over-limit status, and perform database operations to persist
 * and update budget details.</p>
 */
public class Budget_Item {
    private double limitAmount;
    private double spentAmount;
    private Category category;
    private Budget budget;
    private Database db;

    /**
     * Default constructor for Budget_Item.
     * Initializes a new database instance.
     */
    public Budget_Item(){
        this.db = new Database();
    }

    /**
     * Constructs a Budget_Item with a specific category and limit.
     * Initial spent amount is set to 0.0.
     * * @param category    The {@link Category} associated with this budget item.
     * @param limitAmount The maximum spending limit for this category.
     */
    public Budget_Item(Category category, double limitAmount) {
        this.category = category;
        this.limitAmount = limitAmount;
        this.spentAmount = 0.0;
        this.db = new Database();
    }

    /**
     * Constructs a Budget_Item with a specific category, limit, and current spent amount.
     * * @param category    The {@link Category} associated with this budget item.
     * @param limitAmount The maximum spending limit for this category.
     * @param spentAmount The current amount already spent in this category.
     */
    public Budget_Item(Category category, double limitAmount, double spentAmount) {
        this.category = category;
        this.limitAmount = limitAmount;
        this.spentAmount = spentAmount;
        this.db = new Database();
    }

    /**
     * @return The maximum limit allowed for this budget item.
     */
    public double getLimitAmount() {
        return limitAmount;
    }

    /**
     * @return The total amount currently spent in this category.
     */
    public double getSpentAmount() {
        return spentAmount;
    }

    /**
     * @return The {@link Category} linked to this item.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @return The parent {@link Budget} this item belongs to.
     */
    public Budget getBudget() {
        return budget;
    }

    /**
     * @param limitAmount The new limit to be set for this budget item.
     */
    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }

    /**
     * Links this item to a specific parent budget.
     * @param budget The {@link Budget} object.
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    /**
     * Calculates the remaining funds before reaching the limit.
     * @return The difference between limitAmount and spentAmount.
     */
    public double getRemaining() {
        return limitAmount - spentAmount;
    }

    /**
     * Determines if the current spending has exceeded the set limit.
     * @return {@code true} if spentAmount is greater than limitAmount; otherwise {@code false}.
     */
    public boolean isOverLimit() {
        return spentAmount > limitAmount;
    }

    /**
     * Prints the remaining budget limit to the standard output.
     */
    public void limitRemaining() {
        System.out.println("Remaining limit: " + getRemaining());
    }

    /**
     * Retrieves all category names associated with a user's budget items from the database.
     * * @param userID The ID of the user.
     * @return An {@link ArrayList} of category names.
     */
    public ArrayList<String> getItemCategory(int userID){
        String sql = "select Categories.name as Name from Categories, Budget_Items,Budgets " +
                "where Budgets.user_id=? AND Budgets.budget_id=Budget_Items.budget_id AND " +
                "Categories.category_id=Budget_Items.category_id";
        String[] p = {String.valueOf(userID)};
        return db.selectQuery(sql, p, "Name");
    }

    /**
     * Retrieves all spent amounts for a user's budget items from the database.
     * * @param userID The ID of the user.
     * @return An {@link ArrayList} of strings representing spent amounts.
     */
    public ArrayList<String> getItemSpent(int userID){
        String sql = "select spent_amount from Budget_Items, Budgets where Budgets.user_id = ? and Budgets.budget_id = Budget_Items.budget_id";
        String[] p = {String.valueOf(userID)};
        return db.selectQuery(sql, p, "spent_amount");
    }

    /**
     * Retrieves all limit amounts for a user's budget items from the database.
     * * @param userID The ID of the user.
     * @return An {@link ArrayList} of strings representing limit amounts.
     */
    public ArrayList<String> getItemLimit(int userID){
        String sql = "select limit_amount from Budget_Items, Budgets where Budgets.user_id = ? and Budgets.budget_id = Budget_Items.budget_id";
        String[] p = {String.valueOf(userID)};
        return db.selectQuery(sql, p, "limit_amount");
    }

    /**
     * Inserts the current budget item record into the database.
     * * @param budgetId The ID of the parent budget to link this item to.
     */
    public void saveToDatabase(int budgetId) {
        String sql = "INSERT INTO Budget_Items (budget_id, category_id, limit_amount, spent_amount) VALUES (?, ?, ?, ?)";
        String[] params = {
                String.valueOf(budgetId),
                String.valueOf(this.category.getCategoryId()),
                String.valueOf(this.limitAmount),
                String.valueOf(this.spentAmount)
        };
        db.updateQuery(sql, params);
        System.out.println("Budget Item saved for: " + this.category.getName());
    }

    /**
     * Updates the spent amount in the database for this specific item and category.
     * * @param budgetId         The parent budget ID.
     * @param additionalAmount The amount to be added to the current spent total.
     */
    public void updateSpentInDatabase(int budgetId, double additionalAmount) {
        String sql = "UPDATE Budget_Items SET spent_amount = spent_amount + ? WHERE budget_id = ? AND category_id = ?";
        String[] params = {
                String.valueOf(additionalAmount),
                String.valueOf(budgetId),
                String.valueOf(this.category.getCategoryId())
        };
        db.updateQuery(sql, params);
        this.spentAmount += additionalAmount;
    }
}