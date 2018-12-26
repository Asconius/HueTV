package com.asconius.philipshueandroidtv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends Activity {

    private MediaProjection mediaProjection;
    private MediaProjectionManager mediaProjectionManager;
    private ImageReader imageReader;

    public void promptUser(View view) {
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w("onActivityResult", "MainActivity.onActivityResult");
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        Log.w("onDestroy", "MainActivity.onDestroy");
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onImageRequestEvent(ImageRequestEvent event) {
        captureImage();
    }

    private void captureImage() {
        if (mediaProjection != null) {
            Log.w("captureImage", "MainActivity.captureImage mediaProjection != null");
            DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
            imageReader = ImageReader.newInstance(metrics.widthPixels, metrics.heightPixels, PixelFormat.RGBA_8888, 1);
            imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    Image image = reader.acquireNextImage();
                    Log.w("onImageAvailable", "MainActivity.OnImageAvailableListener.onImageAvailable " + image);
                    image.close();
                }
            }, null);
            createVirtualDisplay();
        }
    }

    private VirtualDisplay createVirtualDisplay() {
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        Log.w("createVirtualDisplay", "MainActivity.createVirtualDisplay " + metrics);
        return mediaProjection.createVirtualDisplay("MainActivity",
                metrics.widthPixels, metrics.heightPixels, metrics.densityDpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.getSurface(), null, null);
    }
}
