package com.asconius.huetv;

import com.asconius.huetv.huesdk.intf.HueSDK;
import com.asconius.huetv.stub.HueSDKStub;

public class TestHueSDKModule extends HueSDKModule {

    @Override
    public HueSDK provideHueSDK() {
        return new HueSDKStub();
    }
}
