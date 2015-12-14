package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public class Color {

    final public static Color BLACK = new Color(1, 0, 0, 0);
    final public static Color WHITE = new Color(1, 1, 1, 1);
    final public static Color RED = new Color(1, 1, 0, 0);
    final public static Color GREEN = new Color(1, 0, 1, 0);
    final public static Color BLUE = new Color(1, 0, 0, 1);
    public final float alpha;
    public final float red;
    public final float green;
    public final float blue;

    public Color(float alpha, float red, float green, float blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color(float alpha, int rgb) {
        this.alpha = alpha;
        this.red = ((rgb >> 16) & 0xff) / 255f;
        this.green = ((rgb >> 8) & 0xff) / 255f;
        this.blue = (rgb & 0xff) / 255f;
    }

    public Color(float alpha, float bright) {
        this.alpha = alpha;
        this.red = this.blue = this.green = bright;
    }
}
