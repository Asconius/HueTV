package com.asconius.huetv;

import android.graphics.Color;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class HueTest {

    private static final int HUE_MAX = 65535;
    private static final int HUE_MIN = 0;
    private static final int SATURATION_MAX = 254;
    private static final int SATURATION_MIN = 0;
    private static final int BRIGHTNESS_MAX = 254;
    private static final int BRIGHTNESS_MIN = 0;

    @Test
    public void red() {
        Hue hue = new Hue(Color.RED);
        assertThat(hue.getHue()).isEqualTo(HUE_MIN);
        assertThat(hue.getSaturation()).isEqualTo(SATURATION_MAX);
        assertThat(hue.getBrightness()).isEqualTo(BRIGHTNESS_MAX);
    }

    @Test
    public void blue() {
        Hue hue = new Hue(Color.BLUE);
        assertThat(hue.getHue()).isEqualTo(HUE_MAX / 3 * 2);
        assertThat(hue.getSaturation()).isEqualTo(SATURATION_MAX);
        assertThat(hue.getBrightness()).isEqualTo(BRIGHTNESS_MAX);
    }

    @Test
    public void green() {
        Hue hue = new Hue(Color.GREEN);
        assertThat(hue.getHue()).isEqualTo(HUE_MAX / 3);
        assertThat(hue.getSaturation()).isEqualTo(SATURATION_MAX);
        assertThat(hue.getBrightness()).isEqualTo(BRIGHTNESS_MAX);
    }

    @Test
    public void yellow() {
        Hue hue = new Hue(Color.YELLOW);
        assertThat(hue.getHue()).isEqualTo(HUE_MAX / 6);
        assertThat(hue.getSaturation()).isEqualTo(SATURATION_MAX);
        assertThat(hue.getBrightness()).isEqualTo(BRIGHTNESS_MAX);
    }

    @Test
    public void white() {
        Hue hue = new Hue(Color.WHITE);
        assertThat(hue.getHue()).isEqualTo(HUE_MIN);
        assertThat(hue.getSaturation()).isEqualTo(SATURATION_MIN);
        assertThat(hue.getBrightness()).isEqualTo(BRIGHTNESS_MAX);
    }

    @Test
    public void black() {
        Hue hue = new Hue(Color.BLACK);
        assertThat(hue.getHue()).isEqualTo(HUE_MIN);
        assertThat(hue.getSaturation()).isEqualTo(SATURATION_MIN);
        assertThat(hue.getBrightness()).isEqualTo(BRIGHTNESS_MIN);
    }
}
