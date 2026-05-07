import java.util.ArrayList;

public class Category {
    private int categoryId;
    private String name;
    private boolean isDefault;
    private Database db;  // non-static instance

    public Category(int categoryId, String name, boolean isDefault) {
        this.categoryId = categoryId;
        this.name = name;
        this.isDefault = isDefault;
        this.db = new Database();  // initialize database
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String name) {
        this.name = name;
    }


    // Database methods (non-static)
    public void saveToDatabase() {
        String sql = "INSERT INTO Categories ( name, is_default) VALUES (?, ?)";
        String[] params = {
                this.name,
                String.valueOf(this.isDefault ? 1 : 0)
        };
        db.updateQuery(sql, params);
        System.out.println("Category saved: " + this.name);
    }

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

    public void deleteFromDatabase() {
        String sql = "DELETE FROM Categories WHERE category_id = ?";
        String[] params = { String.valueOf(this.categoryId) };
        db.updateQuery(sql, params);
        System.out.println("Category deleted: " + this.name);
    }

    public boolean exists() {
        String sql = "SELECT category_id FROM Categories WHERE name = ?";
        String[] params = { this.name };
        ArrayList<String> result = db.selectQuery(sql, params, "category_id");
        return !result.isEmpty();
    }
}
