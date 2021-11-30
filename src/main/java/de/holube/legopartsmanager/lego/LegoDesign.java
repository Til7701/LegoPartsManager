package de.holube.legopartsmanager.lego;

public class LegoDesign {

    private String description;
    private int categoryID;
    private String material;

    public LegoDesign(String description, int categoryID, String material) {
        this.description = description;
        this.categoryID = categoryID;
        this.material = material;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
