package com.asconius.huetv;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { HueSDKModule.class })
public interface HueSDKComponent {

    HueSDKDecorator hueSDKDecorator();

    void inject(HueActivity hueActivity);
}
