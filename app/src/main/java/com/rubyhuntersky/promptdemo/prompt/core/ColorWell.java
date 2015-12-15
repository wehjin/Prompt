package com.rubyhuntersky.promptdemo.prompt.core;

import android.support.annotation.NonNull;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Collections;
import java.util.List;

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

            @Override
            public List<Element> toElements(Document document) {
                return Collections.singletonList(getElement(document));
            }

            @NonNull
            private Element getElement(Document document) {
                final Element element = document.createElement("ColorimWell");
                element.setAttribute("colorim", colorim.name());
                return element;
            }
        });
    }

    static public ColorWell create(final OnColor onColor) {
        return new ColorWell() {
            @Override
            public Color getColor(Palette palette) {
                return onColor.getColor(palette);
            }

            @Override
            public List<Element> toElements(Document document) {
                return onColor.toElements(document);
            }
        };
    }

    public abstract List<Element> toElements(Document document);

    public interface OnColor {
        Color getColor(Palette palette);
        List<Element> toElements(Document document);
    }
}
