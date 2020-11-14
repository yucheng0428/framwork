package com.qyai.main.bracelet.bean;

import java.io.Serializable;

public class LactionInfo implements Serializable {
 private    String locX;
   private  String  locY;

    private String deviceId;

    public String getLocX() {
        return locX;
    }

    public void setLocX(String locX) {
        this.locX = locX;
    }

    public String getLocY() {
        return locY;
    }

    public void setLocY(String locY) {
        this.locY = locY;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "LactionInfo{" +
                "locX='" + locX + '\'' +
                ", locY='" + locY + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
