package com.qyai.main.bracelet.bean;

/**
 * 搜索设备 实体
 */
public class DeviceBean {


    /**
     * deviceMac : E9:B9:98:C1:50:C9
     * deviceName : E04S 50C9
     * deviceRssi : -42
     */

    private String deviceMac;
    private String deviceName;
    private int deviceRssi;

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDeviceRssi() {
        return deviceRssi;
    }

    public void setDeviceRssi(int deviceRssi) {
        this.deviceRssi = deviceRssi;
    }
}
