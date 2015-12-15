package com.rubyhuntersky.promptdemo.prompt.core;

import android.support.annotation.NonNull;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wehjin
 * @since 12/12/15.
 */

abstract public class Dimension {

    public static final Dimension SPACE_START = create(Unit.SPACE, 0);
    public static final Dimension SPACE_END = create(Unit.SPACE, 1);
    public static final Dimension READABLE = create(Unit.READABLE, 1);
    public static final Dimension TAPPABLE = create(Unit.TAPPABLE, 1);

    abstract public List<Element> toElements(Document document);

    abstract public float convert(float perReadable, float perTappable, float perSpace);

    public Dimension negate() {
        return multiply(-1);
    }

    public Dimension multiply(final float factor) {
        final Dimension leftDimension = Dimension.this;
        return create(new Implementation() {

            @Override
            public float convert(float perReadable, float perTappable, float perSpace) {
                return leftDimension.convert(perReadable, perTappable, perSpace) * factor;
            }

            @Override
            public List<Element> toElements(Document document) {
                final List<Element> elements = new ArrayList<>(leftDimension.toElements(document));
                elements.add(getElement(document));
                return elements;
            }

            @NonNull
            private Element getElement(Document document) {
                final Element element = document.createElement("Multiply");
                element.setAttribute("factor", String.valueOf(factor));
                return element;
            }
        });
    }

    public Dimension offset(final Dimension offset) {
        final Dimension leftDimension = Dimension.this;
        return create(new Implementation() {
            @Override
            public float convert(float perReadable, float perTappable, float perSpace) {
                return leftDimension.convert(perReadable, perTappable, perSpace)
                      + offset.convert(perReadable, perTappable, perSpace);
            }

            @Override
            public List<Element> toElements(Document document) {
                final List<Element> elements = new ArrayList<>(leftDimension.toElements(document));
                elements.add(getElement(document));
                return elements;
            }

            private Element getElement(Document document) {
                final Element element = document.createElement("Offset");
                final List<Element> offsetElements = offset.toElements(document);
                for (Element offsetElement : offsetElements) {
                    element.appendChild(offsetElement);
                }
                return element;
            }
        });
    }

    public static Dimension create(final Unit unit, final float amount) {
        return create(new Implementation() {
            @Override
            public List<Element> toElements(Document document) {
                final Element element = document.createElement("UnitDimension");
                element.setAttribute("unit", unit.name());
                element.setAttribute("amount", String.valueOf(amount));
                return Collections.singletonList(element);
            }

            @Override
            public float convert(float perReadable, float perTappable, float perSpace) {
                switch (unit) {
                    case READABLE:
                        return perReadable * amount;
                    case TAPPABLE:
                        return perTappable * amount;
                    case SPACE:
                        return perSpace * amount;
                }
                throw new IllegalArgumentException();
            }
        });
    }

    private static Dimension create(final Implementation implementation) {
        return new Dimension() {
            @Override
            public float convert(float perReadable, float perTappable, float perSpace) {
                return implementation.convert(perReadable, perTappable, perSpace);
            }

            @Override
            public List<Element> toElements(Document document) {
                return implementation.toElements(document);
            }
        };
    }

    private interface Implementation {
        float convert(float perReadable, float perTappable, float perSpace);
        List<Element> toElements(Document document);
    }
}
