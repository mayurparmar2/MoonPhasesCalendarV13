package com.demo.moonphases.common;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.demo.moonphases.R;

import javax.crypto.spec.SecretKeySpec;

import com.demo.moonphases.MainActivity;

public class TapToStartActivity extends AppCompatActivity {
    public static final String ACTION_CLOSE = "ACTION_CLOSE";
    private static String INSTALL_PREF = "install_pref";
    private static SecretKeySpec secret;
    Activity activity;
    CardView btn_start;
    Context context;
    PrefManager prefManager;
    TextView txt_privacyPolicy;
    private FirstReceiver firstReceiver;
    private Context mContext;



    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColorMain, getTheme()));
        } else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColorMain));
        }
        setContentView(R.layout.taptostart_activity);
        this.mContext = this;
        this.context = this;
        this.activity = this;
        this.prefManager = new PrefManager(this);

        this.btn_start = (CardView) findViewById(R.id.btn_start);
        this.txt_privacyPolicy = (TextView) findViewById(R.id.txt_privacy);
        this.txt_privacyPolicy.setText(Html.fromHtml("<a href='https://www.google.com'>Privacy Policy</a>"));
        this.txt_privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TapToStartActivity.this.mContext, Privacy_Policy_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                TapToStartActivity.this.startActivity(intent);
            }
        });
        this.btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TapToStartActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                TapToStartActivity.this.startActivity(intent);
            }
        });
        IntentFilter intentFilter = new IntentFilter(ACTION_CLOSE);
        FirstReceiver firstReceiver = new FirstReceiver();
        this.firstReceiver = firstReceiver;
        registerReceiver(firstReceiver, intentFilter);
        if (checkNewInstall()) {
            return;
        }
    }

    private boolean checkNewInstall() {
        Context context = this.mContext;
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        boolean z = sharedPreferences.getBoolean(INSTALL_PREF, false);
        if (!z) {
            sharedPreferences.edit().putBoolean(INSTALL_PREF, true).commit();
        }
        return z;
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ExitActivity.class));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.firstReceiver);
    }


    class FirstReceiver extends BroadcastReceiver {
        FirstReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("FirstReceiver", "FirstReceiver");
            if (intent.getAction().equals(TapToStartActivity.ACTION_CLOSE)) {
                TapToStartActivity.this.finish();
            }
        }
    }

}
