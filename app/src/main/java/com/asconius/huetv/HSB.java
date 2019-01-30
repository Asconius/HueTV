package com.asconius.huetv;

import com.philips.lighting.hue.sdk.utilities.impl.Color;

public class HSB {

    private static final float HUE_MAX = 65535f;
    private static final float SATURATION_MAX = 254f;
    private static final float BRIGHTNESS_MAX = 254f;
    private final float hue;
    private final float saturation;
    private final float brightness;

    public HSB(int color) {
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
