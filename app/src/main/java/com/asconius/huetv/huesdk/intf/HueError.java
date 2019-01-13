package com.asconius.huetv.huesdk.intf;

public interface HueError {

    int NO_CONNECTION = 22;
    int BRIDGE_NOT_RESPONDING = 46;
    int AUTHENTICATION_FAILED = 1;

    int getCode();

    void setCode(int code);

    String getMessage();

    void setMessage(String message);

    String getAddress();

    void setAddress(String address);
}
