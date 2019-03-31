package com.asconius.huetv;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import com.asconius.huetv.stub.PHBridgeStub;
import com.google.common.truth.Truth;
import com.philips.lighting.model.PHLightState;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

@RunWith(AndroidJUnit4.class)
public class AppAndroidUnitTest extends AndroidUnitTest {

    private static final String PACKAGE_NAME = "com.asconius.huetv";

    @Before
    public void startMainActivityFromHomeScreen() throws UiObjectNotFoundException {
        clearSharedPrefs(InstrumentationRegistry.getInstrumentation().getTargetContext());
        inject();
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        launchApp(PACKAGE_NAME);
        // BridgeActivity
        clickListViewItem("MAC 2");
        // MainActivity
        uiDevice.findObject(new UiSelector().text("AUTHORIZE").className("android.widget.Button")).click();
        uiDevice.findObject(new UiSelector().text("START NOW").className("android.widget.Button")).click();
        uiDevice.findObject(new UiSelector().text("START").className("android.widget.Button")).click();
        // Home
        uiDevice.pressHome();
        uiDevice.findObject(new UiSelector().description("Apps")).clickAndWaitForNewWindow();
    }

    @Test
    public void appAndroidUnitTest() {
        List<PHLightState> phLightStates = ((PHBridgeStub)hueSDK.getSelectedBridge()).getLightStateList();
        PHLightState lightState = phLightStates.get(phLightStates.size() - 1);
        MatcherAssert.assertThat(lightState.getHue(), allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(65535)));
        MatcherAssert.assertThat(lightState.getSaturation(), allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(255)));
        MatcherAssert.assertThat(lightState.getBrightness(), allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(255)));
    }

    public void clickListViewItem(String name) throws UiObjectNotFoundException {
        UiScrollable listView = new UiScrollable(new UiSelector());
        listView.setMaxSearchSwipes(100);
        listView.scrollTextIntoView(name);
        listView.waitForExists(5000);
        UiObject listViewItem = listView.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()), name);
        listViewItem.click();
    }

    public void launchApp(String packageName) {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent();
        intent.setPackage(packageName);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);
        Collections.sort(resolveInfoList, new ResolveInfo.DisplayNameComparator(packageManager));
        if(resolveInfoList.size() > 0) {
            ResolveInfo resolveInfo = resolveInfoList.get(0);
            ActivityInfo activity = resolveInfo.activityInfo;
            ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
            Intent intentActionMain = new Intent(Intent.ACTION_MAIN);
            intentActionMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            intentActionMain.setComponent(name);
            context.startActivity(intentActionMain);
        }
    }
}
