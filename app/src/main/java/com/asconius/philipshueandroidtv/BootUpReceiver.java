package com.asconius.philipshueandroidtv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("onReceive", "BootUpReceiver.onReceive");
        new ScreenCaptureJobScheduler().scheduleJob(context);
    }
}
