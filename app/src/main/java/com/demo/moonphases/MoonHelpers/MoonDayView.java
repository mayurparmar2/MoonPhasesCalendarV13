package com.demo.moonphases.MoonHelpers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.demo.moonphases.R;


public class MoonDayView extends View {
    private Paint fPaint;
    private Path fPath;
    private int lunation;
    private Paint mPaint;
    private Path mPath;
    private double moonPhase;
    private RectF outerBounds;

    public MoonDayView(Context context) {
        super(context);
        this.moonPhase = 0.0d;
        this.lunation = 0;
        prepareDrawable();
    }

    public MoonDayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.moonPhase = 0.0d;
        this.lunation = 0;
        prepareDrawable();
    }

    public void setPhaseLunation(double d, int i) {
        this.moonPhase = d;
        this.lunation = i;
        invalidate();
    }

    private void prepareDrawable() {
        Resources resources = getResources();
        this.mPath = new Path();
        this.fPath = new Path();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(getColor(resources, R.color.moonColor));
        this.mPaint.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.fPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.fPaint.setColor(getColor(resources, R.color.frameColor));
        this.fPaint.setAntiAlias(true);
        this.outerBounds = new RectF(0.0f, 0.0f, 256.0f, 256.0f);
    }

    private int getColor(Resources resources, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            return getContext().getColor(i);
        }
        return resources.getColor(i);
    }

    private void setupPath(int i, int i2, int i3) {
        float f = i2;
        float f2 = i2 + i3;
        this.outerBounds.set(i, f, i + i3, f2);
        this.mPath.reset();
        this.fPath.reset();
        int i4 = this.lunation;
        if (i4 >= 0) {
            if (i4 == 0) {
                this.fPath.addArc(this.outerBounds, 0.0f, 360.0f);
                this.fPath.close();
            } else if (i4 == 1) {
                this.mPath.addArc(this.outerBounds, 270.0f, 180.0f);
                this.mPath.close();
                this.fPath.set(this.mPath);
            } else if (i4 == 2) {
                this.mPath.addOval(this.outerBounds, Path.Direction.CCW);
                this.mPath.close();
                this.fPath.set(this.mPath);
            } else if (i4 == 3) {
                this.mPath.addArc(this.outerBounds, 90.0f, 180.0f);
                this.mPath.close();
                this.fPath.set(this.mPath);
            }
            this.fPaint.setStrokeWidth(i3 / 8);
            return;
        }
        double d = this.moonPhase;
        double floor = d - Math.floor(d);
        double d2 = this.moonPhase;
        int i5 = 180;
        int i6 = 270;
        int i7 = 90;
        if (d2 >= 2.0d) {
            if (d2 > 3.0d) {
                floor = 1.0d - floor;
                i5 = -180;
            }
            i6 = 90;
            i7 = 270;
        } else if (d2 >= 1.0d) {
            floor = 1.0d - floor;
        } else {
            i5 = -180;
        }
        this.mPath.addArc(this.outerBounds, i6, 180.0f);
        double d3 = i3;
        double cos = Math.cos(floor * 1.5707963267948966d) * d3;
        double d4 = i + ((d3 - cos) / 2.0d);
        this.outerBounds.set((float) d4, f, (float) (d4 + cos), f2);
        this.mPath.addArc(this.outerBounds, i7, i5);
        this.mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        int min = (Math.min(width, height) * 80) / 100;
        setupPath((width - min) / 2, (height - min) / 2, min);
        if (this.lunation >= 0) {
            canvas.drawPath(this.fPath, this.fPaint);
        }
        canvas.drawPath(this.mPath, this.mPaint);
    }
}
