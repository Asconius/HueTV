package com.asconius.philipshueandroidtv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends Activity {

    public static final String TAG = "PhilipsHueAndroidTV";
    private MediaProjection mediaProjection;
    private MediaProjectionManager mediaProjectionManager;
    private ImageReader imageReader;
    private PHHueSDK phHueSDK;

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
        Log.d("onActivityResult", "MainActivity.onActivityResult");
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
        new ScreenCaptureJobScheduler().scheduleJob(this);
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

    private void captureImage() {
        if (mediaProjection != null) {
            Log.d("captureImage", "MainActivity.captureImage mediaProjection != null");
            DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
            imageReader = ImageReader.newInstance(metrics.widthPixels, metrics.heightPixels, PixelFormat.RGBA_8888, 1);
            imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    Image image = reader.acquireNextImage();
                    Log.d("onImageAvailable", "MainActivity.OnImageAvailableListener.onImageAvailable " + image);
                    Bitmap bitmap = Bitmap.createScaledBitmap(convert(image), 640, 360, false);
                    createPaletteAsync(bitmap);
                    image.close();
                    reader.close();
                }
            }, null);
            createVirtualDisplay();
        }
    }

    public void createPaletteAsync(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                PHBridge bridge = phHueSDK.getSelectedBridge();
                List<PHLight> allLights = bridge.getResourceCache().getAllLights();
                for (PHLight light : allLights) {
                    Hue hue = null;
                    List<Palette.Swatch> swatchList = palette.getSwatches();
                    Collections.sort(swatchList, new Comparator<Palette.Swatch>() {
                        @Override
                        public int compare(Palette.Swatch o1, Palette.Swatch o2) {
                            return Integer.compare(o1.getPopulation(), o2.getPopulation());
                        }
                    });
                    Iterator<Palette.Swatch> iterator = swatchList.iterator();
                    if (iterator.hasNext()) {
                        Palette.Swatch swatch = iterator.next();
                        hue = new Hue(swatch.getRgb());
                    }
                    if (hue != null) {
                        PHLightState lightState = new PHLightState();
                        lightState.setHue(hue.getHue());
                        lightState.setSaturation(hue.getSaturation());
                        lightState.setBrightness(hue.getBrightness());
                        bridge.updateLightState(light, lightState, listener);
                    }
                }
            }
        });
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
        Log.d("convert", "MainActivity.convert " + bitmap);
        image.close();
        return bitmap;
    }

    private void createVirtualDisplay() {
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        Log.d("createVirtualDisplay", "MainActivity.createVirtualDisplay " + metrics);
        mediaProjection.createVirtualDisplay("MainActivity",
                metrics.widthPixels, metrics.heightPixels, metrics.densityDpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.getSurface(), null, null);
    }

    PHLightListener listener = new PHLightListener() {

        @Override
        public void onSuccess() {
        }

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
