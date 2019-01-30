package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.HueSDK;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHNotificationManager;
import com.philips.lighting.model.PHBridge;

import java.util.List;
import java.util.Map;

public class HueSDKImpl implements HueSDK {

    private final PHHueSDK phHueSDK = PHHueSDK.create();

    @Override
    public void setAppName(String appName) {
        phHueSDK.setAppName(appName);
    }

    @Override
    public void setDeviceName(String deviceName) {
        phHueSDK.setDeviceName(deviceName);
    }

    @Override
    public PHNotificationManager getNotificationManager() {
        return phHueSDK.getNotificationManager();
    }

    @Override
    public void connect(PHAccessPoint accessPoint) {
        phHueSDK.connect(accessPoint);
    }

    @Override
    public boolean disconnect(PHBridge bridge) {
        return phHueSDK.disconnect(bridge);
    }

    @Override
    public List<PHAccessPoint> getDisconnectedAccessPoint() {
        return phHueSDK.getDisconnectedAccessPoint();
    }

    @Override
    public PHBridge getSelectedBridge() {
        return phHueSDK.getSelectedBridge();
    }

    @Override
    public boolean isAccessPointConnected(PHAccessPoint accessPoint) {
        return phHueSDK.isAccessPointConnected(accessPoint);
    }

    @Override
    public void disableAllHeartbeat() {
        phHueSDK.disableAllHeartbeat();
    }

    @Override
    public void disableHeartbeat(PHBridge bridge) {
        phHueSDK.disableHeartbeat(bridge);
    }

    @Override
    public void enableHeartbeat(PHBridge bridge, long time) {
        phHueSDK.enableHeartbeat(bridge, time);
    }

    @Override
    public Map<String, Long> getLastHeartbeat() {
        return phHueSDK.getLastHeartbeat();
    }

    @Override
    public Object getSDKService(byte msgType) {
        return phHueSDK.getSDKService(msgType);
    }

    @Override
    public List<PHAccessPoint> getAccessPointsFound() {
        return phHueSDK.getAccessPointsFound();
    }

    @Override
    public void setSelectedBridge(PHBridge selectedBridge) {
        phHueSDK.setSelectedBridge(selectedBridge);
    }

    @Override
    public void startPushlinkAuthentication(PHAccessPoint accessPoint) {
        phHueSDK.startPushlinkAuthentication(accessPoint);
    }
}
