package de.holube.legopartsmanager.lego;

public class LegoColor {

    private String name;
    private String rgb;
    private boolean transparent;

    public LegoColor(String name, String rgb, boolean transparent) {
        this.name = name;
        this.rgb = rgb;
        this.transparent = transparent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }
}
