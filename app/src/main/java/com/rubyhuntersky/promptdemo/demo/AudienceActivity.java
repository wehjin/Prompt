package com.rubyhuntersky.promptdemo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rubyhuntersky.promptdemo.R;
import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.Color;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Region;

public class AudienceActivity extends AppCompatActivity implements Audience {

    private PatchView patchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        patchView = (PatchView) findViewById(R.id.audience);

    }

    @Override
    public Patch getPatch(Color color, Region dimensions) {
        return patchView.addPatch(color, dimensions);
    }
}
