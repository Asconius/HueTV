package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.AccessPoint;
import com.philips.lighting.hue.sdk.PHAccessPoint;

public class AccessPointImpl implements AccessPoint {

    private final PHAccessPoint phAccessPoint;

    public AccessPointImpl() {
        phAccessPoint = new PHAccessPoint();
    }

    public AccessPointImpl(PHAccessPoint phAccessPoint) {
        this.phAccessPoint = phAccessPoint;
    }

    public PHAccessPoint getPhAccessPoint() {
        return phAccessPoint;
    }

    @Override
    public void setIpAddress(String ipAddress) {
        phAccessPoint.setIpAddress(ipAddress);
    }

    @Override
    public void setUsername(String username) {
        phAccessPoint.setUsername(username);
    }

    @Override
    public String getIpAddress() {
        return phAccessPoint.getIpAddress();
    }

    @Override
    public String getMacAddress() {
        return phAccessPoint.getMacAddress();
    }
}
