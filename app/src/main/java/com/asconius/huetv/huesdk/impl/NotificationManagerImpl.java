package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.AccessPoint;
import com.asconius.huetv.huesdk.intf.HueParsingError;
import com.asconius.huetv.huesdk.intf.NotificationManager;
import com.asconius.huetv.huesdk.intf.SDKListener;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHNotificationManager;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationManagerImpl implements NotificationManager {

    private final PHNotificationManager phNotificationManager;
    private final Map<SDKListener, PHSDKListener> sdkListenerPHSDKListenerMap = new HashMap<>();

    public NotificationManagerImpl(PHNotificationManager phNotificationManager) {
        this.phNotificationManager = phNotificationManager;
    }

    @Override
    public void registerSDKListener(final SDKListener var1) {
        PHSDKListener phsdkListener = new PHSDKListener() {
            @Override
            public void onCacheUpdated(List<Integer> list, PHBridge phBridge) {
                var1.onCacheUpdated(list, new BridgeImpl(phBridge));
            }

            @Override
            public void onBridgeConnected(PHBridge phBridge, String s) {
                var1.onBridgeConnected(new BridgeImpl(phBridge), s);
            }

            @Override
            public void onAuthenticationRequired(PHAccessPoint phAccessPoint) {
                var1.onAuthenticationRequired(new AccessPointImpl(phAccessPoint));
            }

            @Override
            public void onAccessPointsFound(List<PHAccessPoint> list) {
                List<AccessPoint> accessPointList = new ArrayList<>();
                for (PHAccessPoint phAccessPoint : list) {
                    accessPointList.add(new AccessPointImpl(phAccessPoint));
                }
                var1.onAccessPointsFound(accessPointList);
            }

            @Override
            public void onError(int i, String s) {
                var1.onError(i, s);
            }

            @Override
            public void onConnectionResumed(PHBridge phBridge) {
                var1.onConnectionResumed(new BridgeImpl(phBridge));
            }

            @Override
            public void onConnectionLost(PHAccessPoint phAccessPoint) {
                var1.onConnectionLost(new AccessPointImpl(phAccessPoint));
            }

            @Override
            public void onParsingErrors(List<PHHueParsingError> list) {
                List<HueParsingError> hueParsingErrorList = new ArrayList<>();
                for (PHHueParsingError error: list) {
                    hueParsingErrorList.add(new HueParsingErrorImpl(error));
                }
                var1.onParsingErrors(hueParsingErrorList);
            }
        };
        sdkListenerPHSDKListenerMap.put(var1, phsdkListener);
        phNotificationManager.registerSDKListener(phsdkListener);
    }

    @Override
    public void unregisterSDKListener(SDKListener var1) {
        phNotificationManager.unregisterSDKListener(sdkListenerPHSDKListenerMap.get(var1));
    }
}
