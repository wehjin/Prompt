package com.rubyhuntersky.promptdemo.prompt.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author wehjin
 * @since 12/21/15.
 */

public interface Reducible {
    List<Element> toElements(Document document);
}
