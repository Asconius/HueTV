package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.BridgeConfiguration;
import com.asconius.huetv.huesdk.intf.BridgeResourcesCache;
import com.asconius.huetv.huesdk.intf.Light;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;

import java.util.ArrayList;
import java.util.List;

public class BridgeResourcesCacheImpl implements BridgeResourcesCache {

    private final PHBridgeResourcesCache phBridgeResourcesCache;

    public BridgeResourcesCacheImpl(PHBridgeResourcesCache phBridgeResourcesCache) {
        this.phBridgeResourcesCache = phBridgeResourcesCache;
    }

    public PHBridgeResourcesCache getPhBridgeResourcesCache() {
        return phBridgeResourcesCache;
    }

    @Override
    public BridgeConfiguration getBridgeConfiguration() {
        return new BridgeConfigurationImpl(phBridgeResourcesCache.getBridgeConfiguration());
    }

    @Override
    public List<Light> getAllLights() {
        List<Light> lightList = new ArrayList<>();
        for (PHLight phLight : phBridgeResourcesCache.getAllLights()) {
            lightList.add(new LightImpl(phLight));
        }
        return lightList;
    }
}
