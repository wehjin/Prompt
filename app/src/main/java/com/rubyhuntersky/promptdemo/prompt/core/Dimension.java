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

abstract public class Dimension implements Reducible {

    public static final Dimension ZERO = create(Unit.ZERO, 0);
    public static final Dimension SPACE_START = create(Unit.SPACE, 0);
    public static final Dimension SPACE_END = create(Unit.SPACE, 1);
    public static final Dimension READABLE = create(Unit.READABLE, 1);
    public static final Dimension TAPPABLE = create(Unit.TAPPABLE, 1);
    public static final Dimension HALF_SPACE = create(Unit.SPACE, .5f);
    public static final Dimension HALF_READABLE_TEXT = create(Unit.READABLE, .5f);
    public static final Dimension HALF_TAPPABLE = create(Unit.TAPPABLE, .5f);
    public static final Dimension QUARTER_TAPPABLE = create(Unit.TAPPABLE, .25f);
    public static final Dimension CENTER_READABLE = Dimension.HALF_SPACE.offset(Dimension.HALF_READABLE_TEXT.negate());
    public static final Dimension CENTER_LABEL = Dimension.HALF_SPACE.offset(
          Dimension.TAPPABLE.multiply(1 / 5f).negate());
    public static final Dimension ALTSPACE = create(Unit.ALTSPACE, 1f);

    @Override
    abstract public List<Element> toElements(Document document);

    abstract public float convert(float perReadable, float perTappable, float perSpace, float perAltspace);

    public Dimension negate() {
        return multiply(-1);
    }

    public Dimension multiply(final float factor) {
        final Dimension leftDimension = Dimension.this;
        return create(new Implementation() {

            @Override
            public float convert(float perReadable, float perTappable, float perSpace, float perAltspace) {
                return leftDimension.convert(perReadable, perTappable, perSpace, perAltspace) * factor;
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

    public Dimension divide(final Dimension divisor) {
        final Dimension leftDimension = Dimension.this;
        return create(new Implementation() {
            @Override
            public float convert(float perReadable, float perTappable, float perSpace, float perAltspace) {
                final float divisorFloat = divisor.convert(perReadable, perTappable, perSpace, perAltspace);
                return leftDimension.convert(perReadable, perTappable, perSpace, perAltspace) / divisorFloat;
            }

            @Override
            public List<Element> toElements(Document document) {
                final Element element = document.createElement("Divide");
                final List<Element> childElements = divisor.toElements(document);
                for (Element childElement : childElements) {
                    element.appendChild(childElement);
                }
                return Collections.singletonList(element);
            }
        });
    }

    public Dimension offset(final Dimension offset) {
        final Dimension leftDimension = Dimension.this;
        return create(new Implementation() {
            @Override
            public float convert(float perReadable, float perTappable, float perSpace, float perAltspace) {
                return leftDimension.convert(perReadable, perTappable, perSpace, perAltspace)
                      + offset.convert(perReadable, perTappable, perSpace, perAltspace);
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
            public float convert(float perReadable, float perTappable, float perSpace, float perAltspace) {
                switch (unit) {
                    case ZERO:
                        return 0;
                    case READABLE:
                        return perReadable * amount;
                    case TAPPABLE:
                        return perTappable * amount;
                    case SPACE:
                        return perSpace * amount;
                    case ALTSPACE:
                        return perAltspace * amount;
                }
                throw new IllegalArgumentException();
            }
        });
    }

    private static Dimension create(final Implementation implementation) {
        return new Dimension() {
            @Override
            public float convert(float perReadable, float perTappable, float perSpace, float perAltspace) {
                return implementation.convert(perReadable, perTappable, perSpace, perAltspace);
            }

            @Override
            public List<Element> toElements(Document document) {
                return implementation.toElements(document);
            }
        };
    }

    private interface Implementation {
        float convert(float perReadable, float perTappable, float perSpace, float perAltspace);
        List<Element> toElements(Document document);
    }
}
