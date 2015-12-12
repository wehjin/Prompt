package com.rubyhuntersky.promptdemo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.rubyhuntersky.promptdemo.R;
import com.rubyhuntersky.promptdemo.prompt.core.AndroidColor;
import com.rubyhuntersky.promptdemo.prompt.core.Audience;
import com.rubyhuntersky.promptdemo.prompt.core.Color;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;

public class AudienceActivity extends AppCompatActivity implements Audience {

    private FrameLayout audienceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audienceView = (FrameLayout) findViewById(R.id.audience);
    }

    @Override
    public Patch getPatch(Color color) {
        final View view = new View(this);
        view.setBackgroundColor(getAndroidColor(color));
        audienceView.addView(view);
        return new Patch() {
            @Override
            public void erase() {
                audienceView.removeView(view);
            }
        };
    }

    private int getAndroidColor(Color color) {
        int red = (int) (255 * color.red);
        int green = (int) (255 * color.green);
        int blue = (int) (255 * color.blue);
        int alpha = (int) (255 * color.alpha);
        return AndroidColor.argb(alpha, red, green, blue);
    }
}
