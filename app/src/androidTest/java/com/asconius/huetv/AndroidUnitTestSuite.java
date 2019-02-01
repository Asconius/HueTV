package com.asconius.huetv;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BridgeActivityAndroidUnitTest.class,
        HueSharedPreferencesAndroidUnitTest.class,
        MainActivityAndroidUnitTest.class,
        PushlinkActivityAndroidUnitTest.class,
        AppAndroidUnitTest.class
})
public class AndroidUnitTestSuite { }
