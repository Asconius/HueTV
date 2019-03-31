package com.asconius.huetv;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.graphics.Palette;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Button;

import com.asconius.huetv.event.ImageRequestEvent;
import com.asconius.huetv.event.ScheduleJobEvent;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends HueActivity {

    private static final String TAG = "HueTV";
    private MediaProjection mediaProjection;
    private MediaProjectionManager mediaProjectionManager;
    private ImageReader imageReader;
    private boolean isServiceRunning = false;
    private Button startButton;
    private Button stopButton;
    private ScreenCaptureJobScheduler screenCaptureJobScheduler;

    public void authorize(View view) {
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 1);
    }

    public void start(View view) {
        isServiceRunning = true;
        screenCaptureJobScheduler.scheduleJob(this);
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    public void stop(View view) {
        isServiceRunning = false;
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        EventBus.getDefault().register(this);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        screenCaptureJobScheduler = new ScreenCaptureJobScheduler();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "MainActivity.onActivityResult");
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
        startButton.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        Log.d("onDestroy", "MainActivity.onDestroy");
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onImageRequestEvent(ImageRequestEvent event) {
        captureImage();
    }

    @Subscribe
    public void onScheduleJobEvent(ScheduleJobEvent event) {
        if (isServiceRunning) {
            screenCaptureJobScheduler.scheduleJob(getApplicationContext());
        }
    }

    private void captureImage() {
        if (mediaProjection != null) {
            Log.d(TAG, "MainActivity.captureImage mediaProjection != null");
            DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
            imageReader = ImageReader.newInstance(metrics.widthPixels, metrics.heightPixels, PixelFormat.RGBA_8888, 1);
            imageReader.setOnImageAvailableListener(reader -> {
                Log.d(TAG, "MainActivity.OnImageAvailableListener.onImageAvailable ");
                Bitmap bitmap = Bitmap.createBitmap(640, 360, Bitmap.Config.ARGB_8888);
                PixelCopy.request(reader.getSurface(), bitmap, copyResult -> {
                    Log.d(TAG, "MainActivity.OnImageAvailableListener.onPixelCopyFinished " + copyResult);
                    if (PixelCopy.SUCCESS == copyResult) {
                        createPaletteAsync(bitmap);
                    }
                }, new Handler(Looper.getMainLooper()));
                reader.close();
            }, null);
            createVirtualDisplay();
        }
    }

    private void createPaletteAsync(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                Log.d("onGenerated", "MainActivity.PaletteAsyncListener.onGenerated");
                updateLightState(palette);
            }
        });
    }

    public void updateLightState(Palette palette) {
        PHBridge bridge = hueSDKDecorator.getSelectedBridge();
        if (bridge != null) {
            List<PHLight> allLights = bridge.getResourceCache().getAllLights();
            List<Palette.Swatch> swatchList = palette.getSwatches();
            Iterator<Palette.Swatch> iterator = swatchList.iterator();
            for (PHLight light : allLights) {
                HSB hsb = null;
                if (iterator.hasNext()) {
                    Palette.Swatch swatch = iterator.next();
                    hsb = new HSB(swatch.getRgb());
                }
                if (hsb != null) {
                    PHLightState lightState = new PHLightState();
                    lightState.setHue(hsb.getHue());
                    lightState.setSaturation(hsb.getSaturation());
                    lightState.setBrightness(hsb.getBrightness());
                    bridge.updateLightState(light, lightState, listener);
                }
            }
        }
        EventBus.getDefault().post(new ScheduleJobEvent());
    }

    private void createVirtualDisplay() {
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        Log.d("createVirtualDisplay", "MainActivity.createVirtualDisplay " + metrics);
        mediaProjection.createVirtualDisplay("MainActivity",
                metrics.widthPixels, metrics.heightPixels, metrics.densityDpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.getSurface(), null, null);
    }

    private final PHLightListener listener = new PHLightListener() {

        @Override
        public void onSuccess() {}

        @Override
        public void onStateUpdate(Map<String, String> arg0, List<PHHueError> arg1) {
            Log.w(TAG, "Light has updated");
        }

        @Override
        public void onError(int arg0, String arg1) {}

        @Override
        public void onReceivingLightDetails(PHLight arg0) {}

        @Override
        public void onReceivingLights(List<PHBridgeResource> arg0) {}

        @Override
        public void onSearchComplete() {}
    };
}
