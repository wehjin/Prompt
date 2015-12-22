package com.rubyhuntersky.promptdemo.prompt.basic;

import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Observer;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Presentation;
import com.rubyhuntersky.promptdemo.prompt.core.Region;
import com.rubyhuntersky.promptdemo.prompt.core.Shape;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public class ColorPrompt extends BasePrompt<Void, Void> {
    public static final ColorPrompt PRIMARY = new ColorPrompt(ColorWell.PRIMARY);
    public static final ColorPrompt ACCENT = new ColorPrompt(ColorWell.ACCENT);

    private final ColorWell colorWell;

    public ColorPrompt(ColorWell color) {
        this.colorWell = color;
    }

    @Override
    public Presentation<Void> present(Audience audience, Observer<Void> observer) {
        final Presentation<Void> presentation = super.present(audience, observer);
        final Patch patch = audience.getPatch(this.colorWell, new Region(audience.getSpace()), Shape.RECTANGLE);
        return new Presentation<Void>() {
            @Override
            public void end() {
                patch.erase();
                presentation.end();
            }

            @Override
            public boolean isEnded() {
                return presentation.isEnded();
            }

            @Override
            public Void getProgress() {
                return presentation.getProgress();
            }
        };
    }

    @Override
    public List<Element> toElements(Document document) {
        final ArrayList<Element> elements = new ArrayList<>(super.toElements(document));
        elements.add(getElement(document));
        return elements;
    }

    private Element getElement(Document document) {
        final Element element = document.createElement("ColorPrompt");
        List<Element> colorElements = colorWell.toElements(document);
        for (Element colorElement : colorElements) {
            element.appendChild(colorElement);
        }
        return element;
    }
}
