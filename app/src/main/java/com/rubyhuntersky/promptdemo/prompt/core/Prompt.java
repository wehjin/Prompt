package com.rubyhuntersky.promptdemo.prompt.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public interface Prompt<P, O> extends Reducible {
    Presentation<P> present(Audience audience, Observer<O> observer);
    List<Element> toElements(Document document);

    Prompt<P, O> inset(Dimension... insets);

    <P2, O2, P3, O3> Prompt<P3, O3> carveBottom(Dimension size, Prompt<P2, O2> prompt,
          OutcomeAdapter<O, O2, O3> adapter);

    Prompt<P, O> limitHeight(Dimension size, Anchor anchor);
}
