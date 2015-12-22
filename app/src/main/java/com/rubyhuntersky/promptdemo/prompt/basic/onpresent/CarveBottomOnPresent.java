package com.rubyhuntersky.promptdemo.prompt.basic.onpresent;

import com.rubyhuntersky.promptdemo.prompt.basic.BasePrompt;
import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Dimension;
import com.rubyhuntersky.promptdemo.prompt.core.Observer;
import com.rubyhuntersky.promptdemo.prompt.core.OutcomeAdapter;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Prompt;
import com.rubyhuntersky.promptdemo.prompt.core.Reducibles;
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
public class CarveBottomOnPresent<P1, O1, P2, O2, P3, O3> implements BasePrompt.OnPresent<P3, O3> {
    private final Dimension size;
    private final Prompt<P2, O2> prompt;
    private final OutcomeAdapter<O1, O2, O3> adapter;
    private final BasePrompt<P1, O1> original;

    public CarveBottomOnPresent(Dimension size, Prompt<P2, O2> prompt, OutcomeAdapter<O1, O2, O3> adapter,
          BasePrompt<P1, O1> original) {
        this.size = size;
        this.prompt = prompt;
        this.adapter = adapter;
        this.original = original;
    }

    @Override
    public void present(final BasePrompt.Presenter<P3, O3> presenter) {
        final Space originalSpace = presenter.getSpace();
        final float minorHeight = Math.round(
              size.convert(originalSpace.perReadableY, originalSpace.perTappableY, originalSpace.height,
                           originalSpace.width));
        final Space majorSpace = originalSpace.inset(0f, 0f, minorHeight);
        final Space minorSpace = originalSpace.inset(majorSpace.height, 0f, 0f);
        presenter.addPresentation(original.present(new Audience() {
            @Override
            public Space getSpace() {
                return majorSpace;
            }

            @Override
            public Patch getPatch(ColorWell colorWell, Region region, Shape shape) {
                return presenter.getPatch(colorWell, region, shape);
            }
        }, new Observer<O1>() {
            @Override
            public void onOutcome(O1 outcome) {
                presenter.onOutcome(adapter.adaptFirst(outcome));
            }
        }));
        presenter.addPresentation(prompt.present(new Audience() {
            @Override
            public Space getSpace() {
                return minorSpace;
            }

            @Override
            public Patch getPatch(ColorWell colorWell, Region region, Shape shape) {
                return presenter.getPatch(colorWell, region, shape);
            }
        }, new Observer<O2>() {
            @Override
            public void onOutcome(O2 outcome) {
                presenter.onOutcome(adapter.adaptSecond(outcome));
            }
        }));
    }

    @Override
    public List<Element> toElements(Document document) {
        final List<Element> elements = new ArrayList<>(original.toElements(document));
        final Element nextElement = getElement(document);
        elements.add(nextElement);
        return elements;
    }

    private Element getElement(Document document) {
        final Element element = document.createElement("CarveBottom");
        element.appendChild(Reducibles.createElement("Size", document, size));
        element.appendChild(Reducibles.createElement("Prompt", document, prompt));
        return element;
    }
}
