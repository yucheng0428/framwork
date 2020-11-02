package com.qyai.beaconlib.location.bean;

public class ReqBeaconInfo {
    public String major;
    public String minor;
    public String power;
    public String range;
    public String signalStrength;

    public ReqBeaconInfo() {
    }

    public ReqBeaconInfo(String major, String minor, String power, String range, String signalStrength) {
        this.major = major;
        this.minor = minor;
        this.power = power;
        this.range = range;
        this.signalStrength = signalStrength;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(String signalStrength) {
        this.signalStrength = signalStrength;
    }
}
