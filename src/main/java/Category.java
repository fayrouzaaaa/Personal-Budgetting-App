public class Category {
    private int categoryId;
    private String name;
    private boolean isDefault;

    public Category(int categoryId, String name, boolean isDefault) {
        this.categoryId = categoryId;
        this.name = name;
        this.isDefault = isDefault;
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

    public void addCategory() {
        System.out.println("Category added: " + name);
    }

    public void editCategory(String newName) {
        this.name = newName;
        System.out.println("Category edited to: " + name);
    }

    public void deleteCategory() {
        System.out.println("Category deleted: " + name);
    }

    public void setCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{id=" + categoryId + ", name='" + name + "', isDefault=" + isDefault + "}";
    }
}