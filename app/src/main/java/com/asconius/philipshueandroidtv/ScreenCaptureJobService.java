package com.asconius.philipshueandroidtv;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class ScreenCaptureJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        EventBus.getDefault().post(new ImageRequestEvent());
        Log.d("onStartJob", "ScreenCaptureJobService.onStartJob");
        new ScreenCaptureJobScheduler().scheduleJob(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
