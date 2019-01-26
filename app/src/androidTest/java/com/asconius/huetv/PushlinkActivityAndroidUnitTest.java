package com.asconius.huetv;

import com.asconius.huetv.stub.HueSDKStub;
import com.asconius.huetv.stub.PHBridgeStub;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;

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
import static org.hamcrest.Matchers.anything;

public class PushlinkActivityAndroidUnitTest extends AndroidUnitTest {

    @Rule
    public ActivityTestRule<PushlinkActivity> activityRule = new ActivityTestRule<PushlinkActivity>(PushlinkActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            clearSharedPrefs(InstrumentationRegistry.getInstrumentation().getTargetContext());
            super.beforeActivityLaunched();

            inject();
        }
    };

    @Test
    public void pushlinkActivity() {
        long waitingTime = 10000;
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResource);

        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));

        for (PHSDKListener listener : ((HueSDKStub)hueSDK).getPhsdkListenerList()) {
            listener.onError(PHMessageType.PUSHLINK_AUTHENTICATION_FAILED, "Message");
        }

        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
