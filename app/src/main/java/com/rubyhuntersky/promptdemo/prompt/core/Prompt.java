package com.rubyhuntersky.promptdemo.prompt.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public interface Prompt<ProgressT> extends Reducible {
    Presentation<ProgressT> present(Audience audience, Observer<ProgressT> observer);
    List<Element> toElements(Document document);

    Prompt<ProgressT> inset(Dimension... insets);
    <ProgressT2, AdaptedT> Prompt<AdaptedT> carveBottom(Dimension size, Prompt<ProgressT2> prompt,
          Adapter2<ProgressT, ProgressT2, AdaptedT> adapter);

    interface Adapter2<InputT1, InputT2, OutputT> {
        Adapter2<Void, Void, Void> VOID = new Adapter2<Void, Void, Void>() {
        };
    }
}
