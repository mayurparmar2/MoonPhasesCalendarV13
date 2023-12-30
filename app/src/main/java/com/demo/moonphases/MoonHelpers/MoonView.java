package com.demo.moonphases.MoonHelpers;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.moonphases.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.demo.moonphases.MainActivity;
import com.demo.moonphases.MoonPhaseActivity;


public class MoonView extends LinearLayout {
    private static final String DATE_FORMAT = "EEEE, MMMM d yyyy";
    private static final int[] IMAGE_LOOKUP = {R.drawable.moon0, R.drawable.moon1, R.drawable.moon2, R.drawable.moon3, R.drawable.moon4, R.drawable.moon5, R.drawable.moon6, R.drawable.moon7, R.drawable.moon8, R.drawable.moon9, R.drawable.moon10, R.drawable.moon11, R.drawable.moon12, R.drawable.moon13, R.drawable.moon14, R.drawable.moon15, R.drawable.moon16, R.drawable.moon17, R.drawable.moon18, R.drawable.moon19, R.drawable.moon20, R.drawable.moon21, R.drawable.moon22, R.drawable.moon23, R.drawable.moon24, R.drawable.moon25, R.drawable.moon26, R.drawable.moon27, R.drawable.moon28, R.drawable.moon29};
    private static final double MOON_PHASE_LENGTH = 29.530588853d;
    private static final String TAG = "MoonView";
    public Animation nextInAnimation;
    public Animation nextOutAnimation;
    public Animation previousInAnimation;
    public Animation previousOutAnimation;
    private SimpleDateFormat dateFormat;
    private Calendar mCalendar;
    private TextView mDateText;
    private boolean mIsNorthernHemi;
    private ImageView mMoonImage;
    private TextView mPhaseText;

    public MoonView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
        this.mCalendar = Calendar.getInstance();
        this.mIsNorthernHemi = true;
        setOrientation(1);
        this.mPhaseText = new TextView(context);
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        this.mPhaseText.setGravity(1);
        addView(this.mPhaseText, layoutParams);
        this.mMoonImage = new ImageView(context);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, 0);
        layoutParams2.weight = 1.0f;
        addView(this.mMoonImage, layoutParams2);
        this.mDateText = new TextView(context);
        ViewGroup.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
        this.mDateText.setGravity(1);
        addView(this.mDateText, layoutParams3);
    }

    private int getPhaseText(int i) {
        return i == 0 ? R.string.new_moon : (i <= 0 || i >= 7) ? i == 7 ? R.string.first_quarter : (i <= 7 || i >= 15) ? i == 15 ? R.string.full_moon : (i <= 15 || i >= 23) ? i == 23 ? R.string.third_quarter : R.string.waning_crescent : R.string.waning_gibbous : R.string.waxing_gibbous : R.string.waxing_crescent;
    }

    public void update(Calendar calendar, boolean z) {
        this.mCalendar = calendar;
        this.mIsNorthernHemi = z;
        double computeMoonPhase = computeMoonPhase();
        Log.i(TAG, "Computed moon phase: " + computeMoonPhase);
        int floor = ((int) Math.floor(computeMoonPhase)) % 30;
        Log.i(TAG, "Discrete phase value: " + floor);
        this.mPhaseText.setText(getPhaseText(floor));
        this.mPhaseText.setVisibility(View.INVISIBLE);
        if (MoonPhaseActivity.tvPhaseText != null) {
            TextView textView = MoonPhaseActivity.tvPhaseText;
            textView.setText("" + this.mPhaseText.getText().toString().trim());
        }
        if (MainActivity.tvMainPhase != null) {
            TextView textView2 = MainActivity.tvMainPhase;
            textView2.setText("" + this.mPhaseText.getText().toString().trim());
        }
        this.mMoonImage.setImageResource(IMAGE_LOOKUP[floor]);
        this.mDateText.setText(this.dateFormat.format(this.mCalendar.getTime()));
        this.mDateText.setVisibility(View.INVISIBLE);
        if (MoonPhaseActivity.tvPhaseDay != null) {
            TextView textView3 = MoonPhaseActivity.tvPhaseDay;
            textView3.setText("" + this.dateFormat.format(this.mCalendar.getTime()));
        }
        invalidate();
    }

    private double computeMoonPhase() {
        int i = this.mCalendar.get(1);
        int i2 = this.mCalendar.get(2) + 1;
        int i3 = this.mCalendar.get(5);
        double d = i;
        double floor = Math.floor((12 - i2) / 10);
        Double.isNaN(d);
        double d2 = d - floor;
        Log.i(TAG, "transformedYear: " + d2);
        int i4 = i2 + 9;
        if (i4 >= 12) {
            i4 -= 12;
        }
        Log.i(TAG, "transformedMonth: " + i4);
        double floor2 = Math.floor((4712.0d + d2) * 365.25d);
        double d3 = (double) i4;
        Double.isNaN(d3);
        double floor3 = Math.floor((d3 * 30.6d) + 0.5d);
        double floor4 = Math.floor(Math.floor((d2 / 100.0d) + 49.0d) * 0.75d) - 38.0d;
        double d4 = i3;
        Double.isNaN(d4);
        double d5 = floor2 + floor3 + d4 + 59.0d;
        if (d5 > 2299160.0d) {
            d5 -= floor4;
        }
        Log.i(TAG, "intermediate: " + d5);
        double d6 = (d5 - 2451550.1d) / MOON_PHASE_LENGTH;
        double floor5 = d6 - Math.floor(d6);
        if (floor5 < 0.0d) {
            floor5 += 1.0d;
        }
        Log.i(TAG, "normalizedPhase: " + floor5);
        return floor5 * MOON_PHASE_LENGTH;
    }
}
