package com.asconius.huetv.huesdk.intf;

import java.util.List;
import java.util.Map;

public interface HueSDK {

    byte SEARCH_BRIDGE = 1;
    int HB_INTERVAL = 10000;

    void setAppName(String appName);

    void setDeviceName(String deviceName);

    NotificationManager getNotificationManager();

    void connect(AccessPoint accessPoint);

    boolean disconnect(Bridge bridge);

    List<AccessPoint> getDisconnectedAccessPoint();

    Bridge getSelectedBridge();

    boolean isAccessPointConnected(AccessPoint accessPoint);

    void disableAllHeartbeat();

    void disableHeartbeat(Bridge bridge);

    void enableHeartbeat(Bridge bridge, long time);

    Map<String, Long> getLastHeartbeat();

    Object getSDKService(byte msgType);

    List<AccessPoint> getAccessPointsFound();

    void setSelectedBridge(Bridge selectedBridge);

    void startPushlinkAuthentication(AccessPoint accessPoint);
}
