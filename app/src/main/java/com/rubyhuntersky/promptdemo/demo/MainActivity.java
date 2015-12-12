package com.rubyhuntersky.promptdemo.demo;

import android.os.Bundle;

import com.rubyhuntersky.promptdemo.prompt.basic.ColorPrompt;
import com.rubyhuntersky.promptdemo.prompt.core.Color;
import com.rubyhuntersky.promptdemo.prompt.core.Presentation;

public class MainActivity extends AudienceActivity {

    private ColorPrompt colorPrompt;
    private Presentation presentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colorPrompt = new ColorPrompt(Color.BLUE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presentation = colorPrompt.present(this, null);
    }

    @Override
    protected void onStop() {
        presentation.end();
        super.onStop();
    }
}
