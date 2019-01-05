package com.asconius.philipshueandroidtv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.util.Base64;

import com.philips.lighting.hue.sdk.utilities.impl.Color;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainActivityAndroidUnitTest {

    private static final String IMAGE = "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAIAAAACUFjqAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAJOgAACToAYJjBRwAAAAjSURBVChTY3gro4KM/n9iQEYDKs2w6AUyUtrog4wGTnqjDwC/x5Bw50Z6RAAAAABJRU5ErkJggg";

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
}
