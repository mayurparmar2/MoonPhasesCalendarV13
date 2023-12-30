package com.demo.moonphases;

import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

import com.demo.moonphases.Adapter.WallpaperAdapter;
import com.demo.moonphases.OnClickInterface.OnItemClickListener;
import com.demo.moonphases.common.PrefManager;
import com.demo.moonphases.common.Privacy_Policy_activity;


public class WallpaperActivity extends AppCompatActivity implements OnItemClickListener {
    
    
    PrefManager prefManager;
    RecyclerView rvWallpapers;
    
    Toolbar toolbar;
    WallpaperAdapter wallpaperAdapter;
    int[] wallpaperList = {R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5, R.drawable.bg_6, R.drawable.bg_7, R.drawable.bg_8, R.drawable.bg_9, R.drawable.bg_10, R.drawable.bg_11, R.drawable.bg_12, R.drawable.bg_13, R.drawable.bg_14, R.drawable.bg_15, R.drawable.bg_16, R.drawable.bg_17, R.drawable.bg_18, R.drawable.bg_19, R.drawable.bg_20};

    public Bundle getNonPersonalizedAdsBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("npa", "1");
        return bundle;
    }




    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_wallpaper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_more));
        this.prefManager = new PrefManager(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvWallpapers);
        this.rvWallpapers = recyclerView;
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.rvWallpapers.setLayoutManager(new GridLayoutManager(this, 2));
        WallpaperAdapter wallpaperAdapter = new WallpaperAdapter(this.wallpaperList, this, this);
        this.wallpaperAdapter = wallpaperAdapter;
        this.rvWallpapers.setAdapter(wallpaperAdapter);
    }

    @Override
    public void OnClick(View view, final int i) {
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_lock_screen);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(true);
        ((LinearLayout) dialog.findViewById(R.id.btnHomeScreen)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                try {
                    dialog.dismiss();
                    wallpaperManager.setResource(WallpaperActivity.this.wallpaperList[i]);
                    Toast makeText = Toast.makeText(WallpaperActivity.this, "Home Screen Wallpaper Set.", Toast.LENGTH_SHORT);
                    makeText.setGravity(17, 0, 0);
                    makeText.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        ((LinearLayout) dialog.findViewById(R.id.btnLockScreen)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                try {
                    if (Build.VERSION.SDK_INT >= 24) {
                        dialog.dismiss();
                        wallpaperManager.setResource(WallpaperActivity.this.wallpaperList[i], WallpaperManager.FLAG_LOCK);
                        Toast makeText = Toast.makeText(WallpaperActivity.this, "Lock Screen Wallpaper Set.", Toast.LENGTH_SHORT);
                        makeText.setGravity(17, 0, 0);
                        makeText.show();
                    } else {
                        dialog.dismiss();
                        Toast makeText2 = Toast.makeText(WallpaperActivity.this, "Lock screen wallpaper not supported.", Toast.LENGTH_SHORT);
                        makeText2.setGravity(17, 0, 0);
                        makeText2.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog.show();
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
