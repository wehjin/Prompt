package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/16/15.
 */

public class Anchor {

    public static final Anchor CENTER = new Anchor(.5f, .5f, .5f);
    public static final Anchor LEFT = new Anchor(0, .5f, .5f);
    public static final Anchor RIGHT = new Anchor(1, .5f, .5f);
    public static final Anchor TOP = new Anchor(.5f, 0, .5f);
    public static final Anchor BOTTOM = new Anchor(.5f, 1, .5f);
    public static final Anchor TOP_LEFT = new Anchor(0, 0, .5f);
    public static final Anchor TOP_RIGHT = new Anchor(0, 1, .5f);
    public static final Anchor BOTTOM_LEFT = new Anchor(1, 0, .5f);
    public static final Anchor BOTTOM_RIGHT = new Anchor(1, 1, .5f);

    public final float x;
    public final float y;
    public final float z;

    public Anchor(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toAttribute() {
        return String.format("%f,%f,%f", x, y, z);
    }
}
