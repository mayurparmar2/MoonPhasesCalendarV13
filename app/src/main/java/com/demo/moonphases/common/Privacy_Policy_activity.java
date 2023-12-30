package com.demo.moonphases.common;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.demo.moonphases.R;
import com.google.android.material.tabs.TabLayout;


public class Privacy_Policy_activity extends AppCompatActivity {
    TabPagerAdapter adapter;
    Context context;
    ViewPager pager;
    TabLayout tabs;
    Toolbar toolbar;
    CharSequence[] Titles = {"PRIVACY POLICY", "PERMISSION"};
    int totaltabs = 2;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            window.setStatusBarColor(getResources().getColor(R.color.pp_color));
        }
        setContentView(R.layout.privacy_policy_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.context = this;
        this.pager = (ViewPager) findViewById(R.id.viewpager);
        this.tabs = (TabLayout) findViewById(R.id.tabs);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), this.Titles, this.totaltabs);
        this.adapter = tabPagerAdapter;
        this.pager.setAdapter(tabPagerAdapter);
        this.tabs.setupWithViewPager(this.pager);
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

    public class TabPagerAdapter extends FragmentStatePagerAdapter {
        CharSequence[] Titles;
        int totaltabs;

        public TabPagerAdapter(FragmentManager fragmentManager, CharSequence[] charSequenceArr, int i) {
            super(fragmentManager);
            this.Titles = charSequenceArr;
            this.totaltabs = i;
        }

        @Override
        public Fragment getItem(int i) {
            if (i != 0) {
                if (i != 1) {
                    return null;
                }
                return new Permission_Fragment();
            }
            return new Privacy_Policy_Fragment();
        }

        @Override
        public CharSequence getPageTitle(int i) {
            return this.Titles[i];
        }

        @Override
        public int getCount() {
            return this.totaltabs;
        }
    }
}
