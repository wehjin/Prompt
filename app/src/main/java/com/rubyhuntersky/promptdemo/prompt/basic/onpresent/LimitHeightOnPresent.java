package com.rubyhuntersky.promptdemo.prompt.basic.onpresent;

import com.rubyhuntersky.promptdemo.prompt.basic.BasePrompt;
import com.rubyhuntersky.promptdemo.prompt.core.Anchor;
import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Dimension;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
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
public class LimitHeightOnPresent<P, O> implements BasePrompt.OnPresent<P, O> {
    private final Dimension size;
    private final Anchor anchor;
    private final BasePrompt<P, O> prompt;

    public LimitHeightOnPresent(Dimension size, Anchor anchor, BasePrompt<P, O> prompt) {
        this.size = size;
        this.anchor = anchor;
        this.prompt = prompt;
    }

    @Override
    public void present(final BasePrompt.Presenter<P, O> presenter) {
        final Space space = presenter.getSpace();
        final float height = size.convert(space.perReadableY, space.perTappableY, space.height, space.width);
        final float extra = Math.round(space.height - height);
        final float topInset = extra * anchor.y;
        final float bottomInset = space.height - topInset - height;
        final Space limitedSpace = space.inset(topInset, 0, bottomInset);
        presenter.addPresentation(prompt.present(new Audience() {
            @Override
            public Space getSpace() {
                return limitedSpace;
            }

            @Override
            public Patch getPatch(ColorWell colorWell, Region region, Shape shape) {
                return presenter.getPatch(colorWell, region, shape);
            }
        }, presenter));
    }

    @Override
    public List<Element> toElements(Document document) {
        final ArrayList<Element> elements = new ArrayList<>(prompt.toElements(document));
        elements.add(Reducibles.createElement("LimitHeight", document, size));
        return elements;
    }
}
