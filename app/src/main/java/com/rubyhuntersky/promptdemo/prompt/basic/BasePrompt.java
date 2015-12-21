package com.rubyhuntersky.promptdemo.prompt.basic;

import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Dimension;
import com.rubyhuntersky.promptdemo.prompt.core.Observer;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Presentation;
import com.rubyhuntersky.promptdemo.prompt.core.Prompt;
import com.rubyhuntersky.promptdemo.prompt.core.Reducibles;
import com.rubyhuntersky.promptdemo.prompt.core.Region;
import com.rubyhuntersky.promptdemo.prompt.core.Shape;
import com.rubyhuntersky.promptdemo.prompt.core.Space;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public class BasePrompt<ProgressT> implements Prompt<ProgressT> {

    private final OnPresent<ProgressT> onPresent;

    public BasePrompt(OnPresent<ProgressT> onPresent) {
        this.onPresent = onPresent;
    }

    public BasePrompt() {
        this.onPresent = null;
    }

    @Override
    public Prompt<ProgressT> inset(final Dimension... insets) {
        return new BasePrompt<>(new InsetOnPresent<>(this, insets));
    }

    @Override
    public <ProgressT2, AdaptedT> Prompt<AdaptedT> carveBottom(final Dimension size, final Prompt<ProgressT2> prompt,
          Adapter2<ProgressT, ProgressT2, AdaptedT> adapter) {
        final BasePrompt<ProgressT> original = this;
        return new BasePrompt<>(new OnPresent<AdaptedT>() {
            @Override
            public void present(final Presenter<AdaptedT> presenter) {
                final Space originalSpace = presenter.getSpace();
                final Space majorSpace = originalSpace.inset(Dimension.ZERO, Dimension.ZERO, size);
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
                }, new Observer<ProgressT>() {
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
                }, new Observer<ProgressT2>() {
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
        });
    }

    @Override
    public Presentation<ProgressT> present(final Audience audience, Observer<ProgressT> observer) {

        final Presenter<ProgressT> presenter = new Presenter<ProgressT>() {

            boolean isEnded = false;
            List<Presentation<?>> presentations = new ArrayList<>();

            @Override
            public void addPresentation(Presentation<?> presentation) {
                presentations.add(presentation);
            }

            @Override
            public Space getSpace() {
                return audience.getSpace();
            }

            @Override
            public Patch getPatch(ColorWell color, Region dimensions, Shape shape) {
                return audience.getPatch(color, dimensions, shape);
            }

            @Override
            public void end() {
                for (Presentation<?> presentation : presentations) {
                    presentation.end();
                }
                isEnded = true;
            }

            @Override
            public boolean isEnded() {
                return isEnded;
            }

            @Override
            public ProgressT getProgress() {
                return null;
            }
        };
        if (onPresent != null) {
            onPresent.present(presenter);
        }
        return presenter;
    }

    @Override
    public List<Element> toElements(Document document) {
        return onPresent == null ? Collections.<Element>emptyList() : onPresent.toElements(document);
    }

    public interface OnPresent<ProgressT> {
        void present(Presenter<ProgressT> presenter);
        List<Element> toElements(Document document);
    }

    public interface Presenter<ProgressT> extends Audience, Observer<ProgressT>, Presentation<ProgressT> {
        void addPresentation(Presentation<?> presentation);
    }

    private static class InsetOnPresent<ProgressT> implements OnPresent<ProgressT> {
        private final BasePrompt<ProgressT> previousPrompt;
        private final Dimension[] insets;

        public InsetOnPresent(BasePrompt<ProgressT> previousPrompt, Dimension... insets) {
            this.previousPrompt = previousPrompt;
            this.insets = insets;
        }

        @Override
        public void present(final Presenter<ProgressT> presenter) {
            previousPrompt.present(new Audience() {
                @Override
                public Space getSpace() {
                    return presenter.getSpace().inset(insets);
                }

                @Override
                public Patch getPatch(ColorWell color, Region region, Shape shape) {
                    return presenter.getPatch(color, region, shape);
                }
            }, presenter);
        }

        @Override
        public List<Element> toElements(Document document) {
            final List<Element> elements = previousPrompt.toElements(document);
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
}
