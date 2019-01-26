package com.asconius.huetv;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

public class App extends Application {

    private HueSDKComponent hueSDKComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        hueSDKComponent = createComponent();
    }

    protected HueSDKComponent createComponent() {
        return DaggerHueSDKComponent.create();
    }

    public HueSDKComponent getComponent() {
        return hueSDKComponent;
    }

    @VisibleForTesting
    public void setComponent(HueSDKComponent component) {
        hueSDKComponent = component;
    }
}
