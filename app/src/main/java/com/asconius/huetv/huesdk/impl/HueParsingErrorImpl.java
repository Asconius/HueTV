package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.HueParsingError;
import com.philips.lighting.model.PHHueParsingError;

public class HueParsingErrorImpl extends HueErrorImpl implements HueParsingError {

    private final PHHueParsingError phHueParsingError;

    public HueParsingErrorImpl(PHHueParsingError phHueParsingError) {
        super(phHueParsingError.getCode(), phHueParsingError.getMessage(), phHueParsingError.getAddress());
        this.phHueParsingError = phHueParsingError;
    }
}
