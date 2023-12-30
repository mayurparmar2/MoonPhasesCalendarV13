package com.demo.moonphases;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

import com.demo.moonphases.MoonHelpers.MainView;
import com.demo.moonphases.common.PrefManager;
import com.demo.moonphases.common.Privacy_Policy_activity;


public class MoonPhaseActivity extends AppCompatActivity {
    private static final String TAG = "MoonPhase";
    public static TextView tvPhaseDay;
    public static TextView tvPhaseText;
    private static String ICICLE_KEY = "MoonPhase";
    
    LinearLayout btnChooseDate;
    LinearLayout btnTodayPhase;
    
    PrefManager prefManager;
    
    Toolbar toolbar;
    private MainView mMainView;
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(i, i2, i3);
            MoonPhaseActivity.this.mMainView.setDate(calendar);
        }
    };

    public Bundle getNonPersonalizedAdsBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("npa", "1");
        return bundle;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_moon_phase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_more));
        this.prefManager = new PrefManager(this);
        
        
        

        tvPhaseDay = (TextView) findViewById(R.id.tvPhaseDay);
        tvPhaseText = (TextView) findViewById(R.id.tvPhaseText);
        this.btnTodayPhase = (LinearLayout) findViewById(R.id.btnTodayPhase);
        this.btnChooseDate = (LinearLayout) findViewById(R.id.btnChooseDate);
        MainView mainView = (MainView) findViewById(R.id.main_view);
        this.mMainView = mainView;
        if (bundle != null) {
            Bundle bundle2 = bundle.getBundle(ICICLE_KEY);
            if (bundle2 != null) {
                Log.i(TAG, "Resotring saved instance state.");
                this.mMainView.restoreState(bundle2);
                return;
            }
            return;
        }
        mainView.setNorthernHemi(isNorthernHemi());
        this.mMainView.update();
        this.btnTodayPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoonPhaseActivity.this.mMainView.setDate(Calendar.getInstance());
            }
        });
        this.btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoonPhaseActivity.this.showDialog(1);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Log.i(TAG, "Saving instance state.");
        bundle.putBundle(ICICLE_KEY, this.mMainView.saveState());
    }

    @Override
    public Dialog onCreateDialog(int i) {
        if (i != 1) {
            return null;
        }
        Calendar date = this.mMainView.getDate();
        return new DatePickerDialog(this, this.mDateSetListener, date.get(1), date.get(2), date.get(5));
    }

    @Override
    public void onPrepareDialog(int i, Dialog dialog) {
        if (i == 1) {
            Calendar date = this.mMainView.getDate();
            ((DatePickerDialog) dialog).updateDate(date.get(1), date.get(2), date.get(5));
        }
    }

    private boolean isNorthernHemi() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION");
        }
        Location lastKnownLocation = ((LocationManager) getSystemService(Context.LOCATION_SERVICE)).getLastKnownLocation("network");
        return lastKnownLocation != null && lastKnownLocation.getLongitude() > 0.0d;
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
}
