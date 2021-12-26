package de.holube.legopartsmanager.lego;

public class LegoColor {

    private final String name;
    private final String rgb;
    private final int r;
    private final int g;
    private final int b;
    private final int intColor;
    private final boolean transparent;

    public LegoColor(String name, String rgb, int r, int g, int b, boolean transparent) {
        this.name = name;
        this.rgb = rgb;
        this.r = r;
        this.g = g;
        this.b = b;
        this.intColor = (255<<24) | (r<<16) | (g<<8) | b;
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

    public int getIntColor() {
        return intColor;
    }
}
