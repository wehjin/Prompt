package com.rubyhuntersky.promptdemo.prompt.basic;

import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Dimension;
import com.rubyhuntersky.promptdemo.prompt.core.Observer;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Region;
import com.rubyhuntersky.promptdemo.prompt.core.Presentation;
import com.rubyhuntersky.promptdemo.prompt.core.Prompt;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public class BasePrompt<ProgressT, ResultT> implements Prompt<ProgressT, ResultT> {

    private final OnPresent<ProgressT, ResultT> onPresent;

    public BasePrompt(OnPresent<ProgressT, ResultT> onPresent) {
        this.onPresent = onPresent;
    }

    public BasePrompt() {
        this.onPresent = null;
    }

    @Override
    public Prompt<ProgressT, ResultT> inset(final Dimension inset) {
        final BasePrompt<ProgressT, ResultT> prompt = this;
        return new BasePrompt<>(new OnPresent<ProgressT, ResultT>() {
            @Override
            public void present(final Presenter<ProgressT, ResultT> presenter) {
                prompt.present(new Audience() {
                    @Override
                    public Patch getPatch(ColorWell color, Region dimensions) {
                        return presenter.getPatch(color, dimensions.inset(inset));
                    }
                }, presenter);
            }
        });
    }

    @Override
    public Presentation<ProgressT> present(final Audience audience, Observer<ResultT> observer) {

        final Presenter<ProgressT, ResultT> presenter = new Presenter<ProgressT, ResultT>() {

            boolean isEnded = false;

            @Override
            public Patch getPatch(ColorWell color, Region dimensions) {
                return audience.getPatch(color, dimensions);
            }

            @Override
            public void end() {
                isEnded = true;
            }

            @Override
            public boolean isEnded() {
                return isEnded;
            }

            @Override
            public ProgressT getProgress() {
                return null;
            }
        };
        if (onPresent != null) {
            onPresent.present(presenter);
        }
        return presenter;
    }

    public interface OnPresent<ProgressT, ResultT> {
        void present(Presenter<ProgressT, ResultT> presenter);
    }

    public interface Presenter<ProgressT, ResultT> extends Audience, Observer<ResultT>, Presentation<ProgressT> {
    }
}
