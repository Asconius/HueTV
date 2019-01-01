package com.asconius.philipshueandroidtv;

import android.graphics.Color;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class HueTest {

    @Test
    public void red() {
        Hue hue = new Hue(Color.RED);
        assertThat(hue.getHue()).isEqualTo(0);
        assertThat(hue.getSaturation()).isEqualTo(254);
        assertThat(hue.getBrightness()).isEqualTo(254);
    }

    @Test
    public void blue() {
        Hue hue = new Hue(Color.BLUE);
        assertThat(hue.getHue()).isEqualTo(43690);
        assertThat(hue.getSaturation()).isEqualTo(254);
        assertThat(hue.getBrightness()).isEqualTo(254);
    }

    @Test
    public void green() {
        Hue hue = new Hue(Color.GREEN);
        assertThat(hue.getHue()).isEqualTo(21845);
        assertThat(hue.getSaturation()).isEqualTo(254);
        assertThat(hue.getBrightness()).isEqualTo(254);
    }

    @Test
    public void yellow() {
        Hue hue = new Hue(Color.YELLOW);
        assertThat(hue.getHue()).isEqualTo(10922);
        assertThat(hue.getSaturation()).isEqualTo(254);
        assertThat(hue.getBrightness()).isEqualTo(254);
    }

    @Test
    public void white() {
        Hue hue = new Hue(Color.WHITE);
        assertThat(hue.getHue()).isEqualTo(0);
        assertThat(hue.getSaturation()).isEqualTo(0);
        assertThat(hue.getBrightness()).isEqualTo(254);
    }

    @Test
    public void black() {
        Hue hue = new Hue(Color.BLACK);
        assertThat(hue.getHue()).isEqualTo(0);
        assertThat(hue.getSaturation()).isEqualTo(0);
        assertThat(hue.getBrightness()).isEqualTo(0);
    }
}
