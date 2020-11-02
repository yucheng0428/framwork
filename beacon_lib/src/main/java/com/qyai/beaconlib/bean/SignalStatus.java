package com.qyai.beaconlib.bean;

public class SignalStatus {

    public int oldStatus;
    public int newStatus;

    public SignalStatus(int oldStatus, int newStatus){
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }
}
