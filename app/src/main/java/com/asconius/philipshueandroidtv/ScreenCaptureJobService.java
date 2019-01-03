package com.asconius.philipshueandroidtv;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.asconius.philipshueandroidtv.event.ImageRequestEvent;
import com.asconius.philipshueandroidtv.event.ScheduleJobEvent;

import org.greenrobot.eventbus.EventBus;

public class ScreenCaptureJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("onStartJob", "ScreenCaptureJobService.onStartJob");
        EventBus.getDefault().post(new ImageRequestEvent());
        EventBus.getDefault().post(new ScheduleJobEvent());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
