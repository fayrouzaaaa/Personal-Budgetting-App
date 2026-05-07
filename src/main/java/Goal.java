import java.util.ArrayList;

public class Goal {
    private int goalId;
    private double targetAmount;
    private double currentAmount;
    private String name;
    private Category category;
    private Database db;

    public Goal(int goalId, String name, double targetAmount) {
        this(goalId, name, targetAmount, 0.0, null);
        this.db = new Database();
    }

    public Goal(int goalId, String name, double targetAmount, Category category) {
        this(goalId, name, targetAmount, 0.0, category);
        this.db = new Database();
    }

    public Goal(int goalId, String name, double targetAmount, double currentAmount, Category category) {
        this.goalId = goalId;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.category = category;
        this.db = new Database();
    }

    public int getGoalId() {
        return goalId;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public void setGoal(String name, double targetAmount) {
        this.name = name;
        this.targetAmount = targetAmount;
    }

    // Database methods (non-static)
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

    public boolean exists() {
        Database tempDb = new Database();
        String sql = "SELECT goal_id FROM Goals WHERE name = ?";
        String[] params = { this.name };
        ArrayList<String> result = tempDb.selectQuery(sql, params, "goal_id");
        return !result.isEmpty();
    }
}
