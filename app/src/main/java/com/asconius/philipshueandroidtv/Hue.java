package com.asconius.philipshueandroidtv;

import com.philips.lighting.hue.sdk.utilities.impl.Color;

public class Hue {

    private static final float HUE_MAX = 65535f;
    private static final float SATURATION_MAX = 254f;
    private static final float BRIGHTNESS_MAX = 254f;
    private float hue;
    private float saturation;
    private float brightness;

    public Hue(int color) {
        hue = Color.hue(color);
        saturation = Color.saturation(color);
        brightness = Color.brightness(color);
    }

    public int getHue() {
        return (int)(hue * HUE_MAX);
    }

    public int getSaturation() {
        return (int)(saturation * SATURATION_MAX);
    }

    public int getBrightness() {
        return (int)(brightness * BRIGHTNESS_MAX);
    }
}
