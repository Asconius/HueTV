package com.asconius.huetv;

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

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.google.common.truth.Truth.assertThat;
import static org.hamcrest.Matchers.anything;

public class BridgeActivityAndroidUnitTest extends AndroidUnitTest {

    @Rule
    public ActivityTestRule<BridgeActivity> activityRule = new ActivityTestRule<BridgeActivity>(BridgeActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            clearSharedPrefs(InstrumentationRegistry.getInstrumentation().getTargetContext());
            super.beforeActivityLaunched();

            inject();
        }
    };

    @Test
    public void bridgeActivityAuthenticationRequired() throws UiObjectNotFoundException {
        long waitingTime = 10000;
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        onView(withId(R.id.bridge_list)).check(matches(isDisplayed()));
        onView(withId(R.id.textView1)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.bridge_list)).atPosition(0).perform(click());

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

    @Test
    public void bridgeActivityBridgeConnected() throws UiObjectNotFoundException {
        long waitingTime = 10000;
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        onView(withId(R.id.bridge_list)).check(matches(isDisplayed()));
        onView(withId(R.id.textView1)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.bridge_list)).atPosition(1).perform(click());

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
