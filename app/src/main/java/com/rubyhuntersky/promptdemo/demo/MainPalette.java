package com.rubyhuntersky.promptdemo.demo;

import com.rubyhuntersky.promptdemo.prompt.core.Color;
import com.rubyhuntersky.promptdemo.prompt.core.Colorim;
import com.rubyhuntersky.promptdemo.prompt.core.Palette;

/**
 * @author wehjin
 * @since 12/14/15.
 */
class MainPalette implements Palette {

    private final Color dividerDark = new Color(.12f, 0f);
    private final Color contentDisabledDark = new Color(.38f, 0f);
    private final Color contentSecondaryDark = new Color(.54f, 0f);
    private final Color contentDark = new Color(.87f, 0f);
    private final Color dividerLight = new Color(.12f, 1f);
    private final Color contentDisabledLight = new Color(.3f, 1f);
    private final Color contentSecondaryLight = new Color(.7f, 1f);
    private final Color contentLight = new Color(1f, 1f);
    private final Color accentFallback = new Color(1f, 0xf5e89f);
    private final Color accent = new Color(1f, 0xff4081);
    private final Color primaryLight = new Color(1f, 0xc5cae9);
    private final Color primaryDark = new Color(1f, 0x303f9f);
    private final Color primary = new Color(1f, 0x3f51b5);

    @Override
    public Color getColor(Colorim colorim) {
        switch (colorim) {
            case WHITE:
                return Color.WHITE;
            case BLACK:
                return Color.BLACK;
            case PRIMARY:
                return primary;
            case PRIMARY_DARK:
                return primaryDark;
            case PRIMARY_LIGHT:
                return primaryLight;
            case ACCENT:
                return accent;
            case ACCENT_FALLBACK:
                return accentFallback;
            case CONTENT_DARK:
                return contentDark;
            case CONTENT_SECONDARY_DARK:
                return contentSecondaryDark;
            case CONTENT_DISABLED_DARK:
                return contentDisabledDark;
            case DIVIDER_DARK:
                return dividerDark;
            case CONTENT_LIGHT:
                return contentLight;
            case CONTENT_SECONDARY_LIGHT:
                return contentSecondaryLight;
            case CONTENT_DISABLED_LIGHT:
                return contentDisabledLight;
            case DIVIDER_LIGHT:
                return dividerLight;
        }
        return null;
    }
}
