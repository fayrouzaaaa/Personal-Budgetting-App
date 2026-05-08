import java.util.ArrayList;

/**
 * Represents a financial savings goal for a user (e.g., "New Car", "Emergency Fund").
 * * <p>The Goal class tracks a target monetary amount against a current saved amount.
 * It provides functionality to update progress, check for a goal's existence,
 * and synchronize target and current values with the database.</p>

 */
public class Goal {
    private int goalId;
    private double targetAmount;
    private double currentAmount;
    private String name;
    private Category category;
    private Database db = new Database();

    /**
     * Constructs a Goal with basic details and sets current amount to zero.
     * * @param goalId       The unique identifier for the goal.
     * @param name         The name/title of the goal.
     * @param targetAmount The total amount of money required to reach the goal.
     */
    public Goal(int goalId, String name, double targetAmount) {
        this(goalId, name, targetAmount, 0.0, null);
    }

    /**
     * Constructs a Goal linked to a specific category.
     * * @param goalId       The unique identifier for the goal.
     * @param name         The name/title of the goal.
     * @param targetAmount The total amount of money required to reach the goal.
     * @param category     The {@link Category} associated with this goal.
     */
    public Goal(int goalId, String name, double targetAmount, Category category) {
        this(goalId, name, targetAmount, 0.0, category);
    }

    /**
     * Constructs a Goal with all specific details, including current progress.
     * * @param goalId        The unique identifier for the goal.
     * @param name          The name/title of the goal.
     * @param targetAmount  The total amount of money required to reach the goal.
     * @param currentAmount The amount currently saved toward the goal.
     * @param category      The {@link Category} associated with this goal.
     */
    public Goal(int goalId, String name, double targetAmount, double currentAmount, Category category) {
        this.goalId = goalId;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.category = category;
    }

    /**
     * Retrieves the unique goal ID from the database for a specific user and category.
     * * @param userID     The ID of the user.
     * @param categoryID The ID of the category.
     * @return The goal_id found in the database.
     */
    public int getGoalId(int userID, int categoryID) {
        String sql = "SELECT goal_id FROM Goals WHERE user_id = ? AND category_id = ?";
        String[] p = {Integer.toString(userID), Integer.toString(categoryID)};
        ArrayList<String> result = db.selectQuery(sql, p, "goal_id");
        return Integer.parseInt(result.getFirst());
    }

    /**
     * Fetches the target amount for a specific goal from the database.
     * * @param goalID The ID of the goal.
     * @return The target_amount value.
     */
    public double getTargetAmount(int goalID) {
        String sql = "SELECT target_amount FROM Goals WHERE goal_id =?";
        String[] p = {Integer.toString(goalID)};
        ArrayList<String> result = db.selectQuery(sql, p, "target_amount");
        return Double.parseDouble(result.getFirst());
    }

    /**
     * Fetches the current saved amount for a specific goal from the database.
     * * @param goalId The ID of the goal.
     * @return The current_amount value.
     */
    public double getCurrentAmount(int goalId) {
        String sql = "SELECT current_amount FROM Goals WHERE goal_id = ?";
        String[] p = {Integer.toString(goalId)};
        ArrayList<String> result = db.selectQuery(sql, p, "current_amount");
        return Double.parseDouble(result.getFirst());
    }

    /**
     * Retrieves the name of the goal from the database.
     * * @param goalID The ID of the goal.
     * @return The name of the goal.
     */
    public String getName(int goalID) {
        String sql = "SELECT name FROM Goals WHERE goal_id = ?";
        String[] p = {Integer.toString(goalId)};
        ArrayList<String> result = db.selectQuery(sql, p, "name");
        return result.getFirst();
    }

    /**
     * Retrieves the name of the category linked to a specific goal.
     * * @param goalID The ID of the goal.
     * @return The name of the category associated with the goal.
     */
    public String getCategory(int goalID) {
        String sql = "SELECT category_id FROM Goals WHERE goal_id = ?";
        String[] p = {Integer.toString(goalId)};
        ArrayList<String> temp = db.selectQuery(sql, p, "category_id");

        sql = "SELECT name FROM Categories WHERE category_id = ?";
        String[] p2 = {temp.getFirst()};
        ArrayList<String> result = db.selectQuery(sql, p2, "name");

        return result.getFirst();
    }

    /**
     * Updates the local name of the goal.
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the local target amount of the goal.
     * @param targetAmount The new target amount.
     */
    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    /**
     * Helper method to update both the name and target amount locally.
     * * @param name         The new name.
     * @param targetAmount The new target amount.
     */
    public void setGoal(String name, double targetAmount) {
        this.name = name;
        this.targetAmount = targetAmount;
    }

    /**
     * Inserts the current goal record into the database for a specific user.
     * * @param userId The ID of the user owning this goal.
     */
    public void saveToDatabase(int userId) {
        String sql = "INSERT INTO Goals (name, target_amount, current_amount, category_id, user_id) VALUES (?, ?, ?, ?, ?)";
        String categoryIdValue = (this.category != null) ? String.valueOf(this.category.getCategoryId()) : null;
        String[] params = {
                this.name,
                String.valueOf(this.targetAmount),
                String.valueOf(this.currentAmount),
                categoryIdValue,
                String.valueOf(userId)
        };
        db.updateQuery(sql, params);
        System.out.println("Goal saved: " + this.name);
    }

    /**
     * Adds a specified amount to the current saved progress in the database
     * and updates the local instance.
     * * @param amount The amount to contribute to the goal.
     */
    public void updateProgressInDatabase(double amount) {
        String sql = "UPDATE Goals SET current_amount = current_amount + ? WHERE goal_id = ?";
        String[] params = {
                String.valueOf(amount),
                String.valueOf(this.goalId)
        };
        db.updateQuery(sql, params);
        this.currentAmount += amount;
        System.out.println("Progress updated: " + this.currentAmount + " / " + this.targetAmount);
    }

    /**
     * Checks if a goal with the current name already exists in the database.
     * * @return {@code true} if a goal name match is found; {@code false} otherwise.
     */
    public boolean exists() {
        Database tempDb = new Database();
        String sql = "SELECT goal_id FROM Goals WHERE name = ?";
        String[] params = { this.name };
        ArrayList<String> result = tempDb.selectQuery(sql, params, "goal_id");
        return !result.isEmpty();
    }
}