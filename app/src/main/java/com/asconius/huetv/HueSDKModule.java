package com.asconius.huetv;

import com.asconius.huetv.huesdk.impl.HueSDKImpl;
import com.asconius.huetv.huesdk.intf.HueSDK;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class HueSDKModule {

    @Provides
    @Singleton
    public HueSDK provideHueSDK() {
        return new HueSDKImpl();
    }
}
