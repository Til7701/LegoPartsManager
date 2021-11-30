package de.holube.legopartsmanager.lego;

import java.util.Objects;

public class LegoElement {

    private String designID;
    private int colorID;

    public LegoElement(String designID, int colorID) {
        this.designID = designID;
        this.colorID = colorID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegoElement that = (LegoElement) o;
        return colorID == that.colorID && Objects.equals(designID, that.designID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(designID, colorID);
    }

    public String getDesignID() {
        return designID;
    }

    public void setDesignID(String designID) {
        this.designID = designID;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }
}
