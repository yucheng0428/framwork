package com.qyai.bracelet_lib.bean;


/**
 * 获取设备信息参数
 *
 */
public class DeviceInfo {


    /**
     * code : 0
     * data : {"deviceBatteryState":0,"deviceVersion":"0.7","deviceBatteryValue":80,"deviceId":145}
     * dataType : 512
     */

    private int code;
    private DataBean data;
    private int dataType;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public static class DataBean {
        /**
         * deviceBatteryState : 0
         * deviceVersion : 0.7
         * deviceBatteryValue : 80
         * deviceId : 145
         */

        private int deviceBatteryState;
        private String deviceVersion;
        private int deviceBatteryValue;
        private int deviceId;

        public int getDeviceBatteryState() {
            return deviceBatteryState;
        }

        public void setDeviceBatteryState(int deviceBatteryState) {
            this.deviceBatteryState = deviceBatteryState;
        }

        public String getDeviceVersion() {
            return deviceVersion;
        }

        public void setDeviceVersion(String deviceVersion) {
            this.deviceVersion = deviceVersion;
        }

        public int getDeviceBatteryValue() {
            return deviceBatteryValue;
        }

        public void setDeviceBatteryValue(int deviceBatteryValue) {
            this.deviceBatteryValue = deviceBatteryValue;
        }

        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "deviceBatteryState=" + deviceBatteryState +
                    ", deviceVersion='" + deviceVersion + '\'' +
                    ", deviceBatteryValue=" + deviceBatteryValue +
                    ", deviceId=" + deviceId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "code=" + code +
                ", data=" + data +
                ", dataType=" + dataType +
                '}';
    }
}
