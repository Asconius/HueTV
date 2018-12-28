package com.asconius.philipshueandroidtv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.nio.ByteBuffer;

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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w("onActivityResult", "MainActivity.onActivityResult");
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
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
                    Bitmap bitmap = convert(image);
                    image.close();
                    reader.close();
                }
            }, null);
            createVirtualDisplay();
        }
    }

    public Bitmap convert(Image image) {
        if(image == null) return null;
        int width = image.getWidth();
        int height = image.getHeight();
        final Image.Plane[] planes = image.getPlanes();
        final ByteBuffer buffer = planes[0].getBuffer();
        int pixelStride = planes[0].getPixelStride();
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * width;
        Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        Log.w("convert", "MainActivity.convert " + bitmap);
        image.close();
        return bitmap;
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
