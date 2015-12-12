package com.rubyhuntersky.promptdemo.prompt.basic;

import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.Color;
import com.rubyhuntersky.promptdemo.prompt.core.Region;
import com.rubyhuntersky.promptdemo.prompt.core.Observer;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Presentation;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public class ColorPrompt extends BasePrompt<Color, Void> {
    private final Color color;

    public ColorPrompt(Color color) {
        this.color = color;
    }

    @Override
    public Presentation<Color> present(Audience audience, Observer<Void> observer) {
        final Patch patch = audience.getPatch(this.color, Region.SPACE_ALL);

        final Presentation<Color> superPresentation = super.present(audience, observer);
        return new Presentation<Color>() {
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
            public Color getProgress() {
                return ColorPrompt.this.color;
            }
        };
    }
}
