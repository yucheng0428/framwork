package com.qyai.beaconlib.bean;

public class SensorEventBean {
    private int x;
    private int y;
    private int z;
    private long timeStamp;

    public SensorEventBean() {

    }

    public SensorEventBean(float[] values, long timeStamp) {
        this.timeStamp = timeStamp;
        this.x = (byte) ((int) values[0] & 0xff);  //x
        this.y = (byte) ((int) values[1] & 0xff);  //y
        this.z = (byte) ((int) values[2] & 0xff);  //z
    }

    public SensorEventBean(byte[] values, long timeStamp) {
        this.timeStamp = timeStamp;
        this.x = values[0];  //x
        this.y = values[1];  //y
        this.z = values[2];  //z
    }

    public SensorEventBean(int x, int y, int z, long timeStamp) {
        this.timeStamp = timeStamp;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
