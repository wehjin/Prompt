package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/14/15.
 */

abstract public class ColorWell {

    public static final ColorWell BLACK = create(Colorim.BLACK);
    public static final ColorWell WHITE = create(Colorim.WHITE);
    public static final ColorWell PRIMARY = create(Colorim.PRIMARY);
    public static final ColorWell PRIMARY_LIGHT = create(Colorim.PRIMARY_LIGHT);
    public static final ColorWell PRIMARY_DARK = create(Colorim.PRIMARY_DARK);
    public static final ColorWell ACCENT = create(Colorim.ACCENT);
    public static final ColorWell ACCENT_FALLBACK = create(Colorim.ACCENT_FALLBACK);
    public static final ColorWell DIVIDER_DARK = create(Colorim.DIVIDER_DARK);

    abstract public Color getColor(Palette palette);

    static public ColorWell create(final Colorim colorim) {
        return create(new OnColor() {
            @Override
            public Color getColor(Palette palette) {
                return palette.getColor(colorim);
            }
        });
    }

    static public ColorWell create(final OnColor onColor) {
        return new ColorWell() {
            @Override
            public Color getColor(Palette palette) {
                return onColor.getColor(palette);
            }
        };
    }

    public interface OnColor {
        Color getColor(Palette palette);
    }
}
