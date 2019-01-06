package com.asconius.philipshueandroidtv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.util.Base64;

import com.philips.lighting.hue.sdk.utilities.impl.Color;
import com.philips.lighting.model.PHLightState;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainActivityAndroidUnitTest {

    private static final String IMAGE = "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAIAAAACUFjqAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAJOgAACToAYJjBRwAAAAjSURBVChTY3gro4KM/n9iQEYDKs2w6AUyUtrog4wGTnqjDwC/x5Bw50Z6RAAAAABJRU5ErkJggg";

    @Rule
    public final ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createPalette() {
        byte[] data = Base64.decode(IMAGE, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Palette palette = Palette.from(bitmap).generate();
        List<Palette.Swatch> swatchList = palette.getSwatches();
        List<Integer> redList = Arrays.asList(0, 32, 232, 248);
        List<Integer> greenList = Arrays.asList(160, 176, 24, 240);
        List<Integer> blueList = Arrays.asList(232, 72, 32, 0);
        List<Iterator<Integer>> iteratorList = Arrays.asList(redList.iterator(), greenList.iterator(), blueList.iterator());
        for (Palette.Swatch swatch : swatchList) {
            assertThat(Color.red(swatch.getRgb())).isEqualTo(iteratorList.get(0).next());
            assertThat(Color.green(swatch.getRgb())).isEqualTo(iteratorList.get(1).next());
            assertThat(Color.blue(swatch.getRgb())).isEqualTo(iteratorList.get(2).next());
        }
    }

    @Test
    public void updateLightState() {
        PHBridgeStub phBridge = new PHBridgeStub();
        MainActivity mainActivity = mainActivityActivityTestRule.getActivity();
        mainActivity.getPhHueSDK().setSelectedBridge(phBridge);

        byte[] data = Base64.decode(IMAGE, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Palette palette = Palette.from(bitmap).generate();

        mainActivity.updateLightState(palette);

        List<Float> hueList = Arrays.asList(198.61935f, 136.6665f, 357.68735f, 58.06363f);
        List<Float> saturationList = Arrays.asList(100.0f, 81.49606f, 89.37008f, 100.0f);
        List<Float> brightnessList = Arrays.asList(90.94488f, 68.897644f, 90.94488f, 97.244095f);
        List<Iterator<Float>> iteratorList = Arrays.asList(hueList.iterator(), saturationList.iterator(), brightnessList.iterator());

        for (PHLightState lightState : phBridge.getLightStateList()) {
            assertThat(lightState.getHue() / 65535f * 360f).isEqualTo(iteratorList.get(0).next());
            assertThat(lightState.getSaturation() / 254f * 100f).isEqualTo(iteratorList.get(1).next());
            assertThat(lightState.getBrightness() / 254f * 100f).isEqualTo(iteratorList.get(2).next());
        }
    }
}
