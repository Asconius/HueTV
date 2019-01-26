package com.asconius.huetv;

import com.asconius.huetv.huesdk.intf.HueSDK;
import com.asconius.huetv.stub.HueSDKStub;
import com.asconius.huetv.stub.PHBridgeStub;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

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
    public void bridgeActivity() {
        long waitingTime = 10000;
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResource);

        onView(withId(R.id.bridge_list)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.bridge_list)).atPosition(0).perform(click());

        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
