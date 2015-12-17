package com.rubyhuntersky.promptdemo.prompt.basic;

import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Dimension;
import com.rubyhuntersky.promptdemo.prompt.core.Observer;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Presentation;
import com.rubyhuntersky.promptdemo.prompt.core.Prompt;
import com.rubyhuntersky.promptdemo.prompt.core.Region;
import com.rubyhuntersky.promptdemo.prompt.core.Shape;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Collections;
import java.util.List;

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
    public Prompt<ProgressT, ResultT> inset(final Dimension... insets) {
        return new BasePrompt<>(new InsetOnPresent<>(this, insets));
    }

    @Override
    public Presentation<ProgressT> present(final Audience audience, Observer<ResultT> observer) {

        final Presenter<ProgressT, ResultT> presenter = new Presenter<ProgressT, ResultT>() {

            boolean isEnded = false;

            @Override
            public Patch getPatch(ColorWell color, Region dimensions, Shape shape) {
                return audience.getPatch(color, dimensions, shape);
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

    @Override
    public List<Element> toElements(Document document) {
        return onPresent == null ? Collections.<Element>emptyList() : onPresent.toElements(document);
    }

    public interface OnPresent<ProgressT, ResultT> {
        void present(Presenter<ProgressT, ResultT> presenter);
        List<Element> toElements(Document document);
    }

    public interface Presenter<ProgressT, ResultT> extends Audience, Observer<ResultT>, Presentation<ProgressT> {
    }

    private static class InsetOnPresent<ProgressT, ResultT> implements OnPresent<ProgressT, ResultT> {
        private final BasePrompt<ProgressT, ResultT> previousPrompt;
        private final Dimension[] insets;

        public InsetOnPresent(BasePrompt<ProgressT, ResultT> previousPrompt, Dimension... insets) {
            this.previousPrompt = previousPrompt;
            this.insets = insets;
        }

        @Override
        public void present(final Presenter<ProgressT, ResultT> presenter) {
            previousPrompt.present(new Audience() {
                @Override
                public Patch getPatch(ColorWell color, Region dimensions, Shape shape) {
                    return presenter.getPatch(color, dimensions.inset(insets), shape);
                }
            }, presenter);
        }

        @Override
        public List<Element> toElements(Document document) {
            final List<Element> elements = previousPrompt.toElements(document);
            final Element insetElement = getElement(document);
            elements.add(insetElement);
            return elements;
        }

        private Element getElement(Document document) {
            final Element insetsElement = document.createElement("Insets");
            boolean first = true;
            for (Dimension inset : insets) {
                if (first) {
                    first = false;
                } else {
                    insetsElement.appendChild(document.createElement("_."));
                }
                for (Element element : inset.toElements(document)) {
                    insetsElement.appendChild(element);
                }
            }
            return insetsElement;
        }
    }
}
