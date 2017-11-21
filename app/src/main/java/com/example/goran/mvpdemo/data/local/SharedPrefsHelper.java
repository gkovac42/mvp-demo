package com.example.goran.mvpdemo.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by Goran on 21.11.2017..
 */

public class SharedPrefsHelper {

    private static SharedPrefsHelper instance = null;

    private SharedPreferences prefs;

    public static SharedPrefsHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefsHelper(context.getApplicationContext());
        }
        return instance;
    }

    private SharedPrefsHelper(Context context) {
        prefs = context
                .getApplicationContext()
                .getSharedPreferences("factory", Context.MODE_PRIVATE);
    }


    // save update time using shared prefs
    public void setLastUpdateTime() {

        long lastUpdateTime = Calendar.getInstance().getTimeInMillis();
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putLong("update_time", lastUpdateTime);
        prefsEditor.apply();
    }

    // check if 5 min passed
    public boolean timeToUpdate() {

        long lastUpdateTime = prefs.getLong("update_time", 0);
        long currentTime = Calendar.getInstance().getTimeInMillis();
        return currentTime - lastUpdateTime > 5 * 60 * 1000; // 5min
    }
}
