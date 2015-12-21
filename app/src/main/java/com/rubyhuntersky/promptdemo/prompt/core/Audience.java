package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public interface Audience {
    Space getSpace();
    Patch getPatch(ColorWell colorWell, Region region, Shape shape);
}
