package com.demo.moonphases.common;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.moonphases.R;


public class ExitActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btnNo;
    LinearLayout btnYes;
    LinearLayout lin_rate_yes;
    private Context mContext;

    @Override
    public void onBackPressed() {
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            window.setStatusBarColor(getResources().getColor(R.color.start_color));
        }
        setContentView(R.layout.adview_layout_exit1);
        this.mContext = this;
        setContentView();
    }

    private void setContentView() {
        this.btnYes = (LinearLayout) findViewById(R.id.btnyes);
        this.btnNo = (LinearLayout) findViewById(R.id.btnno);
        this.lin_rate_yes = (LinearLayout) findViewById(R.id.lin_rate_yes);
        this.btnYes.setOnClickListener(this);
        this.btnNo.setOnClickListener(this);
        this.lin_rate_yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnno:
                finish();
                return;
            case R.id.btnyes:
                sendBroadcast(new Intent(TapToStartActivity.ACTION_CLOSE));
                finish();
                return;
            case R.id.lin_rate_yes:
                if (isOnline()) {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + this.mContext.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return;
                }
                Toast makeText = Toast.makeText(getApplicationContext(), "No Internet Connection..", Toast.LENGTH_SHORT);
                makeText.setGravity(17, 0, 0);
                makeText.show();
                return;
            default:
                return;
        }
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
