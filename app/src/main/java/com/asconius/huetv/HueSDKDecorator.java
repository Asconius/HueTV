package com.asconius.huetv;

import com.asconius.huetv.huesdk.intf.HueSDK;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHNotificationManager;
import com.philips.lighting.model.PHBridge;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class HueSDKDecorator implements HueSDK {

    private final HueSDK hueSDK;

    @Inject
    public HueSDKDecorator(HueSDK hueSDK) {
        this.hueSDK = hueSDK;
    }

    @Override
    public void setAppName(String appName) {
        hueSDK.setAppName(appName);
    }

    @Override
    public void setDeviceName(String deviceName) {
        hueSDK.setDeviceName(deviceName);
    }

    @Override
    public PHNotificationManager getNotificationManager() {
        return hueSDK.getNotificationManager();
    }

    @Override
    public void connect(PHAccessPoint accessPoint) {
        hueSDK.connect(accessPoint);
    }

    @Override
    public boolean disconnect(PHBridge bridge) {
        return hueSDK.disconnect(bridge);
    }

    @Override
    public List<PHAccessPoint> getDisconnectedAccessPoint() {
        return hueSDK.getDisconnectedAccessPoint();
    }

    @Override
    public PHBridge getSelectedBridge() {
        return hueSDK.getSelectedBridge();
    }

    @Override
    public boolean isAccessPointConnected(PHAccessPoint accessPoint) {
        return hueSDK.isAccessPointConnected(accessPoint);
    }

    @Override
    public void disableAllHeartbeat() {
        hueSDK.disableAllHeartbeat();
    }

    @Override
    public void disableHeartbeat(PHBridge bridge) {
        hueSDK.disableHeartbeat(bridge);
    }

    @Override
    public void enableHeartbeat(PHBridge bridge, long time) {
        hueSDK.enableHeartbeat(bridge, time);
    }

    @Override
    public Map<String, Long> getLastHeartbeat() {
        return hueSDK.getLastHeartbeat();
    }

    @Override
    public Object getSDKService(byte msgType) {
        return hueSDK.getSDKService(msgType);
    }

    @Override
    public List<PHAccessPoint> getAccessPointsFound() {
        return hueSDK.getAccessPointsFound();
    }

    @Override
    public void setSelectedBridge(PHBridge selectedBridge) {
        hueSDK.setSelectedBridge(selectedBridge);
    }

    @Override
    public void startPushlinkAuthentication(PHAccessPoint accessPoint) {
        hueSDK.startPushlinkAuthentication(accessPoint);
    }
}
