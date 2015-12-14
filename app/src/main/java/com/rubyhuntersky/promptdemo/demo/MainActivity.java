package com.rubyhuntersky.promptdemo.demo;

import android.os.Bundle;

import com.rubyhuntersky.promptdemo.prompt.basic.ColorPrompt;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Dimension;
import com.rubyhuntersky.promptdemo.prompt.core.Palette;
import com.rubyhuntersky.promptdemo.prompt.core.Presentation;
import com.rubyhuntersky.promptdemo.prompt.core.Prompt;

public class MainActivity extends AudienceActivity {

    private Prompt<?, ?> prompt;
    private Presentation presentation;
    private final Palette palette = new MainPalette();

    @Override
    protected Palette getPalette() {
        return palette;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ColorPrompt prompt = new ColorPrompt(ColorWell.PRIMARY);
        this.prompt = prompt.inset(Dimension.TAPPABLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presentation = prompt.present(this, null);
    }

    @Override
    protected void onStop() {
        presentation.end();
        super.onStop();
    }

}
