package com.asconius.philipshueandroidtv;

import android.content.Context;
import android.content.SharedPreferences;

public class HueSharedPreferences {
    private static final String HUE_SHARED_PREFERENCES_STORE = "HueSharedPrefs";
    private static final String LAST_CONNECTED_USERNAME = "LastConnectedUsername";
    private static final String LAST_CONNECTED_IP = "LastConnectedIP";
    private static HueSharedPreferences instance = null;
    private SharedPreferences mSharedPreferences = null;
    private SharedPreferences.Editor mSharedPreferencesEditor = null;

    public static HueSharedPreferences getInstance(Context ctx) {
        if (instance == null) {
            instance = new HueSharedPreferences(ctx);
        }
        return instance;
    }

    private HueSharedPreferences(Context appContext) {
        mSharedPreferences = appContext.getSharedPreferences(HUE_SHARED_PREFERENCES_STORE, 0);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    public String getUsername() {
        String username = mSharedPreferences.getString(LAST_CONNECTED_USERNAME, "");
        return username;
    }

    public boolean setUsername(String username) {
        mSharedPreferencesEditor.putString(LAST_CONNECTED_USERNAME, username);
        return (mSharedPreferencesEditor.commit());
    }

    public String getLastConnectedIPAddress() {
        return mSharedPreferences.getString(LAST_CONNECTED_IP, "");
    }

    public boolean setLastConnectedIPAddress(String ipAddress) {
        mSharedPreferencesEditor.putString(LAST_CONNECTED_IP, ipAddress);
        return (mSharedPreferencesEditor.commit());
    }
}
