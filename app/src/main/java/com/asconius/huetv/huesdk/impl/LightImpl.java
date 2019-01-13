package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.Light;
import com.philips.lighting.model.PHLight;

public class LightImpl implements Light {

    private PHLight phLight;

    public LightImpl(PHLight phLight) {
        this.phLight = phLight;
    }

    public PHLight getPhLight() {
        return phLight;
    }
}
