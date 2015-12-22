package com.rubyhuntersky.promptdemo.prompt.basic;

import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Dimension;
import com.rubyhuntersky.promptdemo.prompt.core.Observer;
import com.rubyhuntersky.promptdemo.prompt.core.OutcomeAdapter;
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

public class BasePrompt<P, O> implements Prompt<P, O> {

    private final OnPresent<P, O> onPresent;

    public BasePrompt(OnPresent<P, O> onPresent) {
        this.onPresent = onPresent;
    }

    public BasePrompt() {
        this.onPresent = null;
    }

    @Override
    public Prompt<P, O> inset(final Dimension... insets) {
        return new BasePrompt<>(new InsetOnPresent<>(this, insets));
    }

    @Override
    public <P2, O2, P3, O3> Prompt<P3, O3> carveBottom(final Dimension size, final Prompt<P2, O2> prompt,
          final OutcomeAdapter<O, O2, O3> adapter) {
        final BasePrompt<P, O> original = this;
        return new BasePrompt<>(new OnPresent<P3, O3>() {
            @Override
            public void present(final Presenter<P3, O3> presenter) {
                final Space originalSpace = presenter.getSpace();
                final float minorHeight = Math.round(
                      size.convert(originalSpace.perReadableY, originalSpace.perTappableY, originalSpace.height));
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
                }, new Observer<O>() {
                    @Override
                    public void onOutcome(O outcome) {
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
        });
    }

    @Override
    public Presentation<P> present(final Audience audience, final Observer<O> observer) {

        final Presenter<P, O> presenter = new Presenter<P, O>() {

            boolean isEnded = false;
            List<Presentation<?>> presentations = new ArrayList<>();

            @Override
            public void addPresentation(Presentation<?> presentation) {
                presentations.add(presentation);
            }

            @Override
            public void onOutcome(O outcome) {
                observer.onOutcome(outcome);
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
            public P getProgress() {
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

    public interface OnPresent<P, O> {
        void present(Presenter<P, O> presenter);
        List<Element> toElements(Document document);
    }

    public interface Presenter<P, O> extends Audience, Observer<O>, Presentation<P> {
        void addPresentation(Presentation<?> presentation);
    }

    private static class InsetOnPresent<P, O> implements OnPresent<P, O> {
        private final BasePrompt<P, O> previousPrompt;
        private final Dimension[] insets;

        public InsetOnPresent(BasePrompt<P, O> previousPrompt, Dimension... insets) {
            this.previousPrompt = previousPrompt;
            this.insets = insets;
        }

        @Override
        public void present(final Presenter<P, O> presenter) {
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
