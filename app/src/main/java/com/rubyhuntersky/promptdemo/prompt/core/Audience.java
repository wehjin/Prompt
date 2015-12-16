package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public interface Audience {
    Patch getPatch(ColorWell colorWell, Region dimensions, Shape shape);
}
