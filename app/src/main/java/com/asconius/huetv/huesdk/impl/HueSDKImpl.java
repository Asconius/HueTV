package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.AccessPoint;
import com.asconius.huetv.huesdk.intf.Bridge;
import com.asconius.huetv.huesdk.intf.HueSDK;
import com.asconius.huetv.huesdk.intf.NotificationManager;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HueSDKImpl implements HueSDK {

    private PHHueSDK phHueSDK = PHHueSDK.create();
    private NotificationManager notificationManager = new NotificationManagerImpl(phHueSDK.getNotificationManager());

    @Override
    public void setAppName(String appName) {
        phHueSDK.setAppName(appName);
    }

    @Override
    public void setDeviceName(String deviceName) {
        phHueSDK.setDeviceName(deviceName);
    }

    @Override
    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    @Override
    public void connect(AccessPoint accessPoint) {
        phHueSDK.connect(((AccessPointImpl) accessPoint).getPhAccessPoint());
    }

    @Override
    public boolean disconnect(Bridge bridge) {
        return phHueSDK.disconnect(((BridgeImpl) bridge).getPhBridge());
    }

    @Override
    public List<AccessPoint> getDisconnectedAccessPoint() {
        List<AccessPoint> accessPointList = new ArrayList<>();
        for (PHAccessPoint phAccessPoint : phHueSDK.getDisconnectedAccessPoint()) {
            accessPointList.add(new AccessPointImpl(phAccessPoint));
        }
        return accessPointList;
    }

    @Override
    public Bridge getSelectedBridge() {
        return new BridgeImpl(phHueSDK.getSelectedBridge());
    }

    @Override
    public boolean isAccessPointConnected(AccessPoint accessPoint) {
        return phHueSDK.isAccessPointConnected(((AccessPointImpl) accessPoint).getPhAccessPoint());
    }

    @Override
    public void disableAllHeartbeat() {
        phHueSDK.disableAllHeartbeat();
    }

    @Override
    public void disableHeartbeat(Bridge bridge) {
        phHueSDK.disableHeartbeat(((BridgeImpl) bridge).getPhBridge());
    }

    @Override
    public void enableHeartbeat(Bridge bridge, long time) {
        phHueSDK.enableHeartbeat(((BridgeImpl) bridge).getPhBridge(), time);
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
    public List<AccessPoint> getAccessPointsFound() {
        List<AccessPoint> accessPointList = new ArrayList<>();
        for (PHAccessPoint phAccessPoint : phHueSDK.getAccessPointsFound()) {
            accessPointList.add(new AccessPointImpl(phAccessPoint));
        }
        return accessPointList;
    }

    @Override
    public void setSelectedBridge(Bridge selectedBridge) {
        phHueSDK.setSelectedBridge(((BridgeImpl) selectedBridge).getPhBridge());
    }

    @Override
    public void startPushlinkAuthentication(AccessPoint accessPoint) {
        phHueSDK.startPushlinkAuthentication(((AccessPointImpl) accessPoint).getPhAccessPoint());
    }
}
