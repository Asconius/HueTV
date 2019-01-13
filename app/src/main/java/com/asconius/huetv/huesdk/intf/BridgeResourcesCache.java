package com.asconius.huetv.huesdk.intf;

import java.util.List;

public interface BridgeResourcesCache {

    BridgeConfiguration getBridgeConfiguration();

    List<Light> getAllLights();
}
