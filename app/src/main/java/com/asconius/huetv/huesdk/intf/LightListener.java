package com.asconius.huetv.huesdk.intf;

import java.util.List;
import java.util.Map;

public interface LightListener {

    void onSuccess();

    void onError(int var1, String var2);

    void onStateUpdate(Map<String, String> var1, List<HueError> var2);

    void onReceivingLightDetails(Light var1);

    void onReceivingLights(List<BridgeResource> var1);

    void onSearchComplete();
}
