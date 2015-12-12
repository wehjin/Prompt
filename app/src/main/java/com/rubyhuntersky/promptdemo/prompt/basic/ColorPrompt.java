package com.rubyhuntersky.promptdemo.prompt.basic;

import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.Color;
import com.rubyhuntersky.promptdemo.prompt.core.Observer;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Presentation;
import com.rubyhuntersky.promptdemo.prompt.core.Prompt;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public class ColorPrompt implements Prompt<Color, Void> {
    private final Color color;

    public ColorPrompt(Color color) {
        this.color = color;
    }

    @Override
    public Presentation<Color> present(Audience audience, Observer<Void> observer) {
        final Patch patch = audience.getPatch(this.color);
        return new Presentation<Color>() {
            boolean isEnded = false;

            @Override
            public void end() {
                isEnded = true;
                patch.erase();
            }

            @Override
            public boolean isEnded() {
                return isEnded;
            }

            @Override
            public Color getProgress() {
                return ColorPrompt.this.color;
            }
        };
    }
}
