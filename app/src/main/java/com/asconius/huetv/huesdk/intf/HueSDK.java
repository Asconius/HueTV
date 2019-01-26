package com.asconius.huetv.huesdk.intf;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHNotificationManager;
import com.philips.lighting.model.PHBridge;

import java.util.List;
import java.util.Map;

public interface HueSDK {

    byte SEARCH_BRIDGE = 1;
    int HB_INTERVAL = 10000;

    void setAppName(String appName);

    void setDeviceName(String deviceName);

    PHNotificationManager getNotificationManager();

    void connect(PHAccessPoint accessPoint);

    boolean disconnect(PHBridge bridge);

    List<PHAccessPoint> getDisconnectedAccessPoint();

    PHBridge getSelectedBridge();

    boolean isAccessPointConnected(PHAccessPoint accessPoint);

    void disableAllHeartbeat();

    void disableHeartbeat(PHBridge bridge);

    void enableHeartbeat(PHBridge bridge, long time);

    Map<String, Long> getLastHeartbeat();

    Object getSDKService(byte msgType);

    List<PHAccessPoint> getAccessPointsFound();

    void setSelectedBridge(PHBridge selectedBridge);

    void startPushlinkAuthentication(PHAccessPoint accessPoint);
}
