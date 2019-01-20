package com.asconius.huetv;

import android.graphics.Color;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class HSBTest {

    private static final int HUE_MAX = 65535;
    private static final int HUE_MIN = 0;
    private static final int SATURATION_MAX = 254;
    private static final int SATURATION_MIN = 0;
    private static final int BRIGHTNESS_MAX = 254;
    private static final int BRIGHTNESS_MIN = 0;

    @Test
    public void red() {
        HSB hsb = new HSB(Color.RED);
        assertThat(hsb.getHue()).isEqualTo(HUE_MIN);
        assertThat(hsb.getSaturation()).isEqualTo(SATURATION_MAX);
        assertThat(hsb.getBrightness()).isEqualTo(BRIGHTNESS_MAX);
    }

    @Test
    public void blue() {
        HSB hsb = new HSB(Color.BLUE);
        assertThat(hsb.getHue()).isEqualTo(HUE_MAX / 3 * 2);
        assertThat(hsb.getSaturation()).isEqualTo(SATURATION_MAX);
        assertThat(hsb.getBrightness()).isEqualTo(BRIGHTNESS_MAX);
    }

    @Test
    public void green() {
        HSB hsb = new HSB(Color.GREEN);
        assertThat(hsb.getHue()).isEqualTo(HUE_MAX / 3);
        assertThat(hsb.getSaturation()).isEqualTo(SATURATION_MAX);
        assertThat(hsb.getBrightness()).isEqualTo(BRIGHTNESS_MAX);
    }

    @Test
    public void yellow() {
        HSB hsb = new HSB(Color.YELLOW);
        assertThat(hsb.getHue()).isEqualTo(HUE_MAX / 6);
        assertThat(hsb.getSaturation()).isEqualTo(SATURATION_MAX);
        assertThat(hsb.getBrightness()).isEqualTo(BRIGHTNESS_MAX);
    }

    @Test
    public void white() {
        HSB hsb = new HSB(Color.WHITE);
        assertThat(hsb.getHue()).isEqualTo(HUE_MIN);
        assertThat(hsb.getSaturation()).isEqualTo(SATURATION_MIN);
        assertThat(hsb.getBrightness()).isEqualTo(BRIGHTNESS_MAX);
    }

    @Test
    public void black() {
        HSB hsb = new HSB(Color.BLACK);
        assertThat(hsb.getHue()).isEqualTo(HUE_MIN);
        assertThat(hsb.getSaturation()).isEqualTo(SATURATION_MIN);
        assertThat(hsb.getBrightness()).isEqualTo(BRIGHTNESS_MIN);
    }
}
