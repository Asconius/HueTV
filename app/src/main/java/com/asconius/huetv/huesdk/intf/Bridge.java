package com.asconius.huetv.huesdk.intf;

public interface Bridge {

    BridgeResourcesCache getResourceCache();

    void updateLightState(Light var1, LightState var2, LightListener var3);
}
