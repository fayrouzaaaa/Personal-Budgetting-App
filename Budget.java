import java.util.ArrayList;
import java.util.List;

public class Budget {
    private int budgetId;
    private String month;
    private List<Budget_Item> budgetItems;

    public Budget(int budgetId, String month) {
        this.budgetId = budgetId;
        this.month = month;
        this.budgetItems = new ArrayList<>();
    }

    public int getBudgetId() {
        return budgetId;
    }

    public String getMonth() {
        return month;
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

    public void updateSpentForCategory(Category category, double amount) {
        Budget_Item item = getBudgetItemByCategory(category);
        if (item != null) {
            item.updateSpent(amount);
        }
    }

    public boolean isCategoryOverLimit(Category category) {
        Budget_Item item = getBudgetItemByCategory(category);
        if (item != null) {
            return item.isOverLimit();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Budget{id=" + budgetId + ", month='" + month + "', items=" + budgetItems.size() + "}";
    }
}