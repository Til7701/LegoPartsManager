package de.holube.legopartsmanager.lego;

public class LegoTableSetItem {

    private final String elementID;
    private final int colorID;
    private final int quantity;

    public LegoTableSetItem(String elementID, int colorID, int quantity) {
        this.elementID = elementID;
        this.colorID = colorID;
        this.quantity = quantity;
    }

    public String getElementID() {
        return elementID;
    }

    public int getColorID() {
        return colorID;
    }

    public int getQuantity() {
        return quantity;
    }
}
