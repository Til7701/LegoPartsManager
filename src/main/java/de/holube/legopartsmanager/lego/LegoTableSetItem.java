package de.holube.legopartsmanager.lego;

public class LegoTableSetItem {
    private final String elementID;
    private final int colorID;
    private final int quanitity;

    public LegoTableSetItem(String elementID, int colorID, int quanitity) {
        this.elementID = elementID;
        this.colorID = colorID;
        this.quanitity = quanitity;
    }

    public String getElementID() {
        return elementID;
    }

    public int getColorID() {
        return colorID;
    }

    public int getQuanitity() {
        return quanitity;
    }
}
