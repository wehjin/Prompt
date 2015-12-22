package com.rubyhuntersky.promptdemo.prompt.basic;

import com.rubyhuntersky.promptdemo.prompt.basic.onpresent.BeforeOnPresent;
import com.rubyhuntersky.promptdemo.prompt.basic.onpresent.CarveBottomOnPresent;
import com.rubyhuntersky.promptdemo.prompt.basic.onpresent.InsetOnPresent;
import com.rubyhuntersky.promptdemo.prompt.basic.onpresent.LimitHeightOnPresent;
import com.rubyhuntersky.promptdemo.prompt.core.Anchor;
import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Dimension;
import com.rubyhuntersky.promptdemo.prompt.core.Observer;
import com.rubyhuntersky.promptdemo.prompt.core.OutcomeAdapter;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Presentation;
import com.rubyhuntersky.promptdemo.prompt.core.Prompt;
import com.rubyhuntersky.promptdemo.prompt.core.Region;
import com.rubyhuntersky.promptdemo.prompt.core.Shape;
import com.rubyhuntersky.promptdemo.prompt.core.Space;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public class BasePrompt<P, O> implements Prompt<P, O> {

    private final OnPresent<P, O> onPresent;

    public BasePrompt(OnPresent<P, O> onPresent) {
        this.onPresent = onPresent;
    }

    public BasePrompt() {
        this.onPresent = null;
    }

    @Override
    public Prompt<P, O> inset(final Dimension... insets) {
        return new BasePrompt<>(new InsetOnPresent<>(this, insets));
    }

    @Override
    public <P2, O2, P3, O3> Prompt<P3, O3> carveBottom(final Dimension size, final Prompt<P2, O2> prompt,
          final OutcomeAdapter<O, O2, O3> adapter) {
        return new BasePrompt<>(new CarveBottomOnPresent<P, O, P2, O2, P3, O3>(size, prompt, adapter, this));
    }

    @Override
    public Prompt<P, O> limitHeight(final Dimension size, final Anchor anchor) {
        return new BasePrompt<>(new LimitHeightOnPresent<>(size, anchor, this));
    }

    @Override
    public Prompt<P, O> before(Prompt<Void, Void> background) {
        return new BasePrompt<>(new BeforeOnPresent<>(background, this));
    }

    @Override
    public Presentation<P> present(final Audience audience, final Observer<O> observer) {

        final Presenter<P, O> presenter = new Presenter<P, O>() {

            boolean isEnded = false;
            List<Presentation<?>> presentations = new ArrayList<>();

            @Override
            public void addPresentation(Presentation<?> presentation) {
                presentations.add(presentation);
            }

            @Override
            public void onOutcome(O outcome) {
                observer.onOutcome(outcome);
            }

            @Override
            public Space getSpace() {
                return audience.getSpace();
            }

            @Override
            public Patch getPatch(ColorWell color, Region dimensions, Shape shape) {
                return audience.getPatch(color, dimensions, shape);
            }

            @Override
            public void end() {
                for (Presentation<?> presentation : presentations) {
                    presentation.end();
                }
                isEnded = true;
            }

            @Override
            public boolean isEnded() {
                return isEnded;
            }

            @Override
            public P getProgress() {
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

    public interface OnPresent<P, O> {
        void present(Presenter<P, O> presenter);
        List<Element> toElements(Document document);
    }

    public interface Presenter<P, O> extends Audience, Observer<O>, Presentation<P> {
        void addPresentation(Presentation<?> presentation);
    }

}
