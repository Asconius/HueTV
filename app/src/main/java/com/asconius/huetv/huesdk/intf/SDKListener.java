package com.asconius.huetv.huesdk.intf;

import java.util.List;

public interface SDKListener {

    void onCacheUpdated(List<Integer> var1, Bridge var2);

    void onBridgeConnected(Bridge var1, String var2);

    void onAuthenticationRequired(AccessPoint var1);

    void onAccessPointsFound(List<AccessPoint> var1);

    void onError(int var1, String var2);

    void onConnectionResumed(Bridge var1);

    void onConnectionLost(AccessPoint var1);

    void onParsingErrors(List<HueParsingError> var1);
}
