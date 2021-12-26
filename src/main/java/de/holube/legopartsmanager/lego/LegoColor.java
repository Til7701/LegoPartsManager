package de.holube.legopartsmanager.lego;

public class LegoColor {

    private String name;
    private String rgb;
    private int r, g, b;
    private boolean transparent;

    public LegoColor(String name, String rgb, int r, int g, int b, boolean transparent) {
        this.name = name;
        this.rgb = rgb;
        this.r = r;
        this.g = g;
        this.b = b;
        this.transparent = transparent;
    }

    public String getName() {
        return name;
    }

    public String getRgb() {
        return rgb;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public boolean isTransparent() {
        return transparent;
    }
}
