package com.rubyhuntersky.promptdemo.prompt.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public interface Prompt<ProgressT, ResultT> {
    List<Element> toElements(Document document);
    Presentation<ProgressT> present(Audience audience, Observer<ResultT> observer);
    Prompt<ProgressT, ResultT> inset(Dimension inset);
}
