package com.asconius.huetv;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.asconius.huetv.stub.HueSDKStub;
import com.asconius.huetv.stub.PHBridgeStub;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHLightState;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.asconius.huetv.HueSharedPreferences.HUE_SHARED_PREFERENCES_STORE;
import static com.asconius.huetv.HueSharedPreferences.LAST_CONNECTED_IP;
import static com.asconius.huetv.HueSharedPreferences.LAST_CONNECTED_USERNAME;
import static com.google.common.truth.Truth.assertThat;

public class HueSharedPreferencesAndroidUnitTest extends AndroidUnitTest {

    @Rule
    public ActivityTestRule<BridgeActivity> activityRule = new ActivityTestRule<BridgeActivity>(BridgeActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            SharedPreferences prefs = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences(HUE_SHARED_PREFERENCES_STORE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.putString(LAST_CONNECTED_USERNAME, "User 1");
            editor.putString(LAST_CONNECTED_IP, "192.168.0.1");
            editor.commit();

            super.beforeActivityLaunched();

            inject();
        }
    };

    @Test
    public void hueSharedPreferences() throws UiObjectNotFoundException {
        long waitingTime = 10000;
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        // PushlinkActivity
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));

        for (PHSDKListener listener : ((HueSDKStub)hueSDK).getPhsdkListenerList()) {
            listener.onBridgeConnected(new PHBridgeStub(), "User 1");
        }

        // MainActivity
        onView(withId(R.id.authorizeButton)).check(matches(isDisplayed()));
        onView(withId(R.id.startButton)).check(matches(isDisplayed()));
        onView(withId(R.id.stopButton)).check(matches(isDisplayed()));
        onView(withId(R.id.authorizeButton)).perform(click());
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject button = uiDevice.findObject(new UiSelector().text("START NOW"));
        if (button.exists() && button.isEnabled()) {
            button.click();
        }
        onView(withId(R.id.startButton)).perform(click());

        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResource);

        IdlingRegistry.getInstance().unregister(idlingResource);
        onView(withId(R.id.stopButton)).perform(click());

        for (PHLightState lightState : ((PHBridgeStub)hueSDK.getSelectedBridge()).getLightStateList()) {
            assertThat(lightState.getHue()).isEqualTo(0);
            assertThat(lightState.getSaturation()).isEqualTo(0);
            assertThat(lightState.getBrightness()).isAnyOf(31,39, 47, 55, 63, 79, 87);
        }
    }
}
