package com.rubyhuntersky.promptdemo.prompt.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author wehjin
 * @since 12/21/15.
 */

public class Reducibles {

    public static Element createElement(String name, Document document, Reducible reducible) {
        final Element element = document.createElement(name);
        final List<Element> childElements = reducible.toElements(document);
        for (Element child : childElements) {
            element.appendChild(child);
        }
        return element;
    }
}
