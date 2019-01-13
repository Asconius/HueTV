package com.asconius.huetv.huesdk.intf;

public interface AccessPoint {

    void setIpAddress(String ipAddress);

    void setUsername(String username);

    String getIpAddress();

    String getMacAddress();
}
