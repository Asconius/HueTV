package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.BridgeResource;
import com.philips.lighting.model.PHBridgeResource;

public class BridgeResourceImpl implements BridgeResource {

    private final PHBridgeResource phBridgeResource;

    public BridgeResourceImpl(PHBridgeResource phBridgeResource) {
        this.phBridgeResource = phBridgeResource;
    }
}
