package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/12/15.
 */
public class Region {
    public final float left;
    public final float top;
    public final float width;
    public final float height;

    public Region(Space space) {
        left = space.x;
        top = space.y;
        width = space.width;
        height = space.height;
    }
}
