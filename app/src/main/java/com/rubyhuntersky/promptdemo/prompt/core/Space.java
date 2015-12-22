package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/21/15.
 */
public class Space {

    public final float maxWidth;
    public final float maxHeight;
    public final float perReadableX;
    public final float perReadableY;
    public final float perTappableX;
    public final float perTappableY;
    public final float x;
    public final float y;
    public final float width;
    public final float height;

    public Space(float maxWidth, float maxHeight, float readingPixels, float tappingPixels) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        perReadableX = perReadableY = readingPixels;
        perTappableX = perTappableY = tappingPixels;
        x = 0;
        y = 0;
        width = maxWidth;
        height = maxHeight;
    }

    public Space(Space space, float leftInset, float topInset, float rightInset, float bottomInset) {
        maxWidth = space.maxWidth;
        maxHeight = space.maxHeight;
        x = space.x + leftInset;
        y = space.y + topInset;
        width = Math.max(space.width - leftInset - rightInset, 0);
        height = Math.max(space.height - topInset - bottomInset, 0);

        final float originFactorY = space.height / height;
        perReadableY = space.perReadableY * originFactorY;
        perTappableY = space.perTappableY * originFactorY;

        final float originFactorX = space.width / width;
        perReadableX = space.perReadableX * originFactorX;
        perTappableX = space.perTappableX * originFactorX;
    }

    public Space inset(Dimension... insets) {
        return new Space(this, getDimension(insets, 3, 1, 1).convert(perReadableX, perTappableX, width, height),
                         getDimension(insets, 0, 0, 0).convert(perReadableY, perTappableY, height, width),
                         getDimension(insets, 1, 1, 1).convert(perReadableX, perTappableX, width, height),
                         getDimension(insets, 2, 2, 0).convert(perReadableY, perTappableY, height, width));
    }

    public Space inset(float... insets) {
        return new Space(this, getFloat(insets, 3, 1, 1), getFloat(insets, 0, 0, 0),
                         getFloat(insets, 1, 1, 1), getFloat(insets, 2, 2, 0));
    }

    private Dimension getDimension(Dimension[] insets, int index4, int index3, int index2) {
        if (insets.length >= 4) {
            return insets[index4];
        } else if (insets.length == 3) {
            return insets[index3];
        } else if (insets.length == 2) {
            return insets[index2];
        } else {
            return insets[0];
        }
    }

    private float getFloat(float[] insets, int index4, int index3, int index2) {
        if (insets.length >= 4) {
            return insets[index4];
        } else if (insets.length == 3) {
            return insets[index3];
        } else if (insets.length == 2) {
            return insets[index2];
        } else {
            return insets[0];
        }
    }
}
