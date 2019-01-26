package com.asconius.huetv;

import android.app.Activity;
import android.os.Bundle;

import javax.inject.Inject;

public class HueActivity extends Activity {

    @Inject
    HueSDKDecorator hueSDKDecorator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App app = (App) getApplication();
        app.getComponent().inject(this);
    }
}
