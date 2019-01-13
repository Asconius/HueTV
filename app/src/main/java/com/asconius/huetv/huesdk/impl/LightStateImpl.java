package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.LightState;
import com.philips.lighting.model.PHLightState;

public class LightStateImpl implements LightState {

    private PHLightState phLightState = new PHLightState();

    public PHLightState getPhLightState() {
        return phLightState;
    }

    @Override
    public void setHue(Integer hue) {
        phLightState.setHue(hue);
    }

    @Override
    public void setSaturation(Integer saturation) {
        phLightState.setSaturation(saturation);
    }

    @Override
    public void setBrightness(Integer brightness) {
        phLightState.setBrightness(brightness);
    }
}
