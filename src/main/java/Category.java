import java.util.ArrayList;

/**
 * Represents a classification for transactions and budgets (e.g., Food, Rent, Salary).
 * * <p>The Category class serves as a model to organize financial activities.
 * It provides methods to persist new categories, update existing ones,
 * delete them from the system, and verify their existence within the database.</p>

 */
public class Category {
    private int categoryId;
    private String name;
    private boolean isDefault;
    private Database db;

    /**
     * Constructs a new Category with a name and sets it as a default category.
     * * @param name The display name of the category.
     */
    public Category(String name) {
        this.name = name;
        this.isDefault = true;
        this.db = new Database();
    }

    /**
     * Constructs a Category with all specific details, typically used when
     * retrieving existing records from the database.
     * * @param categoryId The unique identifier of the category.
     * @param name       The display name of the category.
     * @param isDefault  True if the category is a system default; false if user-defined.
     */
    public Category(int categoryId, String name, boolean isDefault) {
        this.categoryId = categoryId;
        this.name = name;
        this.isDefault = isDefault;
        this.db = new Database();
    }

    /**
     * @return The unique ID of this category.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @return The name of this category.
     */
    public String getName() {
        return name;
    }

    /**
     * @return True if the category is a system default, false otherwise.
     */
    public boolean isDefault() {
        return isDefault;
    }

    /**
     * Updates the local name of the category.
     * * @param name The new category name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Inserts the current category instance as a new record in the database.
     */
    public void saveToDatabase() {
        String sql = "INSERT INTO Categories (name, is_default) VALUES (?, ?)";
        String[] params = {
                this.name,
                String.valueOf(this.isDefault ? 1 : 0)
        };
        db.updateQuery(sql, params);
        System.out.println("Category saved: " + this.name);
    }

    /**
     * Updates the name and default status of this category in the database
     * based on its unique ID.
     */
    public void updateInDatabase() {
        String sql = "UPDATE Categories SET name = ?, is_default = ? WHERE category_id = ?";
        String[] params = {
                this.name,
                String.valueOf(this.isDefault ? 1 : 0),
                String.valueOf(this.categoryId)
        };
        db.updateQuery(sql, params);
        System.out.println("Category updated: " + this.name);
    }

    /**
     * Removes this category record from the database using its unique ID.
     */
    public void deleteFromDatabase() {
        String sql = "DELETE FROM Categories WHERE category_id = ?";
        String[] params = { String.valueOf(this.categoryId) };
        db.updateQuery(sql, params);
        System.out.println("Category deleted: " + this.name);
    }

    /**
     * Checks if a category with the same name already exists in the database.
     * * @return {@code true} if a matching category name is found; {@code false} otherwise.
     */
    public boolean exists() {
        String sql = "SELECT category_id FROM Categories WHERE name = ?";
        String[] params = { this.name };
        ArrayList<String> result = db.selectQuery(sql, params, "category_id");
        return !result.isEmpty();
    }
}