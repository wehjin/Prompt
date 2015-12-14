package com.rubyhuntersky.promptdemo.prompt.basic;

import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Observer;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Presentation;
import com.rubyhuntersky.promptdemo.prompt.core.Region;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public class ColorPrompt extends BasePrompt<ColorWell, Void> {
    private final ColorWell colorWell;

    public ColorPrompt(ColorWell color) {
        this.colorWell = color;
    }

    @Override
    public Presentation<ColorWell> present(Audience audience, Observer<Void> observer) {
        final Patch patch = audience.getPatch(this.colorWell, Region.SPACE_ALL);

        final Presentation<ColorWell> superPresentation = super.present(audience, observer);
        return new Presentation<ColorWell>() {
            @Override
            public void end() {
                patch.erase();
                superPresentation.end();
            }

            @Override
            public boolean isEnded() {
                return superPresentation.isEnded();
            }

            @Override
            public ColorWell getProgress() {
                return ColorPrompt.this.colorWell;
            }
        };
    }
}
