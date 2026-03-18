package data.model;

public class Category {

    private int categoryId;
    private String name;
    private String type;
    private String description;
    private int userId;

    public Category() {
    }

    public Category(int categoryId, String name, String type, String description, int userId) {
        this.categoryId = categoryId;
        this.name = name;
        this.type = type;
        this.description = description;
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
