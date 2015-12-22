package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public interface Observer<T> {
    Observer<Void> VOID_IGNORE = new Observer<Void>() {
        @Override
        public void onOutcome(Void outcome) {
            // Do nothing.
        }
    };

    void onOutcome(T outcome);
}
