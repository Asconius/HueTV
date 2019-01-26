package com.asconius.huetv.stub;

import com.asconius.huetv.huesdk.intf.AccessPoint;
import com.asconius.huetv.huesdk.intf.HueSDK;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHNotificationManager;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HueSDKStub implements HueSDK {

    private PHSDKListener phsdkListener;
    private List<PHAccessPoint> phAccessPointList = new ArrayList<>();
    private PHBridge phBridge = new PHBridgeStub();

    public PHSDKListener getPhsdkListener() {
        return phsdkListener;
    }

    @Override
    public void setAppName(String appName) {

    }

    @Override
    public void setDeviceName(String deviceName) {

    }

    @Override
    public PHNotificationManager getNotificationManager() {
        return new PHNotificationManager() {
            @Override
            public void registerSDKListener(PHSDKListener phsdkListener) {
                HueSDKStub.this.phsdkListener = phsdkListener;
            }

            @Override
            public void unregisterSDKListener(PHSDKListener phsdkListener) {

            }

            @Override
            public void cancelSearchNotification() {

            }
        };
    }

    @Override
    public void connect(PHAccessPoint accessPoint) {
        phsdkListener.onBridgeConnected(new PHBridgeStub(), "User 1");
    }

    @Override
    public boolean disconnect(PHBridge bridge) {
        return false;
    }

    @Override
    public List<PHAccessPoint> getDisconnectedAccessPoint() {
        return null;
    }

    @Override
    public PHBridge getSelectedBridge() {
        return phBridge;
    }

    @Override
    public boolean isAccessPointConnected(PHAccessPoint accessPoint) {
        return false;
    }

    @Override
    public void disableAllHeartbeat() {

    }

    @Override
    public void disableHeartbeat(PHBridge bridge) {

    }

    @Override
    public void enableHeartbeat(PHBridge bridge, long time) {

    }

    @Override
    public Map<String, Long> getLastHeartbeat() {
        return new HashMap<>();
    }

    @Override
    public Object getSDKService(byte msgType) {
        if(msgType == PHHueSDK.SEARCH_BRIDGE) {
            return new PHBridgeSearchManager() {

                @Override
                public void search(boolean b, boolean b1) {
                    PHAccessPoint phAccessPoint1 = new PHAccessPoint("192.168.0.1", "User 1", "mac 1");
                    PHAccessPoint phAccessPoint2 = new PHAccessPoint("192.168.0.2", "User 2", "mac 2");
                    PHAccessPoint phAccessPoint3 = new PHAccessPoint("192.168.0.3", "User 3", "mac 3");
                    PHAccessPoint phAccessPoint4 = new PHAccessPoint("192.168.0.4", "User 4", "mac 4");

                    phsdkListener.onAccessPointsFound(Arrays.asList(phAccessPoint1, phAccessPoint2, phAccessPoint3, phAccessPoint4));
                }

                @Override
                public void search(boolean b, boolean b1, boolean b2) {

                }

                @Override
                public void upnpSearch() {

                }

                @Override
                public void portalSearch() {

                }

                @Override
                public void ipAddressSearch() {

                }

                @Override
                public void setPortalAddress(String s) {

                }

                @Override
                public String getPortalAddress() {
                    return null;
                }
            };
        }
        return null;
    }

    @Override
    public List<PHAccessPoint> getAccessPointsFound() {
        return phAccessPointList;
    }

    @Override
    public void setSelectedBridge(PHBridge selectedBridge) {

    }

    @Override
    public void startPushlinkAuthentication(PHAccessPoint accessPoint) {

    }
}
