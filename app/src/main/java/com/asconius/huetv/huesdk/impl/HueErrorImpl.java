package com.asconius.huetv.huesdk.impl;

import com.asconius.huetv.huesdk.intf.HueError;

public class HueErrorImpl implements HueError {

    private int code;
    private String message;
    private String address;

    public HueErrorImpl(int code, String message, String address) {
        this.code = code;
        this.message = message;
        this.address = address;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }
}
