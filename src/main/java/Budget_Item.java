public class Budget_Item {
    private double limitAmount;
    private double spentAmount;
    private Category category;
    private Budget budget;

    public Budget_Item(Category category, double limitAmount) {
        this.category = category;
        this.limitAmount = limitAmount;
        this.spentAmount = 0.0;
    }

    public Budget_Item(Category category, double limitAmount, double spentAmount) {
        this.category = category;
        this.limitAmount = limitAmount;
        this.spentAmount = spentAmount;
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

    public void updateSpent(double amount) {
        this.spentAmount += amount;
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

    @Override
    public String toString() {
        return "Budget_Item{category=" + category.getName() + ", limit=" + limitAmount + ", spent=" + spentAmount + "}";
    }
}