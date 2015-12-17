package com.rubyhuntersky.promptdemo.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import com.rubyhuntersky.promptdemo.prompt.basic.TextlineShape;
import com.rubyhuntersky.promptdemo.prompt.core.AndroidColor;
import com.rubyhuntersky.promptdemo.prompt.core.Color;
import com.rubyhuntersky.promptdemo.prompt.core.ColorWell;
import com.rubyhuntersky.promptdemo.prompt.core.Palette;
import com.rubyhuntersky.promptdemo.prompt.core.Patch;
import com.rubyhuntersky.promptdemo.prompt.core.Region;
import com.rubyhuntersky.promptdemo.prompt.core.Shape;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wehjin
 * @since 12/12/15.
 */

public class PatchView extends FrameLayout {

    private float readingPixels;
    private float tappingPixels;
    private Map<SuperPatch, View> patchViews = new HashMap<>();
    private Palette palette;

    public PatchView(Context context) {
        super(context);
        init();
    }

    public PatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    private void init() {
        readingPixels = getPixelsFromDp(12);
        tappingPixels = getPixelsFromDp(48);
    }

    public Patch addPatch(final ColorWell colorWell, final Region dimensions, final Shape shape) {
        final ShapeView view = new ShapeView(getContext());
        view.setShape(shape);
        final SuperPatch patch = new SuperPatch() {
            @Override
            public void updateView() {
                view.setColor(getAndroidColor(colorWell));
                view.setLayoutParams(getPatchLayoutParams(dimensions, shape));
            }

            @Override
            public void erase() {
                PatchView.this.removeView(view);
                patchViews.remove(this);
            }
        };
        patch.updateView();
        patchViews.put(patch, view);
        addView(view);
        return patch;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
        for (SuperPatch patch : patchViews.keySet()) {
            patch.updateView();
        }
    }

    @NonNull
    private FrameLayout.LayoutParams getPatchLayoutParams(Region dimensions, Shape shape) {
        final int frameWidth = getWidth();
        final int frameHeight = getHeight();
        int left = (int) dimensions.getLeft().convert(readingPixels, tappingPixels, frameWidth);
        int top = (int) dimensions.getTop().convert(readingPixels, tappingPixels, frameHeight);
        int width = (int) dimensions.getWidth().convert(readingPixels, tappingPixels, frameWidth);
        int height = (int) dimensions.getHeight().convert(readingPixels, tappingPixels, frameHeight);
        if (shape instanceof TextlineShape) {
            height *= 1.5;
        }
        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.leftMargin = left;
        params.topMargin = top;
        return params;
    }

    private float getPixelsFromDp(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int getAndroidColor(ColorWell colorWell) {
        final Color color = colorWell.getColor(palette);
        int red = (int) (255 * color.red);
        int green = (int) (255 * color.green);
        int blue = (int) (255 * color.blue);
        int alpha = (int) (255 * color.alpha);
        return AndroidColor.argb(alpha, red, green, blue);
    }

    interface SuperPatch extends Patch {
        void updateView();
    }
}
