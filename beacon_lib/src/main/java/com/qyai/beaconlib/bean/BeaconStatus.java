package com.qyai.beaconlib.bean;

public class BeaconStatus {

    public int oldStatus;
    public int newStatus;

    public BeaconStatus(int oldStatus, int newStatus){
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

}
