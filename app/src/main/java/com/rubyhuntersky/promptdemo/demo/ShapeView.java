package com.rubyhuntersky.promptdemo.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.rubyhuntersky.promptdemo.prompt.basic.TextlineShape;
import com.rubyhuntersky.promptdemo.prompt.core.Shape;

/**
 * @author wehjin
 * @since 12/16/15.
 */

public class ShapeView extends View {
    public static final String TAG = ShapeView.class.getSimpleName();
    private Shape shape = Shape.RECTANGLE;
    private int color;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private Path path = new Path();

    public ShapeView(Context context) {
        super(context);
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setShape(Shape shape) {
        this.shape = shape;
        invalidate();
    }

    public void setColor(int color) {
        this.color = color;
        if (shape == Shape.RECTANGLE) {
            setBackgroundColor(color);
        } else {
            setBackgroundColor(0x88ff0000);
            paint.setColor(color);
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (shape instanceof TextlineShape) {
            TextlineShape textLine = (TextlineShape) shape;
            final String text = textLine.text;
            paint.setTextSize(getHeight() * 1.41f);
            final float textWidth = paint.measureText(text);
            final float extraWidth = getWidth() - textWidth;
            final float x = extraWidth * ((TextlineShape) shape).anchor.x;
            final float y = getHeight();
            path.reset();
            paint.getTextPath(text, 0, text.length(), x, y, path);
            path.close();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (shape == Shape.RECTANGLE) {
            return;
        }
        canvas.drawPath(path, paint);
    }
}
