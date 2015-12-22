package com.rubyhuntersky.promptdemo.prompt.basic.onpresent;

import com.rubyhuntersky.promptdemo.prompt.basic.BasePrompt;
import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Dimension;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Region;
import com.rubyhuntersky.promptdemo.prompt.core.Shape;
import com.rubyhuntersky.promptdemo.prompt.core.Space;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wehjin
 * @since 12/22/15.
 */
public class InsetOnPresent<P, O> implements BasePrompt.OnPresent<P, O> {
    private final BasePrompt<P, O> previousPrompt;
    private final Dimension[] insets;

    public InsetOnPresent(BasePrompt<P, O> previousPrompt, Dimension... insets) {
        this.previousPrompt = previousPrompt;
        this.insets = insets;
    }

    @Override
    public void present(final BasePrompt.Presenter<P, O> presenter) {
        presenter.addPresentation(previousPrompt.present(new Audience() {
            @Override
            public Space getSpace() {
                return presenter.getSpace().inset(insets);
            }

            @Override
            public Patch getPatch(ColorWell color, Region region, Shape shape) {
                return presenter.getPatch(color, region, shape);
            }
        }, presenter));
    }

    @Override
    public List<Element> toElements(Document document) {
        final List<Element> elements = new ArrayList<>(previousPrompt.toElements(document));
        final Element insetElement = getElement(document);
        elements.add(insetElement);
        return elements;
    }

    private Element getElement(Document document) {
        final Element insetsElement = document.createElement("Insets");
        boolean first = true;
        for (Dimension inset : insets) {
            if (first) {
                first = false;
            } else {
                insetsElement.appendChild(document.createElement("_."));
            }
            for (Element element : inset.toElements(document)) {
                insetsElement.appendChild(element);
            }
        }
        return insetsElement;
    }
}
