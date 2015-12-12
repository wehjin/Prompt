package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public interface Presentation<ProgressT> {
    void end();
    boolean isEnded();
    ProgressT getProgress();
}
