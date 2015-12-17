package com.rubyhuntersky.promptdemo.prompt.basic;

import com.rubyhuntersky.promptdemo.prompt.core.Anchor;
import com.rubyhuntersky.promptdemo.prompt.core.Shape;

/**
 * @author wehjin
 * @since 12/16/15.
 */

public class TextlineShape implements Shape {
    public final String text;
    public final Anchor anchor;

    public TextlineShape(String text, Anchor anchor) {
        this.text = text;
        this.anchor = anchor;
    }
}
