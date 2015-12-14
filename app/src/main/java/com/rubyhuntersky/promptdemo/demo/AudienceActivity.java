package com.rubyhuntersky.promptdemo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rubyhuntersky.promptdemo.R;
import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Palette;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Region;

abstract public class AudienceActivity extends AppCompatActivity implements Audience {

    private PatchView patchView;

    abstract protected Palette getPalette();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        patchView = (PatchView) findViewById(R.id.audience);
        patchView.setPalette(getPalette());

    }

    @Override
    public Patch getPatch(ColorWell color, Region dimensions) {
        return patchView.addPatch(color, dimensions);
    }
}
