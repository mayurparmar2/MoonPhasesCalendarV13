package com.demo.moonphases.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Process;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.moonphases.R;


public class SplashActivity extends AppCompatActivity {
    private static final long COUNTER_TIME = 5;
    PrefManager prefManager;
    ProgressBar progressBar;
    


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        setContentView(R.layout.splashactivity);
        this.prefManager = new PrefManager(this);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.progressBar = progressBar;
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.MULTIPLY);
        if (isOnline()) {
            ContinueWithoutAdsProcess();

        } else {
            ContinueWithoutAdsProcess();
        }
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void ContinueAdsProcess() {
        if (!this.prefManager.getvalue()) {
            if (FlyAppsLLC_Const.isActive_adMob) {
                createTimer(COUNTER_TIME);
                return;
            } else {
                HomeScreen();
                return;
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.HomeScreen();
            }
        }, 3000L);
    }

    private void createTimer(long j) {
        new CountDownTimer(j * 1000, 1000L) {
            @Override
            public void onTick(long j2) {
            }

            @Override
            public void onFinish() {
                SplashActivity.this.HomeScreen();
            }
        }.start();
    }

    public void ContinueWithoutAdsProcess() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.HomeScreen();
            }
        }, 3000L);
    }

    public void HomeScreen() {
        startActivity(new Intent(this, TapToStartActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ExitApp();
    }

    public void ExitApp() {
        moveTaskToBack(true);
        finish();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
