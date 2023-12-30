package com.demo.moonphases.common;

import android.app.Application;
import android.content.SharedPreferences;


public class FlyAppsLLC_Const extends Application {
    public static boolean isActive_adMob = true;
    SharedPreferences.Editor myEdit;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = getSharedPreferences("fly_moonphases", 0);
        this.sharedPreferences = sharedPreferences;
        this.myEdit = sharedPreferences.edit();
    }
}
