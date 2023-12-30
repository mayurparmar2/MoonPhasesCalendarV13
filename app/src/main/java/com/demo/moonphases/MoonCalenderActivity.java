package com.demo.moonphases;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.text.DateFormatSymbols;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.demo.moonphases.MoonHelpers.MonthPickerFragment;
import com.demo.moonphases.MoonHelpers.MoonDayView;
import moon.phases.calendar.MoonHelpers.MoonphaseCalculator;
import com.demo.moonphases.common.PrefManager;
import com.demo.moonphases.common.Privacy_Policy_activity;


public class MoonCalenderActivity extends AppCompatActivity {
    private static final String STATE_MONTH = "month";
    private static final String STATE_YEAR = "year";
    private static final int[] dayNumberLabelIds = {R.id.dayNumberLabel00, R.id.dayNumberLabel01, R.id.dayNumberLabel02, R.id.dayNumberLabel03, R.id.dayNumberLabel04, R.id.dayNumberLabel05, R.id.dayNumberLabel06, R.id.dayNumberLabel10, R.id.dayNumberLabel11, R.id.dayNumberLabel12, R.id.dayNumberLabel13, R.id.dayNumberLabel14, R.id.dayNumberLabel15, R.id.dayNumberLabel16, R.id.dayNumberLabel20, R.id.dayNumberLabel21, R.id.dayNumberLabel22, R.id.dayNumberLabel23, R.id.dayNumberLabel24, R.id.dayNumberLabel25, R.id.dayNumberLabel26, R.id.dayNumberLabel30, R.id.dayNumberLabel31, R.id.dayNumberLabel32, R.id.dayNumberLabel33, R.id.dayNumberLabel34, R.id.dayNumberLabel35, R.id.dayNumberLabel36, R.id.dayNumberLabel40, R.id.dayNumberLabel41, R.id.dayNumberLabel42, R.id.dayNumberLabel43, R.id.dayNumberLabel44, R.id.dayNumberLabel45, R.id.dayNumberLabel46, R.id.dayNumberLabel50, R.id.dayNumberLabel51, R.id.dayNumberLabel52, R.id.dayNumberLabel53, R.id.dayNumberLabel54, R.id.dayNumberLabel55, R.id.dayNumberLabel56};
    private static final int[] moonDayViewIds = {R.id.moonDayView00, R.id.moonDayView01, R.id.moonDayView02, R.id.moonDayView03, R.id.moonDayView04, R.id.moonDayView05, R.id.moonDayView06, R.id.moonDayView10, R.id.moonDayView11, R.id.moonDayView12, R.id.moonDayView13, R.id.moonDayView14, R.id.moonDayView15, R.id.moonDayView16, R.id.moonDayView20, R.id.moonDayView21, R.id.moonDayView22, R.id.moonDayView23, R.id.moonDayView24, R.id.moonDayView25, R.id.moonDayView26, R.id.moonDayView30, R.id.moonDayView31, R.id.moonDayView32, R.id.moonDayView33, R.id.moonDayView34, R.id.moonDayView35, R.id.moonDayView36, R.id.moonDayView40, R.id.moonDayView41, R.id.moonDayView42, R.id.moonDayView43, R.id.moonDayView44, R.id.moonDayView45, R.id.moonDayView46, R.id.moonDayView50, R.id.moonDayView51, R.id.moonDayView52, R.id.moonDayView53, R.id.moonDayView54, R.id.moonDayView55, R.id.moonDayView56};
    
    ImageView ivCalendar;
    ImageView ivLeft;
    ImageView ivRight;
    
    PrefManager prefManager;
    
    Toolbar toolbar;
    AppCompatTextView toolbar_text;
    private TextView[] dayLabels;
    private GestureDetector gestureD;
    private GestureListener gestureL;
    private MoonphaseCalculator mPC;
    private GregorianCalendar monthShown;
    private MoonDayView[] moonViews;
    private int DoWoffset = 0;
    private ValueAnimator anim = null;

    private void gotoSettings() {
    }

    private void registerDayLabels() {
        this.dayLabels = new TextView[dayNumberLabelIds.length];
        int i = 0;
        while (true) {
            TextView[] textViewArr = this.dayLabels;
            if (i >= textViewArr.length) {
                return;
            }
            textViewArr[i] = (TextView) findViewById(dayNumberLabelIds[i]);
            i++;
        }
    }

    private void registerMoonViews() {
        this.moonViews = new MoonDayView[moonDayViewIds.length];
        int i = 0;
        while (true) {
            int[] iArr = moonDayViewIds;
            if (i >= iArr.length) {
                return;
            }
            this.moonViews[i] = (MoonDayView) findViewById(iArr[i]);
            i++;
        }
    }

