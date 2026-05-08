import java.util.ArrayList;

public class Budget_Item {
    private double limitAmount;
    private double spentAmount;
    private Category category;
    private Budget budget;
    private Database db;  // non-static instance

    public Budget_Item(){
        this.db = new Database();
    }

    public Budget_Item(Category category, double limitAmount) {
        this.category = category;
        this.limitAmount = limitAmount;
        this.spentAmount = 0.0;
        this.db = new Database();
    }

    public Budget_Item(Category category, double limitAmount, double spentAmount) {
        this.category = category;
        this.limitAmount = limitAmount;
        this.spentAmount = spentAmount;
        this.db = new Database();
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public Category getCategory() {
        return category;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public double getRemaining() {
        return limitAmount - spentAmount;
    }

    public boolean isOverLimit() {
        return spentAmount > limitAmount;
    }

    public void limitRemaining() {
        System.out.println("Remaining limit: " + getRemaining());
    }

    public ArrayList<String> getItemCategory(int userID){
        String sql = "select Categories.name as Name from Categories, Budget_Items,Budgets " +
                "where Budgets.user_id=? AND Budgets.budget_id=Budget_Items.budget_id AND " +
                "Categories.category_id=Budget_Items.category_id";
        String[] p = {String.valueOf(userID)};
        ArrayList<String> fetch = db.selectQuery(sql, p, "Name");

        return fetch;
    }

    public ArrayList<String> getItemSpent(int userID){
        String sql = "select spent_amount from Budget_Items, Budgets where Budgets.user_id = ? and Budgets.budget_id = Budget_Items.budget_id";
        String[] p = {String.valueOf(userID)};
        ArrayList<String> fetch = db.selectQuery(sql, p, "spent_amount");

        return fetch;
    }


    public ArrayList<String> getItemLimit(int userID){
        String sql = "select limit_amount from Budget_Items, Budgets where Budgets.user_id = ? and Budgets.budget_id = Budget_Items.budget_id";
        String[] p = {String.valueOf(userID)};
        ArrayList<String> fetch = db.selectQuery(sql, p, "limit_amount");

        return fetch;
    }

    
    // Database methods (non-static)
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
