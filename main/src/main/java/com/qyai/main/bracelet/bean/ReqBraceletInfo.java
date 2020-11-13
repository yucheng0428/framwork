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

    private String bloodOxygen;
    private String bloodPressureH;
    private String bloodPressureL;
    private String braceletId;
    private String code;
    private String createTime;
    private String heartRate;
    private String id;
    private String respirationRate;
    private String temperature;
    private String updateTime;

    public String getBloodOxygen() {
        return bloodOxygen;
    }

    public void setBloodOxygen(String bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }

    public String getBloodPressureH() {
        return bloodPressureH;
    }

    public void setBloodPressureH(String bloodPressureH) {
        this.bloodPressureH = bloodPressureH;
    }

    public String getBloodPressureL() {
        return bloodPressureL;
    }

    public void setBloodPressureL(String bloodPressureL) {
        this.bloodPressureL = bloodPressureL;
    }

    public String getBraceletId() {
        return braceletId;
    }

    public void setBraceletId(String braceletId) {
        this.braceletId = braceletId;
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

    public String getRespirationRate() {
        return respirationRate;
    }

    public void setRespirationRate(String respirationRate) {
        this.respirationRate = respirationRate;
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
