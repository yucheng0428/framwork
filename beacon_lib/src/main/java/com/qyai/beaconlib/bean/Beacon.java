package com.qyai.beaconlib.bean;

public class Beacon {
    public String major;
    public String minor;
    public int rssi;
    public long timeStamp;
    public int pow;

    public Beacon(String major, String minor, int rssi) {
        this.major = major;
        this.minor = minor;
        this.rssi = rssi;
        this.timeStamp = System.currentTimeMillis();
    }

    public Beacon(String major, String minor, int rssi, long timestamp) {
        this.major = major;
        this.minor = minor;
        this.rssi = rssi;
        this.timeStamp = timestamp;
    }

    public int getPow() {
        return pow;
    }

    public void setPow(int pow) {
        this.pow = pow;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Beacon) {
            return this.hashCode() == obj.hashCode();
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(this.major) * 65536 + Integer.parseInt(this.minor);
    }

    @Override
    public String toString() {
        return this.major + "-" +  this.minor + "-" + this.rssi + "-" + this.timeStamp;
    }
}
