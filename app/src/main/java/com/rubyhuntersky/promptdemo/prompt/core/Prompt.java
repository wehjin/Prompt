package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public interface Prompt<ProgressT, ResultT> {
    Presentation<ProgressT> present(Audience audience, Observer<ResultT> observer);
    Prompt<ProgressT, ResultT> inset(Dimension inset);
}
