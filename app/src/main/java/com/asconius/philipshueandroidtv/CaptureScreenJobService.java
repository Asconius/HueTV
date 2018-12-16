package com.asconius.philipshueandroidtv;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.v17.leanback.widget.Util;
import android.util.Log;

public class CaptureScreenJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.w("onStartJob", "CaptureScreenJobService.onStartJob");
        new ScreenCaptureJobScheduler().scheduleJob(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
