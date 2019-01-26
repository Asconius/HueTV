package com.asconius.huetv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.v7.graphics.Palette;
import android.util.Base64;

import com.asconius.huetv.stub.PHBridgeStub;
import com.philips.lighting.hue.sdk.utilities.impl.Color;
import com.philips.lighting.model.PHLightState;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainActivityAndroidUnitTest extends AndroidUnitTest {

    private static final String IMAGE = "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAIAAAACUFjqAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAJOgAACToAYJjBRwAAAAjSURBVChTY3gro4KM/n9iQEYDKs2w6AUyUtrog4wGTnqjDwC/x5Bw50Z6RAAAAABJRU5ErkJggg";

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            clearSharedPrefs(InstrumentationRegistry.getInstrumentation().getTargetContext());
            super.beforeActivityLaunched();

            inject();
        }
    };

    @Test
    public void createPalette() {
        byte[] data = Base64.decode(IMAGE, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Palette palette = Palette.from(bitmap).generate();
        List<Palette.Swatch> swatchList = palette.getSwatches();
        List<Integer> redList = Arrays.asList(0, 32, 232, 248);
        List<Integer> greenList = Arrays.asList(160, 176, 24, 240);
        List<Integer> blueList = Arrays.asList(232, 72, 32, 0);
        List<Iterator<Integer>> iteratorList = Arrays.asList(redList.iterator(), greenList.iterator(), blueList.iterator());
        for (Palette.Swatch swatch : swatchList) {
            assertThat(Color.red(swatch.getRgb())).isEqualTo(iteratorList.get(0).next());
            assertThat(Color.green(swatch.getRgb())).isEqualTo(iteratorList.get(1).next());
            assertThat(Color.blue(swatch.getRgb())).isEqualTo(iteratorList.get(2).next());
        }
    }

    @Test
    public void updateLightState() {
        MainActivity mainActivity = activityRule.getActivity();

        byte[] data = Base64.decode(IMAGE, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Palette palette = Palette.from(bitmap).generate();

        mainActivity.updateLightState(palette);

        List<Float> hueList = Arrays.asList(198.61935f, 136.6665f, 357.68735f, 58.06363f);
        List<Float> saturationList = Arrays.asList(100.0f, 81.49606f, 89.37008f, 100.0f);
        List<Float> brightnessList = Arrays.asList(90.94488f, 68.897644f, 90.94488f, 97.244095f);
        List<Iterator<Float>> iteratorList = Arrays.asList(hueList.iterator(), saturationList.iterator(), brightnessList.iterator());

        for (PHLightState lightState : ((PHBridgeStub)hueSDK.getSelectedBridge()).getLightStateList()) {
            assertThat(lightState.getHue() / 65535f * 360f).isEqualTo(iteratorList.get(0).next());
            assertThat(lightState.getSaturation() / 254f * 100f).isEqualTo(iteratorList.get(1).next());
            assertThat(lightState.getBrightness() / 254f * 100f).isEqualTo(iteratorList.get(2).next());
        }
    }

    @Test
    public void mainActivity() throws UiObjectNotFoundException {
        long waitingTime = 10000;
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        MainActivity mainActivity = activityRule.getActivity();

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
