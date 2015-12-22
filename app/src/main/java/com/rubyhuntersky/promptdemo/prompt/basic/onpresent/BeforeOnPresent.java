package com.rubyhuntersky.promptdemo.prompt.basic.onpresent;

import com.rubyhuntersky.promptdemo.prompt.basic.BasePrompt;
import com.rubyhuntersky.promptdemo.prompt.core.Observer;
import com.rubyhuntersky.promptdemo.prompt.core.Prompt;
import com.rubyhuntersky.promptdemo.prompt.core.Reducibles;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wehjin
 * @since 12/22/15.
 */

public class BeforeOnPresent<P, O> implements BasePrompt.OnPresent<P, O> {
    private final Prompt<Void, Void> background;
    private final Prompt<P, O> foreground;

    public BeforeOnPresent(Prompt<Void, Void> background, Prompt<P, O> foreground) {
        this.background = background;
        this.foreground = foreground;
    }

    @Override
    public void present(BasePrompt.Presenter<P, O> presenter) {
        presenter.addPresentation(background.present(presenter, Observer.VOID_IGNORE));
        presenter.addPresentation(foreground.present(presenter, presenter));
    }

    @Override
    public List<Element> toElements(Document document) {
        final List<Element> elements = new ArrayList<>(foreground.toElements(document));
        final Element before = Reducibles.createElement("Before", document, background);
        elements.add(before);
        return elements;
    }
}
