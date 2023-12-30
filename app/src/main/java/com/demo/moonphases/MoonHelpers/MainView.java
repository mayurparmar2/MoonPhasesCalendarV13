package com.demo.moonphases.MoonHelpers;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import java.util.Calendar;


public class MainView extends LinearLayout implements GestureDetector.OnGestureListener {
    private static final String TAG = "MainView";
    private Calendar mDate;
    private GestureDetector mGestureDetector;
    private boolean mIsNorthernHemi;
    private MoonView mMoonView;

    public MainView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDate = Calendar.getInstance();
        this.mIsNorthernHemi = true;
        setFocusable(true);
        setOrientation(LinearLayout.VERTICAL);
        MoonView moonView = new MoonView(context, attributeSet);
        this.mMoonView = moonView;
        addView(moonView, new LinearLayout.LayoutParams(-1, -1));
        update();
        this.mGestureDetector = new GestureDetector(this);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public Bundle saveState() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mDate", this.mDate);
        bundle.putBoolean("mIsNorthernHemi", this.mIsNorthernHemi);
        return bundle;
    }

    public void restoreState(Bundle bundle) {
        this.mIsNorthernHemi = bundle.getBoolean("mIsNorthernHemi");
        this.mDate = (Calendar) bundle.getSerializable("mDate");
        update();
    }

    @Override
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 21) {
            previous();
            return true;
        } else if (i != 22) {
            return super.onKeyDown(i, keyEvent);
        } else {
            next();
            return true;
        }
    }

    public void next() {
        this.mDate.add(5, 1);
        update();
    }

    public void previous() {
        this.mDate.add(5, -1);
        update();
    }

    public void update() {
        this.mMoonView.update(this.mDate, this.mIsNorthernHemi);
    }

    public Calendar getDate() {
        return this.mDate;
    }

    public void setDate(Calendar calendar) {
        this.mDate = calendar;
        update();
    }

    public boolean isNorthernHemi() {
        return this.mIsNorthernHemi;
    }

    public void setNorthernHemi(boolean z) {
        this.mIsNorthernHemi = z;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        Log.i(TAG, "onFling");
        if (((int) motionEvent2.getX()) - ((int) motionEvent.getX()) < 0) {
            next();
            return false;
        }
        previous();
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
}
