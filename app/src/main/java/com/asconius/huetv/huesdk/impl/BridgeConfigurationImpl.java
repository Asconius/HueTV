package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.BridgeConfiguration;
import com.philips.lighting.model.PHBridgeConfiguration;

public class BridgeConfigurationImpl implements BridgeConfiguration {

    private final PHBridgeConfiguration phBridgeConfiguration;

    public BridgeConfigurationImpl(PHBridgeConfiguration phBridgeConfiguration) {
        this.phBridgeConfiguration = phBridgeConfiguration;
    }

    @Override
    public String getIpAddress() {
        return phBridgeConfiguration.getIpAddress();
    }
}
