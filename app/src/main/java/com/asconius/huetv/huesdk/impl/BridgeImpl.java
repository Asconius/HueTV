package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.Bridge;
import com.asconius.huetv.huesdk.intf.BridgeResource;
import com.asconius.huetv.huesdk.intf.BridgeResourcesCache;
import com.asconius.huetv.huesdk.intf.HueError;
import com.asconius.huetv.huesdk.intf.Light;
import com.asconius.huetv.huesdk.intf.LightListener;
import com.asconius.huetv.huesdk.intf.LightState;
import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BridgeImpl implements Bridge {

    private final PHBridge phBridge;

    public BridgeImpl(PHBridge phBridge) {
        this.phBridge = phBridge;
    }

    public PHBridge getPhBridge() {
        return phBridge;
    }

    @Override
    public BridgeResourcesCache getResourceCache() {
        return new BridgeResourcesCacheImpl(phBridge.getResourceCache());
    }

    @Override
    public void updateLightState(Light var1, LightState var2, final LightListener var3) {
        PHLight light = ((LightImpl) var1).getPhLight();
        PHLightState lightState = ((LightStateImpl) var2).getPhLightState();

        PHLightListener phLightListener = new PHLightListener() {
            @Override
            public void onReceivingLightDetails(PHLight phLight) {
                var3.onReceivingLightDetails(new LightImpl(phLight));
            }

            @Override
            public void onReceivingLights(List<PHBridgeResource> list) {
                List<BridgeResource> bridgeResourceList = new ArrayList<>();
                for (PHBridgeResource phBridgeResource : list) {
                    bridgeResourceList.add(new BridgeResourceImpl(phBridgeResource));
                }
                var3.onReceivingLights(bridgeResourceList);
            }

            @Override
            public void onSearchComplete() {
                var3.onSearchComplete();
            }

            @Override
            public void onSuccess() {
                var3.onSuccess();
            }

            @Override
            public void onError(int i, String s) {
                var3.onError(i, s);
            }

            @Override
            public void onStateUpdate(Map<String, String> map, List<PHHueError> list) {
                List<HueError> hueErrorList = new ArrayList<>();
                for (PHHueError phHueError : list) {
                    hueErrorList.add(new HueErrorImpl(phHueError.getCode(), phHueError.getMessage(), phHueError.getMessage()));
                }
                var3.onStateUpdate(map, hueErrorList);
            }
        };

        phBridge.updateLightState(light, lightState, phLightListener);
    }
}
