package com.asconius.huetv;

import android.content.Context;
import android.content.SharedPreferences;

import com.asconius.huetv.huesdk.intf.HueSDK;

import javax.inject.Inject;

import androidx.test.platform.app.InstrumentationRegistry;

public class AndroidUnitTest {

    @Inject
    HueSDK hueSDK;

    protected void inject() {
        TestHueSDKComponent testHueSDKComponent = DaggerTestHueSDKComponent.builder().hueSDKModule(new TestHueSDKModule()).build();
        getAppFromInstrumentation().setComponent(testHueSDKComponent);
        testHueSDKComponent.inject(this);
    }

    private App getAppFromInstrumentation() {
        return (App) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }

    protected void clearSharedPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(HueSharedPreferences.HUE_SHARED_PREFERENCES_STORE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
