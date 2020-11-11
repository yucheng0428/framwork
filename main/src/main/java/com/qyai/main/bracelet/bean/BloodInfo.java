package com.qyai.main.bracelet.bean;

/**
 * 血压测试实体
 */
public class BloodInfo {


    /**
     * bloodDBP : 112
     * heartValue : 60
     * bloodOxygen : 17
     * code : 0
     * tempFloat : 105
     * tempInteger : 0
     * dataType : 1539
     * hrv : 0
     * bloodSBP : 73
     */

    private int bloodDBP;
    private int heartValue;
    private int bloodOxygen;
    private int code;
    private int tempFloat;
    private int tempInteger;
    private int dataType;
    private int hrv;
    private int bloodSBP;

    public int getBloodDBP() {
        return bloodDBP;
    }

    public void setBloodDBP(int bloodDBP) {
        this.bloodDBP = bloodDBP;
    }

    public int getHeartValue() {
        return heartValue;
    }

    public void setHeartValue(int heartValue) {
        this.heartValue = heartValue;
    }

    public int getBloodOxygen() {
        return bloodOxygen;
    }

    public void setBloodOxygen(int bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTempFloat() {
        return tempFloat;
    }

    public void setTempFloat(int tempFloat) {
        this.tempFloat = tempFloat;
    }

    public int getTempInteger() {
        return tempInteger;
    }

    public void setTempInteger(int tempInteger) {
        this.tempInteger = tempInteger;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getHrv() {
        return hrv;
    }

    public void setHrv(int hrv) {
        this.hrv = hrv;
    }

    public int getBloodSBP() {
        return bloodSBP;
    }

    public void setBloodSBP(int bloodSBP) {
        this.bloodSBP = bloodSBP;
    }
}
