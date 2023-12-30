package com.demo.moonphases;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.demo.moonphases.MoonHelpers.MainView;
import com.demo.moonphases.common.PrefManager;
import com.demo.moonphases.common.Privacy_Policy_activity;


public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static TextView tvMainPhase;
    final Context context = this;
    Activity activity;
    
    

    MainView mainPageView;
    LinearLayout moonWallpaper;
    LinearLayout mooncalender;
    RelativeLayout moonphases;
    PrefManager prefManager;
    
    Toolbar toolbar;

    public Bundle getNonPersonalizedAdsBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("npa", "1");
        return bundle;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColorMain, getTheme()));
        } else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColorMain));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_more));
        this.prefManager = new PrefManager(this);
        
        
        
        
        this.activity = this;
        this.moonphases = (RelativeLayout) findViewById(R.id.moonphases);
        this.mooncalender = (LinearLayout) findViewById(R.id.mooncallender);
        this.moonWallpaper = (LinearLayout) findViewById(R.id.moonWallpaper);
        tvMainPhase = (TextView) findViewById(R.id.tvMainPhase);
        this.mainPageView = (MainView) findViewById(R.id.mainPageView);
        if (bundle != null) {
            Bundle bundle2 = bundle.getBundle("MoonPhase");
            if (bundle2 != null) {
                this.mainPageView.restoreState(bundle2);
                return;
            }
            return;
        }
        this.moonphases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MoonPhaseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.mooncalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, MoonCalenderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.moonWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WallpaperActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    private boolean isNorthernHemi() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION");
        }
        Location lastKnownLocation = ((LocationManager) getSystemService(Context.LOCATION_SERVICE)).getLastKnownLocation("network");
        return lastKnownLocation != null && lastKnownLocation.getLongitude() > 0.0d;
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1 && iArr.length > 0) {
            int i2 = iArr[0];
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                this.mainPageView.setNorthernHemi(isNorthernHemi());
                this.mainPageView.update();
                return;
            }
            requestPermission();
            return;
        }
        this.mainPageView.setNorthernHemi(isNorthernHemi());
        this.mainPageView.update();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
