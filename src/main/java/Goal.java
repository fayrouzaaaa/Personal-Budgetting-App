public class Goal {
    private int goalId;
    private double targetAmount;
    private double currentAmount;
    private String name;
    private Category category;

    public Goal(int goalId, String name, double targetAmount) {
        this(goalId, name, targetAmount, 0.0, null);
    }

    public Goal(int goalId, String name, double targetAmount, Category category) {
        this(goalId, name, targetAmount, 0.0, category);
    }

    public Goal(int goalId, String name, double targetAmount, double currentAmount, Category category) {
        this.goalId = goalId;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.category = category;
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

    public void updateProgress(double amount) {
        this.currentAmount += amount;
    }

    public boolean isAchieved() {
        return currentAmount >= targetAmount;
    }

    public double getProgressPercentage() {
        if (targetAmount == 0) return 0;
        return (currentAmount / targetAmount) * 100;
    }

    public void trackProgress(double amount) {
        updateProgress(amount);
    }

    public double getCompletionProgress() {
        return getProgressPercentage();
    }

    @Override
    public String toString() {
        return "Goal{id=" + goalId + ", name='" + name + "', progress=" + getProgressPercentage() + "%}";
    }
}