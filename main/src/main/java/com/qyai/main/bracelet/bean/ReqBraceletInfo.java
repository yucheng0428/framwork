package com.qyai.main.bracelet.bean;

import java.io.Serializable;

public class ReqBraceletInfo implements Serializable {

    /**
     * bloodOxygen : string
     * bloodPressureH : string
     * bloodPressureL : string
     * braceletId : string
     * code : string
     * createTime : string
     * heartRate : string
     * id : string
     * respirationRate : string
     * temperature : string
     * updateTime : string
     */

    private String oxygen;
    private String bloodPressureHigh;
    private String bloodPressureLow;
    private String watchId;
    private String code;
    private String createTime;
    private String heartRate;
    private String id;
    private String breathingRate;
    private String temperature;
    private String updateTime;
    private String watchType;

    public String getWatchType() {
        return watchType;
    }

    public void setWatchType(String watchType) {
        this.watchType = watchType;
    }

    public String getOxygen() {
        return oxygen;
    }

    public void setOxygen(String oxygen) {
        this.oxygen = oxygen;
    }

    public String getBloodPressureHigh() {
        return bloodPressureHigh;
    }

    public void setBloodPressureHigh(String bloodPressureHigh) {
        this.bloodPressureHigh = bloodPressureHigh;
    }

    public String getBloodPressureLow() {
        return bloodPressureLow;
    }

    public void setBloodPressureLow(String bloodPressureLow) {
        this.bloodPressureLow = bloodPressureLow;
    }

    public String getWatchId() {
        return watchId;
    }

    public void setWatchId(String watchId) {
        this.watchId = watchId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBreathingRate() {
        return breathingRate;
    }

    public void setBreathingRate(String breathingRate) {
        this.breathingRate = breathingRate;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
