package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.BridgeResource;
import com.philips.lighting.model.PHBridgeResource;

public class BridgeResourceImpl implements BridgeResource {

    private PHBridgeResource phBridgeResource;

    public BridgeResourceImpl(PHBridgeResource phBridgeResource) {
        this.phBridgeResource = phBridgeResource;
    }
}