    private void rotateDoWlabels() {
        int[] iArr = {R.id.sundayLabel, R.id.mondayLabel, R.id.tuesdayLabel, R.id.wednesdayLabel, R.id.thursdayLabel, R.id.fridayLabel, R.id.saturdayLabel};
        PreferenceManager.getDefaultSharedPreferences(this);
        String[] shortWeekdays = DateFormatSymbols.getInstance().getShortWeekdays();
        for (int i = 0; i < 7; i++) {
            ((TextView) findViewById(iArr[i])).setText(shortWeekdays[((this.DoWoffset + i) % 7) + 1]);
        }
    }

    public Bundle getNonPersonalizedAdsBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("npa", "1");
        return bundle;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_moon_calender);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_more));
        this.prefManager = new PrefManager(this);
        
        
        

        registerDayLabels();
        registerMoonViews();
        this.mPC = new MoonphaseCalculator();
        this.gestureL = new GestureListener();
        this.toolbar_text = (AppCompatTextView) findViewById(R.id.toolbar_text);
        this.ivCalendar = (ImageView) findViewById(R.id.ivCalendar);
        this.ivLeft = (ImageView) findViewById(R.id.ivLeft);
        this.ivRight = (ImageView) findViewById(R.id.ivRight);
        this.gestureD = new GestureDetector(this, this.gestureL);
        if (bundle != null) {
            this.monthShown = new GregorianCalendar(bundle.getInt(STATE_YEAR), bundle.getInt(STATE_MONTH), 1);
        } else {
            setToFirstDayThisMonth();
        }
        this.ivCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoonCalenderActivity.this.pickMonth();
            }
        });
        this.ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoonCalenderActivity.this.monthShown.add(2, 1);
                MoonCalenderActivity.this.refreshCalendar();
            }
        });
        this.ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoonCalenderActivity.this.monthShown.add(2, -1);
                MoonCalenderActivity.this.refreshCalendar();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        rotateDoWlabels();
        refreshCalendar();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(STATE_YEAR, this.monthShown.get(1));
        bundle.putInt(STATE_MONTH, this.monthShown.get(2));
        super.onSaveInstanceState(bundle);
    }

    private void setToFirstDayThisMonth() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        this.monthShown = gregorianCalendar;
        gregorianCalendar.set(5, gregorianCalendar.getActualMinimum(5));
    }

    private String getBestYearMonthFormat() {
        return DateFormat.getBestDateTimePattern(Locale.getDefault(), getResources().getString(R.string.month_year_skelton));
    }

    private void updateActionBarTitle(GregorianCalendar gregorianCalendar) {
        String string;
        if (Build.VERSION.SDK_INT >= 18) {
            string = getBestYearMonthFormat();
        } else {
            string = getResources().getString(R.string.month_year_format);
        }
        AppCompatTextView appCompatTextView = this.toolbar_text;
        appCompatTextView.setText("" + ((Object) DateFormat.format(string, gregorianCalendar)));
    }

    private int calcHighlightDay(GregorianCalendar gregorianCalendar) {
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        if (gregorianCalendar2.get(2) == gregorianCalendar.get(2) && gregorianCalendar2.get(1) == gregorianCalendar.get(1)) {
            return gregorianCalendar2.get(5);
        }
        return -1;
    }

    public void refreshCalendar() {
        int i = this.monthShown.get(1);
        if (i < 1923) {
            Toast.makeText(this, (int) R.string.before_2001, Toast.LENGTH_SHORT).show();
            this.monthShown.set(1923, 0, 1);
        }
        if (MoonphaseCalculator.getSizeOfTimet() < 8 && i > 2037) {
            this.monthShown.set(1, 2037);
        }
        updateActionBarTitle(this.monthShown);
        this.mPC.calc(this.monthShown.get(1), (this.monthShown.get(2) - 0) + 1);
        int actualMinimum = this.monthShown.getActualMinimum(5);
        int actualMaximum = this.monthShown.getActualMaximum(5);
        this.monthShown.set(5, actualMinimum);
        int i2 = ((this.monthShown.get(7) - 1) - this.DoWoffset) % 7;
        int i3 = 0;
        while (i3 < i2) {
            this.dayLabels[i3].setVisibility(View.INVISIBLE);
            this.moonViews[i3].setVisibility(View.INVISIBLE);
            i3++;
        }
        Resources resources = getResources();
        int color = getColor(resources, R.color.highlight);
        int color2 = getColor(resources, R.color.ordinaryday);
        int calcHighlightDay = calcHighlightDay(this.monthShown);
        while (actualMinimum <= actualMaximum) {
            int i4 = calcHighlightDay == actualMinimum ? color : color2;
            this.dayLabels[i3].setBackgroundColor(i4);
            this.dayLabels[i3].setText(Integer.toString(actualMinimum));
            this.dayLabels[i3].setVisibility(View.VISIBLE);
            this.moonViews[i3].setBackgroundColor(i4);
            int i5 = actualMinimum - 1;
            this.moonViews[i3].setPhaseLunation(this.mPC.phases[i5], this.mPC.lunations[i5]);
            this.moonViews[i3].setVisibility(View.VISIBLE);
            i3++;
            actualMinimum++;
        }
        while (true) {
            TextView[] textViewArr = this.dayLabels;
            if (i3 >= textViewArr.length) {
                return;
            }
            textViewArr[i3].setVisibility(View.INVISIBLE);
            this.moonViews[i3].setVisibility(View.INVISIBLE);
            i3++;
        }
    }

    private int getColor(Resources resources, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            return getColor(i);
        }
        return resources.getColor(i);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = this.gestureD.onTouchEvent(motionEvent);
        boolean z = false;
        if (motionEvent.getAction() == 1) {
            final View findViewById = findViewById(R.id.baseview);
            int offset = this.gestureL.getOffset();
            if (Math.abs(offset) > getResources().getDimensionPixelSize(R.dimen.scroll_thresh)) {
                this.monthShown.add(2, offset > 0 ? -1 : 1);
                refreshCalendar();
                ValueAnimator valueAnimator = this.anim;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                } else {
                    ValueAnimator valueAnimator2 = new ValueAnimator();
                    this.anim = valueAnimator2;
                    valueAnimator2.setDuration(100L);
                    this.anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator3) {
                            findViewById.setX(((Integer) valueAnimator3.getAnimatedValue()).intValue());
                        }
                    });
                }
                int width = findViewById.getWidth();
                ValueAnimator valueAnimator3 = this.anim;
                int[] iArr = new int[2];
                if (offset > 0) {
                    width = -width;
                }
                iArr[0] = width;
                iArr[1] = 0;
                valueAnimator3.setIntValues(iArr);
                this.anim.start();
            } else {
                findViewById.setX(0.0f);
            }
            this.gestureL.resetOffset();
            z = true;
        }
        return onTouchEvent | z;
    }

    @Override
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 21) {
            this.monthShown.add(2, -1);
        } else if (i == 22) {
            this.monthShown.add(2, 1);
        } else if (i != 100) {
            return super.onKeyDown(i, keyEvent);
        } else {
            pickMonth();
        }
        refreshCalendar();
        return true;
    }

    public void pickMonth() {
        MonthPickerFragment monthPickerFragment = new MonthPickerFragment();
        monthPickerFragment.setMonth(this.monthShown.get(2), this.monthShown.get(1));
        monthPickerFragment.setOnMonthPickedListener(new MonthPickerFragment.OnMonthPickedListener() {
            @Override
            public void onMonthPicked(int i, int i2) {
                MoonCalenderActivity.this.monthShown.set(1, i);
                MoonCalenderActivity.this.monthShown.set(2, i2);
                MoonCalenderActivity.this.refreshCalendar();
            }
        });
        monthPickerFragment.show(getFragmentManager(), "monthPicker");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;


            case R.id.privacy:
                Intent intent3 = new Intent(getApplicationContext(), Privacy_Policy_activity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);
                return true;
            case R.id.rate:
                if (isOnline()) {
                    Intent intent4 = new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName()));
                    intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent4);
                } else {
                    Toast makeText = Toast.makeText(getApplicationContext(), "No Internet Connection..", Toast.LENGTH_SHORT);
                    makeText.setGravity(17, 0, 0);
                    makeText.show();
                }
                return true;
            case R.id.share:
                if (isOnline()) {
                    Intent intent5 = new Intent("android.intent.action.SEND");
                    intent5.setType("text/plain");
                    intent5.putExtra("android.intent.extra.TEXT", "Hi! I'm using a Phases Of The Moon - Calendar Application. Check it out:http://play.google.com/store/apps/details?id=" + getPackageName());
                    intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(Intent.createChooser(intent5, "Share with Friends"));
                } else {
                    Toast makeText2 = Toast.makeText(getApplicationContext(), "No Internet Connection..", Toast.LENGTH_SHORT);
                    makeText2.setGravity(17, 0, 0);
                    makeText2.show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private int offset;

        private GestureListener() {
            this.offset = 0;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            this.offset = (int) (motionEvent2.getX() - motionEvent.getX());
            MoonCalenderActivity.this.findViewById(R.id.baseview).setX(this.offset);
            return true;
        }

        public int getOffset() {
            return this.offset;
        }

        public void resetOffset() {
            this.offset = 0;
        }
    }
}
