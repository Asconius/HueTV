package com.asconius.philipshueandroidtv;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

public class ScreenCaptureJobScheduler {

    private static final long MINIMUM_LATENCY = 40;
    private static final long MAX_EXECUTION_DELAY = 1000;

    public void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, ScreenCaptureJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(MINIMUM_LATENCY);
        builder.setOverrideDeadline(MAX_EXECUTION_DELAY);
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}