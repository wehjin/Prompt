package com.rubyhuntersky.promptdemo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rubyhuntersky.promptdemo.R;
import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Palette;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Region;
import com.rubyhuntersky.promptdemo.prompt.core.Shape;
import com.rubyhuntersky.promptdemo.prompt.core.Space;

abstract public class AudienceActivity extends AppCompatActivity implements Audience {

    private PatchView patchView;

    abstract protected Palette getPalette();
    abstract protected void onSpaceChanged();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        patchView = (PatchView) findViewById(R.id.audience);
        patchView.setPalette(getPalette());
        patchView.setOnSpaceChanged(new PatchView.OnSpaceChanged() {
            @Override
            public void onSpaceChanged() {
                AudienceActivity.this.onSpaceChanged();
            }
        });
    }

    @Override
    public Space getSpace() {
        return patchView.getSpace();
    }


    @Override
    public Patch getPatch(ColorWell color, Region dimensions, Shape shape) {
        return patchView.addPatch(color, dimensions, shape);
    }
}
