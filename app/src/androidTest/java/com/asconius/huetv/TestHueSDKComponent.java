package com.asconius.huetv;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { HueSDKModule.class })
public interface TestHueSDKComponent extends HueSDKComponent {
    void inject(AndroidUnitTest androidUnitTest);
}
